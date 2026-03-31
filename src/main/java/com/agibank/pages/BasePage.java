package com.agibank.pages;

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
import com.agibank.config.ConfigurationManager;

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

    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void clickElement(By locator) {
        waitForElementClickable(locator).click();
    }

    protected void clickElementWithJS(By locator) {
        WebElement element = waitForElementPresent(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void fillField(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getElementText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    protected void forceElementVisibility(By locator) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var el = arguments[0];" +
                            "if(el) {" +
                            "  el.style.display='block';" +
                            "  el.style.visibility='visible';" +
                            "  el.style.opacity='1';" +
                            "}",
                    driver.findElement(locator));
            logger.debug("Visibilidade forçada via JS: {}", locator);
        } catch (Exception e) {
            logger.debug("Não foi possível forçar visibilidade: {}", locator);
        }
    }

    protected boolean isElementVisible(By locator) {
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                return waitForElementVisible(locator).isDisplayed();
            } catch (TimeoutException e) {
                return false;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                if (attempt == 2)
                    return false;
            }
        }
        return false;
    }

    protected void scrollToElement(By locator) {
        for (int attempt = 0; attempt < 2; attempt++) {
            try {
                WebElement element = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                return;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                if (attempt == 1)
                    throw e;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                return; // element not present — skip scroll silently
            }
        }
    }

    protected WebElement waitForElementStable(By locator) {
        waitForElementVisible(locator);
        return waitForElementClickable(locator);
    }

    /**
     * Move o mouse sobre um elemento (hover) com retry automático.
     * Fallback para click direto em caso de falha (útil em modo headless).
     */
    protected void hoverElement(By locator) {
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                WebElement element = waitForElementStable(locator);
                Actions actions = new Actions(driver);
                actions.moveToElement(element).perform();

                logger.debug("Hover realizado com sucesso no elemento: {}", locator);
                return;
            } catch (Exception e) {
                attempt++;
                logger.warn("Falha no hover tentativa {} de {}: {}", attempt, maxRetries, e.getMessage());

                if (attempt >= maxRetries) {
                    try {
                        logger.info("Tentando click direto em: {}", locator);
                        WebElement element = driver.findElement(locator);
                        element.click();
                        logger.info("Click direto concluído");
                        return;
                    } catch (Exception jsError) {
                        logger.error("Falha ao realizar hover após {} tentativas: {}", maxRetries, locator);
                        throw new RuntimeException("Não foi possível realizar hover: " + locator, jsError);
                    }
                }
            }
        }
    }

    protected void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
