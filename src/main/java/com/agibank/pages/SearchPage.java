package com.agibank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Page Object para a funcionalidade de busca do Blog do Agi
 */
@Slf4j
public class SearchPage extends BasePage {

    // Locators
    private final By searchInput = By.cssSelector(
            "input.search-field, input[type='search'], input[name='s'], #search-input, .search-form input");
    private final By searchResults = By.cssSelector("article, .post, .search-result, .entry, .entry-content");
    private final By noResultsMessage = By.xpath("//p[contains(text(), 'Lamentamos, mas nada foi encontrado')]");
    private final By searchResultTitle = By
            .cssSelector("article h2, article h3, .post-title, .entry-title, h2.entry-title");

    public SearchPage(WebDriver driver) {
        super(driver);
        ensureSearchInputReady();
    }

    /**
     * Garante que o campo de busca está acessível.
     * O tema Astra usa animação de slide (height/overflow) no painel de busca —
     * o elemento existe no DOM mas pode não ser detectado como visível pelo
     * Selenium durante a transição CSS.
     * Fallback: percorre todos os ancestrais do input e força visibilidade via JS.
     */
    private void ensureSearchInputReady() {
        try {
            waitForElementVisible(searchInput);
        } catch (TimeoutException e) {
            log.warn("Campo de busca não visível — aplicando visibilidade via JS nos ancestrais");
            List<WebElement> inputs = driver.findElements(searchInput);
            if (!inputs.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript(
                        "var el = arguments[0];" +
                                "function forceShow(elem) {" +
                                "  elem.style.setProperty('display','block','important');" +
                                "  elem.style.setProperty('visibility','visible','important');" +
                                "  elem.style.setProperty('opacity','1','important');" +
                                "  elem.style.setProperty('height','auto','important');" +
                                "  elem.style.setProperty('max-height','none','important');" +
                                "  elem.style.setProperty('overflow','visible','important');" +
                                "  elem.style.setProperty('clip','auto','important');" +
                                "  elem.style.setProperty('clip-path','none','important');" +
                                "}" +
                                "forceShow(el);" +
                                "var p = el.parentElement; var depth = 0;" +
                                "while (p && p.tagName !== 'BODY' && depth < 15) {" +
                                "  var cs = window.getComputedStyle(p);" +
                                "  if (cs.display === 'none' || cs.visibility === 'hidden' ||" +
                                "      cs.visibility === 'collapse' || parseFloat(cs.opacity) < 0.01 ||" +
                                "      (parseFloat(cs.height) < 2 && cs.overflow !== 'visible') ||" +
                                "      (parseFloat(cs.maxHeight) >= 0 && parseFloat(cs.maxHeight) < 2)) {" +
                                "    forceShow(p);" +
                                "  }" +
                                "  p = p.parentElement; depth++;" +
                                "}",
                        inputs.get(0));
                try {
                    waitForElementVisible(searchInput);
                } catch (TimeoutException e2) {
                    log.error("Campo de busca permanece não visível mesmo após forçar ancestrais via JS");
                    throw new RuntimeException(
                            "Campo de busca não ficou visível após forçar CSS nos ancestrais");
                }
            } else {
                throw new RuntimeException(
                        "Campo de busca não encontrado no DOM após clicar no ícone de busca");
            }
        }
    }

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

    public SearchResultPage submeterBusca() {
        WebElement campo = waitForElementVisible(searchInput);
        campo.sendKeys(Keys.ENTER);
        waitForPageLoad();
        return new SearchResultPage(driver);
    }

    public SearchResultPage realizarBusca(String termo) {
        preencherCampoBusca(termo);
        return submeterBusca();
    }

    public boolean temResultados() {
        try {
            List<WebElement> results = driver.findElements(searchResults);
            return !results.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public int getQuantidadeResultados() {
        return driver.findElements(searchResults).size();
    }

    public boolean isMensagemSemResultadosVisivel() {
        return isElementVisible(noResultsMessage);
    }

    public String getPrimeiroResultadoTitulo() {
        return getElementText(searchResultTitle);
    }

    public boolean isCampoBuscaVisivel() {
        return isElementVisible(searchInput);
    }

    public boolean resultadosContemTermo(String termo) {
        List<WebElement> titles = driver.findElements(searchResultTitle);
        return titles.stream()
                .anyMatch(title -> title.getText().toLowerCase().contains(termo.toLowerCase()));
    }
}
