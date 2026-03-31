package com.agibank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchResultPage extends BasePage {

    private final By tituloResultados = By.xpath("//h1[contains(text(), 'Resultados encontrados para:')]");
    private final By termoBuscado = By.xpath("//h1/following-sibling::*");
    private final By mensagemNenhumResultado = By.xpath("//p[contains(text(), 'Lamentamos, mas nada foi encontrado')]");
    private final By resultados = By.cssSelector("article, .post");
    private final By titulosResultados = By.cssSelector("article h2, .entry-title");

    private final By bannerConsignado = By.xpath("//*[contains(text(), 'Simule hoje seu Consignado')]");
    private final By widgetNewsletter = By.xpath("//*[contains(text(), 'Inscreva-se em nossa Newsletter')]");
    private final By campoEmailNewsletter = By.xpath(
            "//input[@type='email' or contains(@placeholder,'email') or contains(@placeholder,'e-mail')]");
    private final By botaoAssinar = By.xpath("//button[contains(text(), 'Assinar')]");

    // Menus principais — Astra renderiza o texto em <span> dentro de <a>,
    // então usamos //span[...] igual ao HomePage para garantir match correto.
    private final By menuOAgibank = By.xpath("//span[contains(text(), 'O Agibank')]");
    private final By menuProdutos = By.xpath("//span[contains(text(), 'Produtos')]");
    private final By menuServicos = By.xpath(
            "//span[contains(text(), 'Serviços') or contains(text(), 'Servicos')]");
    private final By menuSuasFinancas = By.xpath(
            "//span[contains(text(), 'Suas finanças') or contains(text(), 'Suas financas')]");
    private final By menuSeusBeneficios = By.xpath(
            "//span[contains(text(), 'Seus benefícios') or contains(text(), 'Seus beneficios')]");
    private final By menuSuaSeguranca = By.xpath(
            "//span[contains(text(), 'Sua segurança') or contains(text(), 'Sua seguranca')]");
    private final By menuStories = By.xpath("//span[contains(text(), 'Stories')]");

    // Redes sociais do rodapé
    private final By linkFacebook = By.cssSelector("a[href*='facebook']");
    private final By linkInstagram = By.cssSelector("a[href*='instagram']");
    private final By linkLinkedin = By.cssSelector("a[href*='linkedin']");
    private final By linkTiktok = By.cssSelector("a[href*='tiktok']");

    // Links do rodapé — no Astra theme o footer usa <a> com texto direto (sem
    // <span>)
    private final By footerMenuOAgibank = By.xpath("//footer//a[contains(text(), 'O Agibank')]");
    private final By footerMenuProdutos = By.xpath("//footer//a[contains(text(), 'Produtos')]");
    private final By footerMenuServicos = By.xpath(
            "//footer//a[contains(text(), 'Servi\u00e7os') or contains(text(), 'Servicos')]");
    private final By footerMenuSuasFinancas = By.xpath(
            "//footer//a[contains(text(), 'Suas finan\u00e7as') or contains(text(), 'Suas financas')]");
    private final By footerMenuSeusBeneficios = By.xpath(
            "//footer//a[contains(text(), 'Seus benef\u00edcios') or contains(text(), 'Seus beneficios')]");
    private final By footerMenuSuaSeguranca = By.xpath(
            "//footer//a[contains(text(), 'Sua seguran\u00e7a') or contains(text(), 'Sua seguranca')]");
    private final By footerMenuStories = By.xpath("//footer//a[contains(text(), 'Stories')]");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - CABEÇALHO ==========

    public boolean isTituloResultadosVisivel() {
        return isElementVisible(tituloResultados);
    }

    public String getTermoBuscado() {
        return getElementText(termoBuscado);
    }

    public boolean isMensagemNenhumResultadoVisivel() {
        return isElementVisible(mensagemNenhumResultado);
    }

    public boolean temResultados() {
        return !driver.findElements(resultados).isEmpty();
    }

    public int getQuantidadeResultados() {
        return driver.findElements(resultados).size();
    }

    // ========== MÉTODOS DE VALIDAÇÃO - WIDGETS ==========

    public boolean isBannerConsignadoVisivel() {
        return isElementVisible(bannerConsignado);
    }

    public boolean isWidgetNewsletterVisivel() {
        return isElementVisible(widgetNewsletter);
    }

    public boolean isCampoEmailNewsletterVisivel() {
        return isElementVisible(campoEmailNewsletter);
    }

    public boolean isBotaoAssinarVisivel() {
        return isElementVisible(botaoAssinar);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - MENUS ==========

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

    public boolean todasRedesSociaisVisiveis() {
        scrollToElement(linkFacebook);
        return isElementVisible(linkFacebook) &&
                isElementVisible(linkInstagram) &&
                isElementVisible(linkLinkedin) &&
                isElementVisible(linkTiktok);
    }

    public boolean isLinkFacebookVisivel() {
        scrollToElement(linkFacebook);
        return isElementVisible(linkFacebook);
    }

    public boolean isLinkInstagramVisivel() {
        scrollToElement(linkInstagram);
        return isElementVisible(linkInstagram);
    }

    public boolean isLinkLinkedinVisivel() {
        scrollToElement(linkLinkedin);
        return isElementVisible(linkLinkedin);
    }

    public boolean isLinkTiktokVisivel() {
        scrollToElement(linkTiktok);
        return isElementVisible(linkTiktok);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - FOOTER ==========

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

    public boolean validarPaginaCompleta() {
        boolean menusOk = todosMenusPrincipaisVisiveis();
        boolean widgetsOk = isBannerConsignadoVisivel() && isWidgetNewsletterVisivel();
        boolean redesSociaisOk = todasRedesSociaisVisiveis();
        boolean footerOk = todosLinksFooterVisiveis();

        log.info("Menus principais: {}", menusOk);
        log.info("Widgets laterais: {}", widgetsOk);
        log.info("Redes sociais: {}", redesSociaisOk);
        log.info("Links footer: {}", footerOk);

        return menusOk && widgetsOk && redesSociaisOk && footerOk;
    }
}
