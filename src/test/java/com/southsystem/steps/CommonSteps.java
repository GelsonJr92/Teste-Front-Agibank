package com.southsystem.steps;

import com.southsystem.config.ConfigurationManager;
import com.southsystem.config.DriverFactory;
import com.southsystem.pages.HomePage;
import io.cucumber.java.pt.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Steps definitions comuns compartilhados entre diferentes features
 */
public class CommonSteps {

    private WebDriver driver;
    private HomePage homePage;

    @Dado("que eu acesso a pagina inicial do Blog do Agi")
    public void queEuAcessoAPaginaInicialDoBlogDoAgi() {
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        homePage.acessarPaginaInicial(ConfigurationManager.getBaseUrl());
        Assert.assertTrue(homePage.isPaginaInicialCarregada(),
                "Página inicial não foi carregada corretamente");
    }
}
