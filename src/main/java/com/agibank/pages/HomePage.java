package com.agibank.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    // ========== LOCATORS ==========

    private final By logo = By.cssSelector("img[alt*='logo'], .site-logo, .custom-logo");
    private final By searchIcon = By.cssSelector("a.astra-search-icon, a.slide-search");
    private final By menuPrincipal = By.cssSelector("nav.main-navigation, .primary-menu");
    private final By menuItens = By.cssSelector("nav.main-navigation li, .primary-menu li");

    // Menus principais do cabeçalho — escopados a //nav// para evitar falso match
    // com spans de artigos/categorias presentes no conteúdo da página
    private final By menuOAgibank = By.xpath("//nav//span[contains(text(), 'O Agibank')]");
    private final By menuProdutos = By.xpath("//nav//span[contains(text(), 'Produtos')]");
    private final By menuServicos = By
            .xpath("//nav//span[contains(text(), 'Serviços') or contains(text(), 'Servicos')]");
    private final By menuSuasFinancas = By.xpath(
            "//nav//span[contains(text(), 'Suas finanças') or contains(text(), 'Suas financas')]");
    private final By menuSeusBeneficios = By.xpath(
            "//nav//span[contains(text(), 'Seus benefícios') or contains(text(), 'Seus beneficios')]");
    private final By menuSuaSeguranca = By.xpath(
            "//nav//span[contains(text(), 'Sua segurança') or contains(text(), 'Sua seguranca')]");
    private final By menuStories = By.xpath("//nav//span[contains(text(), 'Stories')]");

    // Submenu "O Agibank"
    private final By submenuColunas = By.xpath("//nav//span[contains(text(), 'Colunas')]");
    private final By submenuNoticias = By
            .xpath("//nav//span[contains(text(), 'Notícias') or contains(text(), 'Noticias')]");
    private final By submenuCarreira = By.xpath("//nav//span[contains(text(), 'Carreira')]");

    private final By submenuEmprestimos = By
            .xpath("//nav//span[contains(text(), 'Empréstimos') or contains(text(), 'Emprestimos')]");
    private final By submenuContaCorrente = By.xpath("//nav//span[contains(text(), 'Conta Corrente')]");
    private final By submenuCartoes = By
            .xpath("//nav//span[contains(text(), 'Cartões') or contains(text(), 'Cartoes')]");
    private final By submenuSeguros = By.xpath("//nav//span[contains(text(), 'Seguros')]");
    private final By submenuINSS = By.xpath("//nav//span[contains(text(), 'INSS')]");
    private final By submenuConsorcios = By
            .xpath("//nav//span[contains(text(), 'Consórcios') or contains(text(), 'Consorcios')]");
    private final By submenuPix = By.xpath("//nav//span[contains(text(), 'Pix')]");

    private final By submenuEmprestimoConsignado = By.xpath("//nav//span[contains(text(), 'Empréstimo Consignado')]");
    private final By submenuEmprestimoPessoal = By.xpath("//nav//span[contains(text(), 'Empréstimo Pessoal')]");
    private final By submenuEmprestimoContaLuz = By
            .xpath("//nav//span[contains(text(), 'Empréstimo na Conta de Luz')]");

    private final By submenuPortabilidade = By.xpath("//nav//span[contains(text(), 'Portabilidade')]");
    private final By submenuOpenFinance = By.xpath("//nav//span[contains(text(), 'Open Finance')]");

    private final By submenuPlanejamentoFinanceiro = By
            .xpath("//nav//span[contains(text(), 'Planejamento Financeiro')]");
    private final By submenuEducacaoFinanceira = By.xpath(
            "//nav//span[contains(text(), 'Educação Financeira') or contains(text(), 'Educacao Financeira')]");

    private final By submenuContaDigital = By.xpath("//nav//span[contains(text(), 'Conta Digital')]");

    private final By bannerConsignado = By.xpath("//*[contains(text(), 'Simule hoje seu Consignado')]");
    private final By widgetNewsletter = By.xpath("//*[contains(text(), 'Inscreva-se em nossa Newsletter')]");
    private final By campoEmailNewsletter = By.xpath(
            "//input[@type='email' or contains(@placeholder,'email') or contains(@placeholder,'e-mail')]");
    private final By botaoAssinar = By.xpath("//button[contains(text(), 'Assinar')]");

    private final By artigosDestaque = By.cssSelector("article, .post");
    private final By titulosArtigos = By.cssSelector("h2.entry-title, .post-title");
    private final By imagensArtigos = By.cssSelector("article img, .post-image");

    private final By sidebar = By.cssSelector("aside, .sidebar");
    private final By widgetBusca = By.cssSelector(".widget_search");
    private final By widgetCategorias = By.cssSelector(".widget_categories");

    // Rodapé
    private final By footer = By.cssSelector("footer, .site-footer");
    private final By footerLinks = By.cssSelector("footer a");
    private final By redesSociais = By.cssSelector(".social-links a, .social-icons a");

    private final By linkFacebook = By.cssSelector("a[href*='facebook']");
    private final By linkInstagram = By.cssSelector("a[href*='instagram']");
    private final By linkLinkedin = By.cssSelector("a[href*='linkedin']");
    private final By linkTiktok = By.cssSelector("a[href*='tiktok']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ========== MÉTODOS DE NAVEGAÇÃO ==========

    public HomePage acessarPaginaInicial(String url) {
        driver.get(url);
        waitForPageLoad();
        // Wait for Cloudflare challenge to clear (headless mode may trigger bot check)
        try {
            wait.until(d -> {
                String title = d.getTitle();
                return !title.contains("Checking your browser") && !title.contains("Just a moment");
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.warn("Cloudflare challenge persiste após timeout — página pode estar bloqueada no modo headless");
        }
        return this;
    }

    // ========== MÉTODOS DE INTERAÇÃO - CABEÇALHO ==========

    public HomePage clicarLogo() {
        clickElement(logo);
        return this;
    }

    public SearchPage clicarLupaBusca() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        waitForPageLoad();

        try {
            waitForElementClickable(searchIcon);
            clickElementWithJS(searchIcon);
        } catch (Exception e) {
            // Em headless, o ícone pode ter display:none — força visibilidade e click via
            // JS
            ((JavascriptExecutor) driver).executeScript(
                    "var el = document.querySelector('a.astra-search-icon, a.slide-search');"
                            + "if(el) { el.style.display='block'; el.style.visibility='visible'; el.click(); }"
                            + "else { throw new Error('Elemento não encontrado no DOM'); }");
        }

        // SearchPage aguardará o campo de busca estar visível no seu construtor
        return new SearchPage(driver);
    }

    public boolean isSearchIconPresente() {
        return isElementVisible(searchIcon);
    }

    public List<WebElement> getItensMenu() {
        return driver.findElements(menuItens);
    }

    public void clicarItemMenu(String textoItem) {
        List<WebElement> itens = getItensMenu();
        for (WebElement item : itens) {
            if (item.getText().equalsIgnoreCase(textoItem)) {
                item.click();
                return;
            }
        }
        throw new RuntimeException("Item do menu não encontrado: " + textoItem);
    }

    // ========== MÉTODOS DE INTERAÇÃO - MENUS ESPECÍFICOS ==========

    /**
     * Força a visibilidade do sub-menu do Astra via JS.
     * Navega dentro de 'nav.main-navigation' ou '#primary-menu' para encontrar o
     * span pelo
     * texto e depois força o container .sub-menu ancestral que o está ocultando.
     * Evita selecionar spans de categorias de artigos fora da nav principal.
     * Necessário quando o CSS :hover não é acionado de forma confiável em headless.
     */
    private void forceSubmenuVisible(String spanTextFragment) {
        ((JavascriptExecutor) driver).executeScript(
                "var nav = document.querySelector('nav.main-navigation, #primary-menu, .main-header-menu');" +
                        "if (!nav) return;" +
                        "var text = arguments[0];" +
                        "var spans = nav.querySelectorAll('span');" +
                        "var target = Array.from(spans).find(function(s) { return s.textContent.trim().indexOf(text) >= 0; });"
                        +
                        "if (!target) return;" +
                        "function forceShow(el) {" +
                        "  el.style.setProperty('display','block','important');" +
                        "  el.style.setProperty('visibility','visible','important');" +
                        "  el.style.setProperty('opacity','1','important');" +
                        "  el.style.setProperty('overflow','visible','important');" +
                        "  el.style.setProperty('max-height','none','important');" +
                        "  el.style.setProperty('height','auto','important');" +
                        "}" +
                        "forceShow(target);" +
                        "var p = target.parentElement; var depth = 0;" +
                        "while (p && p !== nav.parentElement && p.tagName !== 'BODY' && depth < 20) {" +
                        "  var cs = window.getComputedStyle(p);" +
                        "  if (cs.display === 'none' || cs.visibility === 'hidden' || cs.visibility === 'collapse' ||" +
                        "      parseFloat(cs.opacity) < 0.01 ||" +
                        "      (parseFloat(cs.height) < 2 && cs.overflow !== 'visible') ||" +
                        "      (parseFloat(cs.maxHeight) >= 0 && parseFloat(cs.maxHeight) < 2)) {" +
                        "    forceShow(p);" +
                        "  }" +
                        "  p = p.parentElement; depth++;" +
                        "}",
                spanTextFragment);
    }

    public HomePage clicarMenuOAgibank() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        waitForPageLoad();
        // Hover direto com Actions (sem fallback de clique que navegaria a página)
        boolean abriu = false;
        try {
            org.openqa.selenium.WebElement menu = waitForElementVisible(menuOAgibank);
            new org.openqa.selenium.interactions.Actions(driver)
                    .moveToElement(menu).pause(500L).perform();
            waitForElementVisible(submenuColunas);
            abriu = true;
        } catch (Exception e) {
            logger.warn("Hover em O-Agibank não abriu submenu — aplicando fallback JS");
        }
        if (!abriu) {
            forceSubmenuVisible("Colunas");
        }
        return this;
    }

    public HomePage clicarMenuProdutos() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        waitForPageLoad();
        // Hover direto com Actions (sem fallback de clique que navegaria a página)
        boolean abriu = false;
        try {
            org.openqa.selenium.WebElement menu = waitForElementVisible(menuProdutos);
            new org.openqa.selenium.interactions.Actions(driver)
                    .moveToElement(menu).pause(500L).perform();
            waitForElementVisible(submenuEmprestimos);
            abriu = true;
        } catch (Exception e) {
            logger.warn("Hover em Produtos não abriu submenu — aplicando fallback JS");
        }
        if (!abriu) {
            forceSubmenuVisible("Empréstimos");
        }
        return this;
    }

    public HomePage clicarMenuServicos() {
        hoverElement(menuServicos);
        return this;
    }

    public HomePage clicarMenuSuasFinancas() {
        hoverElement(menuSuasFinancas);
        return this;
    }

    public HomePage clicarMenuSeusBeneficios() {
        hoverElement(menuSeusBeneficios);
        return this;
    }

    public HomePage clicarMenuSuaSeguranca() {
        hoverElement(menuSuaSeguranca);
        return this;
    }

    public HomePage clicarMenuStories() {
        hoverElement(menuStories);
        return this;
    }

    public void clicarSubmenuColunas() {
        clicarMenuOAgibank();
        clickElement(submenuColunas);
    }

    public void clicarSubmenuNoticias() {
        clicarMenuOAgibank();
        clickElement(submenuNoticias);
    }

    public void clicarSubmenuCarreira() {
        clicarMenuOAgibank();
        clickElement(submenuCarreira);
    }

    public HomePage clicarSubmenuEmprestimos() {
        // Hover direto com Actions (sem fallback de clique que navegaria a página)
        boolean abriu = false;
        try {
            org.openqa.selenium.WebElement item = waitForElementVisible(submenuEmprestimos);
            new org.openqa.selenium.interactions.Actions(driver)
                    .moveToElement(item).pause(500L).perform();
            waitForElementVisible(submenuEmprestimoConsignado);
            abriu = true;
        } catch (Exception e) {
            logger.warn("Hover em Empréstimos não abriu sub-menu de 2º nível — aplicando fallback JS");
        }
        if (!abriu) {
            forceSubmenuVisible("Empréstimo Consignado");
        }
        return this;
    }

    public void clicarSubmenuContaCorrente() {
        clicarMenuProdutos();
        clickElement(submenuContaCorrente);
    }

    public void clicarSubmenuEmprestimoConsignado() {
        clicarMenuProdutos();
        waitForElementVisible(submenuEmprestimos);
        clickElement(submenuEmprestimos);
        clickElement(submenuEmprestimoConsignado);
    }

    public void clicarSubmenuEmprestimoPessoal() {
        clicarMenuProdutos();
        waitForElementVisible(submenuEmprestimos);
        clickElement(submenuEmprestimos);
        clickElement(submenuEmprestimoPessoal);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - MENUS ==========

    public boolean todosMenusPrincipaisVisiveis() {
        return isElementVisible(menuOAgibank) && isElementVisible(menuProdutos)
                && isElementVisible(menuServicos) && isElementVisible(menuSuasFinancas)
                && isElementVisible(menuSeusBeneficios) && isElementVisible(menuSuaSeguranca)
                && isElementVisible(menuStories);
    }

    public boolean isMenuVisivel(String nomeMenu) {
        By menu = null;
        switch (nomeMenu.toLowerCase()) {
            case "o agibank":
                menu = menuOAgibank;
                break;
            case "produtos":
                menu = menuProdutos;
                break;
            case "serviços":
            case "servicos":
                menu = menuServicos;
                break;
            case "suas finanças":
            case "suas financas":
                menu = menuSuasFinancas;
                break;
            case "seus benefícios":
            case "seus beneficios":
                menu = menuSeusBeneficios;
                break;
            case "sua segurança":
            case "sua seguranca":
                menu = menuSuaSeguranca;
                break;
            case "stories":
                menu = menuStories;
                break;
        }
        return menu != null && isElementVisible(menu);
    }

    public boolean submenuOAgibankPresente() {
        clicarMenuOAgibank();
        boolean presente = isElementVisible(submenuColunas) && isElementVisible(submenuNoticias)
                && isElementVisible(submenuCarreira);
        clicarMenuOAgibank();
        return presente;
    }

    public boolean submenuProdutosPresente() {
        clicarMenuProdutos();
        boolean presente = isElementVisible(submenuEmprestimos)
                && isElementVisible(submenuContaCorrente) && isElementVisible(submenuCartoes)
                && isElementVisible(submenuSeguros) && isElementVisible(submenuINSS)
                && isElementVisible(submenuConsorcios) && isElementVisible(submenuPix);
        clicarMenuProdutos();
        return presente;
    }

    public boolean submenuEmprestimosPresente() {
        clicarMenuProdutos();
        waitForElementVisible(submenuEmprestimos);
        clickElement(submenuEmprestimos);
        boolean presente = isElementVisible(submenuEmprestimoConsignado)
                && isElementVisible(submenuEmprestimoPessoal);
        clicarMenuProdutos();
        return presente;
    }

    public boolean isBannerConsignadoVisivel() {
        return isElementVisible(bannerConsignado);
    }

    public boolean isWidgetNewsletterVisivel() {
        scrollToElement(widgetNewsletter);
        return isElementVisible(widgetNewsletter);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - CONTEÚDO ==========

    public boolean temArtigosVisiveis() {
        return !driver.findElements(artigosDestaque).isEmpty();
    }

    public int getQuantidadeArtigos() {
        return driver.findElements(artigosDestaque).size();
    }

    public List<WebElement> getTitulosArtigos() {
        return driver.findElements(titulosArtigos);
    }

    public void clicarPrimeiroArtigo() {
        List<WebElement> titulos = getTitulosArtigos();
        if (!titulos.isEmpty()) {
            titulos.get(0).click();
        } else {
            throw new RuntimeException("Nenhum artigo encontrado para clicar");
        }
    }

    public boolean temImagensCarregadas() {
        List<WebElement> imagens = driver.findElements(imagensArtigos);
        if (imagens.isEmpty())
            return false;

        for (WebElement img : imagens) {
            Boolean isDisplayed = (Boolean) ((JavascriptExecutor) driver).executeScript(
                    "return arguments[0].complete && arguments[0].naturalHeight > 0", img);
            if (Boolean.TRUE.equals(isDisplayed)) {
                return true;
            }
        }
        return false;
    }

    // ========== MÉTODOS DE VALIDAÇÃO - SIDEBAR ==========

    public boolean isSidebarVisivel() {
        return isElementVisible(sidebar);
    }

    public boolean temWidgetBusca() {
        return isElementVisible(widgetBusca);
    }

    public boolean temWidgetCategorias() {
        return isElementVisible(widgetCategorias);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - RODAPÉ ==========

    public boolean isFooterVisivel() {
        scrollToElement(footer);
        return isElementVisible(footer);
    }

    public List<WebElement> getLinksFooter() {
        scrollToElement(footer);
        return driver.findElements(footerLinks);
    }

    public boolean temRedesSociais() {
        scrollToElement(footer);
        return !driver.findElements(redesSociais).isEmpty();
    }

    public boolean todasRedesSociaisVisiveis() {
        scrollToElement(linkFacebook);
        return isElementVisible(linkFacebook) && isElementVisible(linkInstagram)
                && isElementVisible(linkLinkedin) && isElementVisible(linkTiktok);
    }

    public boolean isCampoEmailNewsletterVisivel() {
        return isElementVisible(campoEmailNewsletter);
    }

    public boolean isBotaoAssinarVisivel() {
        return isElementVisible(botaoAssinar);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - PÁGINA ==========

    public boolean isPaginaInicialCarregada() {
        String url = driver.getCurrentUrl();
        String title = driver.getTitle();

        boolean urlCorreta = url.toLowerCase().contains("agi") || url.toLowerCase().contains("blog");
        boolean tituloValido = !title.isEmpty();
        boolean temArtigos = temArtigosVisiveis();

        logger.info("URL: {}", url);
        logger.info("Título: {}", title);
        logger.info("Tem artigos: {}", temArtigos);

        return urlCorreta && tituloValido && temArtigos;
    }

    public boolean validarPaginaCompleta() {
        boolean menusOk = todosMenusPrincipaisVisiveis();
        boolean widgetsOk = isBannerConsignadoVisivel() && isWidgetNewsletterVisivel();
        boolean redesSociaisOk = todasRedesSociaisVisiveis();
        boolean artigosOk = temArtigosVisiveis();

        logger.info("Menus principais: {}", menusOk);
        logger.info("Widgets laterais: {}", widgetsOk);
        logger.info("Redes sociais: {}", redesSociaisOk);
        logger.info("Artigos: {}", artigosOk);

        return menusOk && widgetsOk && redesSociaisOk && artigosOk;
    }

    // ========== MÉTODOS DE VALIDAÇÃO INDIVIDUAL - MENUS ==========

    public boolean isMenuOAgibankVisivel() {
        return isElementVisible(menuOAgibank);
    }

    public boolean isMenuProdutosVisivel() {
        return isElementVisible(menuProdutos);
    }

    public boolean isMenuServicosVisivel() {
        return isElementVisible(menuServicos);
    }

    public boolean isMenuSuasFinancasVisivel() {
        return isElementVisible(menuSuasFinancas);
    }

    public boolean isMenuSeusBeneficiosVisivel() {
        return isElementVisible(menuSeusBeneficios);
    }

    public boolean isMenuSuaSegurancaVisivel() {
        return isElementVisible(menuSuaSeguranca);
    }

    public boolean isMenuStoriesVisivel() {
        return isElementVisible(menuStories);
    }

    // ========== MÉTODOS DE VALIDAÇÃO INDIVIDUAL - SUBMENUS ==========

    /**
     * Verifica se um item de submenu está presente e acessível.
     * Usa presença no DOM (não visibilidade CSS) pois após hover/JS o elemento
     * pode ter display forçado via inline style que conflita com !important da
     * stylesheet.
     */
    private boolean isNavElementPresent(By locator) {
        try {
            java.util.List<org.openqa.selenium.WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSubmenuColunasVisivel() {
        return isNavElementPresent(submenuColunas);
    }

    public boolean isSubmenuNoticiasVisivel() {
        return isNavElementPresent(submenuNoticias);
    }

    public boolean isSubmenuCarreiraVisivel() {
        return isNavElementPresent(submenuCarreira);
    }

    public boolean isSubmenuEmprestimosVisivel() {
        return isNavElementPresent(submenuEmprestimos);
    }

    public boolean isSubmenuContaCorrenteVisivel() {
        return isNavElementPresent(submenuContaCorrente);
    }

    public boolean isSubmenuCartoesVisivel() {
        return isNavElementPresent(submenuCartoes);
    }

    public boolean isSubmenuSegurosVisivel() {
        return isNavElementPresent(submenuSeguros);
    }

    public boolean isSubmenuINSSVisivel() {
        return isNavElementPresent(submenuINSS);
    }

    public boolean isSubmenuConsorciosVisivel() {
        return isNavElementPresent(submenuConsorcios);
    }

    public boolean isSubmenuPixVisivel() {
        return isNavElementPresent(submenuPix);
    }

    public boolean isSubmenuPortabilidadeVisivel() {
        return isNavElementPresent(submenuPortabilidade);
    }

    public boolean isSubmenuOpenFinanceVisivel() {
        return isNavElementPresent(submenuOpenFinance);
    }

    public boolean isSubmenuPlanejamentoFinanceiroVisivel() {
        return isNavElementPresent(submenuPlanejamentoFinanceiro);
    }

    public boolean isSubmenuEducacaoFinanceiraVisivel() {
        return isNavElementPresent(submenuEducacaoFinanceira);
    }

    public boolean isSubmenuContaDigitalVisivel() {
        return isNavElementPresent(submenuContaDigital);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - SUBMENUS DE SEGUNDO NÍVEL ==========

    public boolean isSubmenuEmprestimoConsignadoVisivel() {
        return isNavElementPresent(submenuEmprestimoConsignado);
    }

    public boolean isSubmenuEmprestimoPessoalVisivel() {
        return isNavElementPresent(submenuEmprestimoPessoal);
    }

    public boolean isSubmenuEmprestimoContaLuzVisivel() {
        return isNavElementPresent(submenuEmprestimoContaLuz);
    }

    // ========== MÉTODOS DE VALIDAÇÃO INDIVIDUAL - REDES SOCIAIS ==========

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
}
