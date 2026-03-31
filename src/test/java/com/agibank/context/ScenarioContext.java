package com.agibank.context;

import com.agibank.config.ConfigurationManager;
import com.agibank.config.DriverFactory;
import com.agibank.pages.HomePage;
import com.agibank.pages.ResponsividadePage;
import com.agibank.pages.SearchPage;
import com.agibank.pages.SearchResultPage;
import org.openqa.selenium.WebDriver;

/**
 * Contexto compartilhado entre os Steps de um mesmo cenário.
 * Gerenciado pelo Cucumber PicoContainer — uma instância por cenário.
 */
public class ScenarioContext {

    private final WebDriver driver;
    private final HomePage homePage;
    private final ResponsividadePage responsividadePage;
    private SearchPage searchPage;
    private SearchResultPage searchResultPage;
    private String termoBuscado;

    public ScenarioContext() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
        this.responsividadePage = new ResponsividadePage(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public ResponsividadePage getResponsividadePage() {
        return responsividadePage;
    }

    public SearchPage getSearchPage() {
        return searchPage;
    }

    public void setSearchPage(SearchPage searchPage) {
        this.searchPage = searchPage;
    }

    public SearchResultPage getSearchResultPage() {
        return searchResultPage;
    }

    public void setSearchResultPage(SearchResultPage searchResultPage) {
        this.searchResultPage = searchResultPage;
    }

    public String getTermoBuscado() {
        return termoBuscado;
    }

    public void setTermoBuscado(String termoBuscado) {
        this.termoBuscado = termoBuscado;
    }

    public String getBaseUrl() {
        return ConfigurationManager.getBaseUrl();
    }
}
