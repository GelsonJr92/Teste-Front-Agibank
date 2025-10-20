package com.southsystem.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page Object para a página de resultados de busca
 */
public class SearchResultPage extends BasePage {
    
    // Locators
    private final By tituloResultados = By.xpath("//h1[contains(text(), 'Resultados encontrados para:')]");
    private final By termoBuscado = By.xpath("//h1/following-sibling::*");
    private final By mensagemNenhumResultado = By.xpath("//p[contains(text(), 'Lamentamos, mas nada foi encontrado')]");
    private final By resultados = By.cssSelector("article, .post");
    private final By titulosResultados = By.cssSelector("article h2, .entry-title");
    
    // Widgets laterais
    private final By bannerConsignado = By.xpath("//*[contains(text(), 'Simule hoje seu Consignado')]");
    private final By widgetNewsletter = By.xpath("//*[contains(text(), 'Se inscreva em nossa Newsletter')]");
    private final By campoEmailNewsletter = By.cssSelector("input[type='email'], input[placeholder*='email' or @placeholder*='e-mail']");
    private final By botaoAssinar = By.xpath("//button[contains(text(), 'Assinar')]");
    
    // Menus principais (mesmo da home)
    private final By menuOAgibank = By.xpath("//a[contains(text(), 'O Agibank')]");
    private final By menuProdutos = By.xpath("//a[contains(text(), 'Produtos')]");
    private final By menuServicos = By.xpath("//a[contains(text(), 'Serviços') or contains(text(), 'Servicos')]");
    private final By menuSuasFinancas = By.xpath("//a[contains(text(), 'Suas finanças') or contains(text(), 'Suas financas')]");
    private final By menuSeusBeneficios = By.xpath("//a[contains(text(), 'Seus benefícios') or contains(text(), 'Seus beneficios')]");
    private final By menuSuaSeguranca = By.xpath("//a[contains(text(), 'Sua segurança') or contains(text(), 'Sua seguranca')]");
    private final By menuStories = By.xpath("//a[contains(text(), 'Stories')]");
    
    // Redes sociais do rodapé
    private final By linkFacebook = By.cssSelector("a[href*='facebook']");
    private final By linkInstagram = By.cssSelector("a[href*='instagram']");
    private final By linkLinkedin = By.cssSelector("a[href*='linkedin']");
    private final By linkTiktok = By.cssSelector("a[href*='tiktok']");
    
