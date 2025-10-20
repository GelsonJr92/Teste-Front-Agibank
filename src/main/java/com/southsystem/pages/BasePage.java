package com.southsystem.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.southsystem.config.ConfigurationManager;
import io.qameta.allure.Step;

/**
 * Classe base para todas as Page Objects
 */
public class BasePage {

    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(ConfigurationManager.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    /**
     * Aguarda elemento estar visível
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Aguarda elemento estar clicável
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Aguarda elemento estar presente no DOM
     */
    protected WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Clica em um elemento com espera
     */
    @Step("Clicar no elemento: {locator}")
    protected void clickElement(By locator) {
        waitForElementClickable(locator).click();
    }

    /**
     * Clica em um elemento usando JavaScript
     */
    @Step("Clicar no elemento com JavaScript: {locator}")
    protected void clickElementWithJS(By locator) {
        WebElement element = waitForElementPresent(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Preenche um campo de texto
     */
    @Step("Preencher campo {locator} com o valor: {text}")
    protected void fillField(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Retorna o texto de um elemento
     */
    protected String getElementText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    /**
     * Força visibilidade de elemento via JavaScript (útil em headless)
     */
    protected void forceElementVisibility(By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var el = arguments[0];" + "if(el) {" + "  el.style.display='block';"
                            + "  el.style.visibility='visible';" + "  el.style.opacity='1';" + "}",
                    driver.findElement(locator));
            logger.debug("Visibilidade forçada via JS: {}", locator);
        } catch (Exception e) {
            logger.debug("Não foi possível forçar visibilidade: {}", locator);
        }
    }

    /**
     * Verifica se elemento está visível
     */
    protected boolean isElementVisible(By locator) {
        try {
            return waitForElementVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Rola a página até o elemento
     */
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Aguarda elemento estar estável (visível e clicável)
     */
    @Step("Aguardar elemento estar estável: {locator}")
    protected WebElement waitForElementStable(By locator) {
        waitForElementVisible(locator);
        return waitForElementClickable(locator);
    }

    /**
     * Move o mouse sobre um elemento (hover) com retry automático Fallback para JavaScript em caso
     * de elemento oculto (comum em headless)
     */
    @Step("Mover mouse sobre elemento: {locator}")
    protected void hoverElement(By locator) {
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                WebElement element = waitForElementStable(locator);
                Actions actions = new Actions(driver);
                actions.moveToElement(element).perform();

                // Pequena pausa para garantir que o hover foi aplicado
                wait.until(driver -> {
                    try {
                        Thread.sleep(300);
                        return true;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                });

                logger.debug("Hover realizado com sucesso no elemento: {}", locator);
                return;
            } catch (Exception e) {
                attempt++;
                logger.warn("Falha no hover tentativa {} de {}: {}", attempt, maxRetries,
                        e.getMessage());

                if (attempt >= maxRetries) {
                    // Fallback: tenta click direto (menus podem não suportar hover em headless)
                    try {
                        logger.info("Tentando click direto em: {}", locator);
                        WebElement element = driver.findElement(locator);
                        element.click();
                        Thread.sleep(300);
                        logger.info("Click direto concluído");
                        return;
                    } catch (Exception jsError) {
                        logger.error("Falha ao realizar hover após {} tentativas: {}", maxRetries,
                                locator);
                        throw new RuntimeException("Não foi possível realizar hover: " + locator,
                                jsError);
                    }
                }
            }
        }
    }

    /**
     * Aguarda a página carregar completamente
     */
    protected void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Tira screenshot
     */
    protected byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
