package com.agibank.hooks;

import com.agibank.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.agibank.config.DriverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hooks centralizados para gerenciar o ciclo de vida do WebDriver por cenário.
 */
public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final ScenarioContext context;

    public Hooks(ScenarioContext context) {
        this.context = context;
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Iniciando cenário: {}", scenario.getName());
        logger.info("========================================");
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        WebDriver driver = context.getDriver();

        if (driver != null) {
            if (scenario.isFailed()) {
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Screenshot - " + scenario.getName());
                } catch (Exception e) {
                    logger.error("Erro ao capturar screenshot: {}", e.getMessage());
                }
            }

            DriverFactory.quitDriver();
        }

        logger.info("========================================");
        logger.info("Cenário finalizado: {}", scenario.getName());
        logger.info("Status: {}", scenario.getStatus());
        logger.info("========================================\n");
    }
}
