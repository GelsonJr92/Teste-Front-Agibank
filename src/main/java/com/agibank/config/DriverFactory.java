package com.agibank.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory para criação e configuração de instâncias do WebDriver.
 * Suporta execução local e remota (Selenium Grid).
 */
@Slf4j
public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Cria e configura uma instância do WebDriver.
     * Detecta automaticamente se deve usar execução local ou Selenium Grid.
     *
     * @param browser Nome do navegador (chrome, firefox, edge)
     * @return WebDriver configurado
     */
    public static WebDriver createDriver(String browser) {
        if (driver.get() != null) {
            return driver.get();
        }

        WebDriver webDriver;
        String gridUrl = System.getProperty("grid.url", System.getenv("GRID_URL"));

        if (gridUrl != null && !gridUrl.isEmpty()) {
            log.info("Criando driver remoto no Selenium Grid: {}", gridUrl);
            webDriver = createRemoteDriver(browser, gridUrl);
        } else {
            log.info("Criando driver local: {}", browser);
            webDriver = createLocalDriver(browser);
        }

        configureDriver(webDriver);
        driver.set(webDriver);
        return webDriver;
    }

    private static WebDriver createLocalDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (ConfigurationManager.isHeadless()) {
                    firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                }
                yield new FirefoxDriver(firefoxOptions);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (ConfigurationManager.isHeadless()) {
                    edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                }
                yield new EdgeDriver(edgeOptions);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                // Anti-bot detection: prevents Cloudflare and similar from blocking automation
                chromeOptions.addArguments(
                        "--disable-blink-features=AutomationControlled",
                        "--remote-allow-origins=*",
                        "--disable-dev-shm-usage",
                        "--no-sandbox",
                        "--disable-features=IsolateOrigins,site-per-process",
                        "--window-size=1920,1080");
                chromeOptions.setExperimentalOption("excludeSwitches",
                        java.util.Collections.singletonList("enable-automation"));
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                if (ConfigurationManager.isHeadless()) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu",
                            "--disable-software-rasterizer", "--disable-extensions",
                            "--no-first-run", "--disable-background-networking",
                            "--disable-background-timer-throttling",
                            "--disable-client-side-phishing-detection",
                            "--disable-hang-monitor", "--disable-popup-blocking",
                            "--disable-prompt-on-repost", "--disable-translate",
                            "--metrics-recording-only", "--safebrowsing-disable-auto-update",
                            "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
                // CDP: inject navigator.webdriver override on every new document (persists
                // across navigations)
                try {
                    chromeDriver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument",
                            java.util.Map.of("source",
                                    "Object.defineProperty(navigator, 'webdriver', {get: () => undefined});" +
                                            "window.navigator.chrome = {runtime: {}};" +
                                            "Object.defineProperty(navigator, 'plugins', {get: () => [1,2,3,4,5]});" +
                                            "Object.defineProperty(navigator, 'languages', {get: () => ['pt-BR','pt','en-US','en']});"));
                } catch (Exception cdpEx) {
                    log.debug("CDP injection não disponível: {}", cdpEx.getMessage());
                }
                yield chromeDriver;
            }
        };
    }

    private static WebDriver createRemoteDriver(String browser, String gridUrl) {
        try {
            URL hubUrl = new URL(gridUrl);
            return switch (browser.toLowerCase()) {
                case "firefox" -> {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (ConfigurationManager.isHeadless()) {
                        firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                    }
                    firefoxOptions.setCapability("se:recordVideo", true);
                    firefoxOptions.setCapability("se:screenResolution", "1920x1080");
                    log.info("Driver Firefox remoto criado com sucesso");
                    yield new RemoteWebDriver(hubUrl, firefoxOptions);
                }
                case "edge" -> {
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (ConfigurationManager.isHeadless()) {
                        edgeOptions.addArguments("--headless=new", "--window-size=1920,1080");
                    }
                    edgeOptions.setCapability("se:recordVideo", true);
                    edgeOptions.setCapability("se:screenResolution", "1920x1080");
                    log.info("Driver Edge remoto criado com sucesso");
                    yield new RemoteWebDriver(hubUrl, edgeOptions);
                }
                default -> {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*", "--disable-dev-shm-usage", "--no-sandbox");
                    if (ConfigurationManager.isHeadless()) {
                        chromeOptions.addArguments("--headless=new");
                    }
                    chromeOptions.setCapability("se:recordVideo", true);
                    chromeOptions.setCapability("se:screenResolution", "1920x1080");
                    log.info("Driver Chrome remoto criado com sucesso");
                    yield new RemoteWebDriver(hubUrl, chromeOptions);
                }
            };
        } catch (MalformedURLException e) {
            log.error("URL do Grid inválida: {}", gridUrl, e);
            throw new RuntimeException("Falha ao criar driver remoto. URL inválida: " + gridUrl, e);
        }
    }

    private static void configureDriver(WebDriver webDriver) {
        webDriver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigurationManager.getImplicitWait()));
        webDriver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigurationManager.getPageLoadTimeout()));
        // Remove navigator.webdriver flag to avoid bot detection on page loads
        if (webDriver instanceof org.openqa.selenium.JavascriptExecutor js) {
            try {
                js.executeScript(
                        "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
            } catch (Exception e) {
                log.debug("Could not override navigator.webdriver: {}", e.getMessage());
            }
        }
    }

    public static WebDriver getDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver == null) {
            return createDriver(ConfigurationManager.getBrowser());
        }
        return currentDriver;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                log.info("Driver encerrado com sucesso");
            } catch (Exception e) {
                log.warn("Erro ao encerrar driver: {}", e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
}
