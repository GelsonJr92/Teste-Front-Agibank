package com.agibank.hooks;

import com.agibank.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.agibank.config.DriverFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Hooks centralizados para gerenciar o ciclo de vida do WebDriver por cenário.
 */
@Slf4j
public class Hooks {

    private final ScenarioContext context;

    public Hooks(ScenarioContext context) {
        this.context = context;
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        log.info("========================================");
        log.info("Iniciando cenário: {}", scenario.getName());
        log.info("========================================");
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
                    log.error("Erro ao capturar screenshot: {}", e.getMessage());
                }
            }

            DriverFactory.quitDriver();
        }

        log.info("========================================");
        log.info("Cenário finalizado: {}", scenario.getName());
        log.info("Status: {}", scenario.getStatus());
        log.info("========================================\n");
    }
}
