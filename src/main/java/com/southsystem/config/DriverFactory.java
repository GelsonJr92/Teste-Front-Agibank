package com.southsystem.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Factory para criação e configuração de instâncias do WebDriver Suporta execução local e remota
 * (Selenium Grid)
 */
public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Cria ChromeOptions com emulação mobile para resoluções pequenas
     * 
     * @param width Largura do dispositivo
     * @param height Altura do dispositivo
     * @return ChromeOptions configurado para mobile
     */
    private static ChromeOptions createMobileChromeOptions(int width, int height) {
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", width);
        deviceMetrics.put("height", height);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent",
                "Mozilla/5.0 (Linux; Android 10; SM-G973F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        return options;
    }

    /**
     * Cria e configura uma instância do WebDriver Detecta automaticamente se deve usar execução
     * local ou Grid
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

        // Se grid.url estiver configurado, usa RemoteWebDriver (Selenium Grid)
        if (gridUrl != null && !gridUrl.isEmpty()) {
            logger.info("Criando driver remoto no Selenium Grid: {}", gridUrl);
            webDriver = createRemoteDriver(browser, gridUrl);
        } else {
            // Execução local
            logger.info("Criando driver local: {}", browser);
            webDriver = createLocalDriver(browser);
        }

        configureDriver(webDriver);
        driver.set(webDriver);
        return webDriver;
    }

    /**
     * Cria driver local (execução na máquina local)
     */
    private static WebDriver createLocalDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (ConfigurationManager.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (ConfigurationManager.isHeadless()) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }
                webDriver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--window-size=1920,1080");

                if (ConfigurationManager.isHeadless()) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--disable-software-rasterizer");
                    chromeOptions.addArguments("--disable-extensions");
                    // User-Agent para evitar detecção de headless
                    chromeOptions.addArguments(
                            "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }

                webDriver = new ChromeDriver(chromeOptions);
                break;
        }

        return webDriver;
    }

    /**
     * Cria driver remoto (execução no Selenium Grid)
     */
    private static WebDriver createRemoteDriver(String browser, String gridUrl) {
        WebDriver webDriver;

        try {
            URL hubUrl = new URL(gridUrl);

            switch (browser.toLowerCase()) {
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (ConfigurationManager.isHeadless()) {
                        firefoxOptions.addArguments("--headless");
                        firefoxOptions.addArguments("--width=1920");
                        firefoxOptions.addArguments("--height=1080");
                    }
                    firefoxOptions.setCapability("se:recordVideo", true);
                    firefoxOptions.setCapability("se:screenResolution", "1920x1080");
                    webDriver = new RemoteWebDriver(hubUrl, firefoxOptions);
                    logger.info("Driver Firefox remoto criado com sucesso");
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (ConfigurationManager.isHeadless()) {
                        edgeOptions.addArguments("--headless=new");
                        edgeOptions.addArguments("--window-size=1920,1080");
                    }
                    edgeOptions.setCapability("se:recordVideo", true);
                    edgeOptions.setCapability("se:screenResolution", "1920x1080");
                    webDriver = new RemoteWebDriver(hubUrl, edgeOptions);
                    logger.info("Driver Edge remoto criado com sucesso");
                    break;

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--no-sandbox");

                    if (ConfigurationManager.isHeadless()) {
                        chromeOptions.addArguments("--headless=new");
                    }

                    chromeOptions.setCapability("se:recordVideo", true);
                    chromeOptions.setCapability("se:screenResolution", "1920x1080");
                    webDriver = new RemoteWebDriver(hubUrl, chromeOptions);
                    logger.info("Driver Chrome remoto criado com sucesso");
                    break;
            }

        } catch (MalformedURLException e) {
            logger.error("URL do Grid inválida: {}", gridUrl, e);
            throw new RuntimeException("Falha ao criar driver remoto. URL inválida: " + gridUrl, e);
        }

        return webDriver;
    }

    /**
     * Configura timeouts do WebDriver
     */
    private static void configureDriver(WebDriver webDriver) {
        webDriver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigurationManager.getImplicitWait()));
        webDriver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigurationManager.getPageLoadTimeout()));
    }

    /**
     * Retorna a instância atual do WebDriver Se não existir, cria automaticamente usando o browser
     * padrão
     * 
     * @return WebDriver atual ou novo
     */
    public static WebDriver getDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver == null) {
            String browser = ConfigurationManager.getBrowser();
            return createDriver(browser);
        }
        return currentDriver;
    }

    /**
     * Encerra a instância do WebDriver
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                logger.info("Driver encerrado com sucesso");
            } catch (Exception e) {
                logger.warn("Erro ao encerrar driver: {}", e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
}
