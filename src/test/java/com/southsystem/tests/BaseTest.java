package com.southsystem.tests;

import com.southsystem.config.ConfigurationManager;
import com.southsystem.config.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

/**
 * Classe base para todos os testes
 */
public class BaseTest {
    
    protected WebDriver driver;
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        String browserToUse = System.getProperty("browser", browser);
        driver = DriverFactory.createDriver(browserToUse);
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            takeScreenshot();
            DriverFactory.quitDriver();
        }
    }
    
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        if (driver != null) {
            return ((org.openqa.selenium.TakesScreenshot) driver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
        }
        return new byte[0];
    }
    
    protected String getBaseUrl() {
        return ConfigurationManager.getBaseUrl();
    }
}