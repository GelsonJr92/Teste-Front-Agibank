package com.southsystem.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object para a funcionalidade de busca do Blog do Agi
 */
public class SearchPage extends BasePage {
    
    // Locators
    private final By searchInput = By.cssSelector("input.search-field, input[type='search'], input[name='s'], #search-input, .search-form input");
    private final By searchResults = By.cssSelector("article, .post, .search-result, .entry, .entry-content");
    private final By noResultsMessage = By.xpath("//p[contains(text(), 'Lamentamos, mas nada foi encontrado')]");
    private final By searchResultTitle = By.cssSelector("article h2, article h3, .post-title, .entry-title, h2.entry-title");
    
    public SearchPage(WebDriver driver) {
        super(driver);
        // Aguardar campo de busca estar disponível após clicar na lupa
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Preenche o campo de busca
     */
    @Step("Preencher campo de busca com: {termo}")
    public SearchPage preencherCampoBusca(String termo) {
        try {
            WebElement campo = waitForElementVisible(searchInput);
            campo.clear();
            campo.sendKeys(termo);
        } catch (TimeoutException e) {
            // Tenta localizar por XPath alternativo
            By alternativeInput = By.xpath("//input[contains(@class, 'search') or @type='search']");
            WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(alternativeInput));
            campo.clear();
            campo.sendKeys(termo);
        }
        return this;
    }
    
    /**
     * Submete a busca pressionando Enter
     */
    @Step("Submeter busca")
    public SearchResultPage submeterBusca() {
        WebElement campo = waitForElementVisible(searchInput);
        campo.sendKeys(Keys.ENTER);
        waitForPageLoad();
        return new SearchResultPage(driver);
    }
    
    /**
     * Realiza uma busca completa (preenche e submete)
     */
    @Step("Realizar busca por: {termo}")
    public SearchResultPage realizarBusca(String termo) {
        preencherCampoBusca(termo);
        return submeterBusca();
    }
    
    /**
     * Verifica se há resultados de busca
     */
    @Step("Verificar se há resultados de busca")
    public boolean temResultados() {
        try {
            List<WebElement> results = driver.findElements(searchResults);
            return !results.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Retorna a quantidade de resultados
     */
    @Step("Contar quantidade de resultados")
    public int getQuantidadeResultados() {
        return driver.findElements(searchResults).size();
    }
    
    /**
     * Verifica se a mensagem de "nenhum resultado" está visível
     */
    @Step("Verificar mensagem de nenhum resultado encontrado")
    public boolean isMensagemSemResultadosVisivel() {
        return isElementVisible(noResultsMessage);
    }
    
    /**
     * Retorna o texto do primeiro resultado
     */
    @Step("Obter título do primeiro resultado")
    public String getPrimeiroResultadoTitulo() {
        return getElementText(searchResultTitle);
    }
    
    /**
     * Verifica se o campo de busca está visível
     */
    @Step("Verificar se o campo de busca está visível")
    public boolean isCampoBuscaVisivel() {
        return isElementVisible(searchInput);
    }
    
    /**
     * Verifica se algum resultado contém o termo buscado
     */
    @Step("Verificar se os resultados contêm o termo: {termo}")
    public boolean resultadosContemTermo(String termo) {
        List<WebElement> titles = driver.findElements(searchResultTitle);
        return titles.stream()
                .anyMatch(title -> title.getText().toLowerCase().contains(termo.toLowerCase()));
    }
}