    // Links do rodapé
    private final By footerMenuOAgibank = By.xpath("//footer//a[contains(text(), 'O Agibank')]");
    private final By footerMenuProdutos = By.xpath("//footer//a[contains(text(), 'Produtos')]");
    private final By footerMenuServicos = By.xpath("//footer//a[contains(text(), 'Serviços') or contains(text(), 'Servicos')]");
    private final By footerMenuSuasFinancas = By.xpath("//footer//a[contains(text(), 'Suas finanças') or contains(text(), 'Suas financas')]");
    private final By footerMenuSeusBeneficios = By.xpath("//footer//a[contains(text(), 'Seus benefícios') or contains(text(), 'Seus beneficios')]");
    private final By footerMenuSuaSeguranca = By.xpath("//footer//a[contains(text(), 'Sua segurança') or contains(text(), 'Sua seguranca')]");
    private final By footerMenuStories = By.xpath("//footer//a[contains(text(), 'Stories')]");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }
    
    // ========== MÉTODOS DE VALIDAÇÃO - CABEÇALHO ==========
    
    /**
     * Verifica se título de resultados está visível
     */
    @Step("Verificar se título de resultados está visível")
    public boolean isTituloResultadosVisivel() {
        return isElementVisible(tituloResultados);
    }
    
    /**
     * Obtém o termo buscado exibido na página
     */
    @Step("Obter termo buscado")
    public String getTermoBuscado() {
        return getElementText(termoBuscado);
    }
    
    /**
     * Verifica se mensagem de nenhum resultado está visível
     */
    @Step("Verificar mensagem de nenhum resultado")
    public boolean isMensagemNenhumResultadoVisivel() {
        return isElementVisible(mensagemNenhumResultado);
    }
    
    /**
     * Verifica se há resultados
     */
    @Step("Verificar se há resultados")
    public boolean temResultados() {
        return !driver.findElements(resultados).isEmpty();
    }
    
    /**
     * Obtém quantidade de resultados
     */
    @Step("Contar resultados")
    public int getQuantidadeResultados() {
        return driver.findElements(resultados).size();
    }
    
    // ========== MÉTODOS DE VALIDAÇÃO - WIDGETS ==========
    
    /**
     * Verifica se banner de Consignado está visível
     */
    @Step("Verificar banner de Consignado")
    public boolean isBannerConsignadoVisivel() {
        return isElementVisible(bannerConsignado);
    }
    
    /**
     * Verifica se widget de Newsletter está visível
     */
    @Step("Verificar widget de Newsletter")
    public boolean isWidgetNewsletterVisivel() {
        return isElementVisible(widgetNewsletter);
    }
    
    /**
     * Verifica se campo de email da Newsletter está visível
     */
    @Step("Verificar campo de email da Newsletter")
    public boolean isCampoEmailNewsletterVisivel() {
        return isElementVisible(campoEmailNewsletter);
    }
    
    /**
     * Verifica se botão Assinar está visível
     */
    @Step("Verificar botão Assinar")
    public boolean isBotaoAssinarVisivel() {
        return isElementVisible(botaoAssinar);
    }
    
    // ========== MÉTODOS DE VALIDAÇÃO - MENUS ==========
    
    /**
     * Verifica se todos os menus principais estão visíveis
     */
    @Step("Verificar menus principais")
    public boolean todosMenusPrincipaisVisiveis() {
        return isElementVisible(menuOAgibank) &&
               isElementVisible(menuProdutos) &&
               isElementVisible(menuServicos) &&
               isElementVisible(menuSuasFinancas) &&
               isElementVisible(menuSeusBeneficios) &&
               isElementVisible(menuSuaSeguranca) &&
               isElementVisible(menuStories);
    }
    
    // ========== MÉTODOS DE VALIDAÇÃO - REDES SOCIAIS ==========
    
    /**
     * Verifica se todos os ícones de redes sociais estão visíveis
     */
    @Step("Verificar ícones de redes sociais")
    public boolean todasRedesSociaisVisiveis() {
        scrollToElement(linkFacebook);
        return isElementVisible(linkFacebook) &&
               isElementVisible(linkInstagram) &&
               isElementVisible(linkLinkedin) &&
               isElementVisible(linkTiktok);
    }
    
    /**
     * Verifica se Facebook está visível
     */
    @Step("Verificar link Facebook")
    public boolean isLinkFacebookVisivel() {
        scrollToElement(linkFacebook);
        return isElementVisible(linkFacebook);
    }
    
    /**
     * Verifica se Instagram está visível
     */
    @Step("Verificar link Instagram")
    public boolean isLinkInstagramVisivel() {
        scrollToElement(linkInstagram);
        return isElementVisible(linkInstagram);
    }
    
    /**
     * Verifica se LinkedIn está visível
     */
    @Step("Verificar link LinkedIn")
    public boolean isLinkLinkedinVisivel() {
        scrollToElement(linkLinkedin);
        return isElementVisible(linkLinkedin);
    }
    
    /**
     * Verifica se TikTok está visível
     */
    @Step("Verificar link TikTok")
    public boolean isLinkTiktokVisivel() {
        scrollToElement(linkTiktok);
        return isElementVisible(linkTiktok);
    }
    
    // ========== MÉTODOS DE VALIDAÇÃO - FOOTER ==========
    
    /**
     * Verifica se todos os links do footer estão visíveis
     */
    @Step("Verificar links do footer")
    public boolean todosLinksFooterVisiveis() {
        scrollToElement(footerMenuOAgibank);
        return isElementVisible(footerMenuOAgibank) &&
               isElementVisible(footerMenuProdutos) &&
               isElementVisible(footerMenuServicos) &&
               isElementVisible(footerMenuSuasFinancas) &&
               isElementVisible(footerMenuSeusBeneficios) &&
               isElementVisible(footerMenuSuaSeguranca) &&
               isElementVisible(footerMenuStories);
    }
    
    /**
     * Validação completa da página de resultados
     */
    @Step("Validar página de resultados completa")
    public boolean validarPaginaCompleta() {
        boolean menusOk = todosMenusPrincipaisVisiveis();
        boolean widgetsOk = isBannerConsignadoVisivel() && isWidgetNewsletterVisivel();
        boolean redesSociaisOk = todasRedesSociaisVisiveis();
        boolean footerOk = todosLinksFooterVisiveis();
        
        System.out.println("Menus principais: " + menusOk);
        System.out.println("Widgets laterais: " + widgetsOk);
        System.out.println("Redes sociais: " + redesSociaisOk);
        System.out.println("Links footer: " + footerOk);
        
        return menusOk && widgetsOk && redesSociaisOk && footerOk;
    }
}
