package com.southsystem.hooks;

import java.io.ByteArrayInputStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.southsystem.config.ConfigurationManager;
import com.southsystem.config.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

/**
 * Hooks centralizados para gerenciar o ciclo de vida do WebDriver Este arquivo substitui
 * os @Before/@After espalhados nos Steps
 */
public class Hooks {

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Iniciando cenário: " + scenario.getName());
        System.out.println("========================================");

        // Cria o driver apenas uma vez por cenário
        if (DriverFactory.getDriver() == null) {
            DriverFactory.createDriver(ConfigurationManager.getBrowser());
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            // Aguarda 2 segundos antes de fechar para visualização
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Captura screenshot em caso de falha
            if (scenario.isFailed()) {
                try {
                    byte[] screenshot =
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Screenshot - " + scenario.getName(),
                            new ByteArrayInputStream(screenshot));
                    scenario.attach(screenshot, "image/png", "Screenshot");
                } catch (Exception e) {
                    System.err.println("Erro ao capturar screenshot: " + e.getMessage());
                }
            }

            // Fecha o navegador
            DriverFactory.quitDriver();
        }

        System.out.println("========================================");
        System.out.println("Cenário finalizado: " + scenario.getName());
        System.out.println("Status: " + scenario.getStatus());
        System.out.println("========================================\n");
    }
}

