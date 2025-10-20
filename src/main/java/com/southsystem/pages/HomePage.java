package com.southsystem.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * Page Object para a página inicial do Blog do Agi Representa todos os elementos e ações da home
 * page
 */
public class HomePage extends BasePage {

    // ========== LOCATORS ==========

    // Cabeçalho
    private final By logo = By.cssSelector("img[alt*='logo'], .site-logo, .custom-logo");
    private final By searchIcon = By.cssSelector("a.astra-search-icon, a.slide-search");
    private final By menuPrincipal = By.cssSelector("nav.main-navigation, .primary-menu");
    private final By menuItens = By.cssSelector("nav.main-navigation li, .primary-menu li");

    // Menus principais do cabeçalho
    private final By menuOAgibank = By.xpath("//span[contains(text(), 'O Agibank')]");
    private final By menuProdutos = By.xpath("//span[contains(text(), 'Produtos')]");
    private final By menuServicos =
            By.xpath("//span[contains(text(), 'Serviços') or contains(text(), 'Servicos')]");
    private final By menuSuasFinancas = By.xpath(
            "//span[contains(text(), 'Suas finanças') or contains(text(), 'Suas financas')]");
    private final By menuSeusBeneficios = By.xpath(
            "//span[contains(text(), 'Seus benefícios') or contains(text(), 'Seus beneficios')]");
    private final By menuSuaSeguranca = By.xpath(
            "//span[contains(text(), 'Sua segurança') or contains(text(), 'Sua seguranca')]");
    private final By menuStories = By.xpath("//span[contains(text(), 'Stories')]");

    // Submenu "O Agibank"
    private final By submenuColunas = By.xpath("//span[contains(text(), 'Colunas')]");
    private final By submenuNoticias =
            By.xpath("//span[contains(text(), 'Notícias') or contains(text(), 'Noticias')]");
    private final By submenuCarreira = By.xpath("//span[contains(text(), 'Carreira')]");

    // Submenu "Produtos"
    private final By submenuEmprestimos =
            By.xpath("//span[contains(text(), 'Empréstimos') or contains(text(), 'Emprestimos')]");
    private final By submenuContaCorrente = By.xpath("//span[contains(text(), 'Conta Corrente')]");
    private final By submenuCartoes =
            By.xpath("//span[contains(text(), 'Cartões') or contains(text(), 'Cartoes')]");
    private final By submenuSeguros = By.xpath("//span[contains(text(), 'Seguros')]");
    private final By submenuINSS = By.xpath("//span[contains(text(), 'INSS')]");
    private final By submenuConsorcios =
            By.xpath("//span[contains(text(), 'Consórcios') or contains(text(), 'Consorcios')]");
    private final By submenuPix = By.xpath("//span[contains(text(), 'Pix')]");

    // Submenu "Empréstimos" (segundo nível)
    private final By submenuEmprestimoConsignado =
            By.xpath("//span[contains(text(), 'Empréstimo Consignado')]");
    private final By submenuEmprestimoPessoal =
            By.xpath("//span[contains(text(), 'Empréstimo Pessoal')]");
    private final By submenuEmprestimoContaLuz =
            By.xpath("//span[contains(text(), 'Empréstimo na Conta de Luz')]");

    // Submenu "Serviços"
    private final By submenuPortabilidade = By.xpath("//span[contains(text(), 'Portabilidade')]");
    private final By submenuOpenFinance = By.xpath("//span[contains(text(), 'Open Finance')]");

    // Submenu "Suas finanças"
    private final By submenuPlanejamentoFinanceiro =
            By.xpath("//span[contains(text(), 'Planejamento Financeiro')]");
    private final By submenuEducacaoFinanceira = By.xpath(
            "//span[contains(text(), 'Educação Financeira') or contains(text(), 'Educacao Financeira')]");

    // Submenu "Conta Digital"
    private final By submenuContaDigital = By.xpath("//span[contains(text(), 'Conta Digital')]");

    // Widgets laterais
    private final By bannerConsignado =
            By.xpath("//*[contains(text(), 'Simule hoje seu Consignado')]");
    private final By widgetNewsletter =
            By.xpath("//*[contains(text(), 'Se inscreva em nossa Newsletter')]");
    private final By campoEmailNewsletter = By.cssSelector(
            "input[type='email'], input[placeholder*='email' or @placeholder*='e-mail']");
    private final By botaoAssinar = By.xpath("//button[contains(text(), 'Assinar')]");

    // Conteúdo Principal
    private final By artigosDestaque = By.cssSelector("article, .post");
    private final By titulosArtigos = By.cssSelector("h2.entry-title, .post-title");
    private final By imagensArtigos = By.cssSelector("article img, .post-image");

    // Sidebar
    private final By sidebar = By.cssSelector("aside, .sidebar");
    private final By widgetBusca = By.cssSelector(".widget_search");
    private final By widgetCategorias = By.cssSelector(".widget_categories");

    // Rodapé
    private final By footer = By.cssSelector("footer, .site-footer");
    private final By footerLinks = By.cssSelector("footer a");
    private final By redesSociais = By.cssSelector(".social-links a, .social-icons a");

    // Redes sociais específicas
    private final By linkFacebook = By.xpath("//a[@aria-label='Facebook']");
    private final By linkInstagram = By.xpath("//a[@aria-label='Instagram']");
    private final By linkLinkedin = By.xpath("//a[@aria-label='LinkedIn']");
    private final By linkTiktok = By.xpath("//a[@aria-label='TikTok']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ========== MÉTODOS DE NAVEGAÇÃO ==========

    /**
     * Navega para a página inicial
     */
    @Step("Acessar página inicial do Blog do Agi")
    public HomePage acessarPaginaInicial(String url) {
        driver.get(url);
        waitForPageLoad();
        return this;
    }

    // ========== MÉTODOS DE INTERAÇÃO - CABEÇALHO ==========

    /**
     * Clica no logo para voltar à home
     */
    @Step("Clicar no logo")
    public HomePage clicarLogo() {
        clickElement(logo);
        return this;
    }

    /**
     * Clica no ícone de busca
     */
    @Step("Clicar na lupa de busca")
    public SearchPage clicarLupaBusca() {
        waitForElementClickable(searchIcon);
        clickElementWithJS(searchIcon);
        // SearchPage aguardará o campo de busca estar visível no seu construtor
        return new SearchPage(driver);
    }

    /**
     * Obtém lista de itens do menu principal
     */
    @Step("Obter itens do menu")
    public List<WebElement> getItensMenu() {
        return driver.findElements(menuItens);
    }

    /**
     * Clica em um item do menu pelo texto
     */
    @Step("Clicar no item do menu: {textoItem}")
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
     * Passa o mouse sobre o menu "O Agibank"
     */
    @Step("Passar mouse sobre o menu O Agibank")
    public HomePage clicarMenuOAgibank() {
        hoverElement(menuOAgibank);
        // Aguarda o submenu aparecer verificando visibilidade do primeiro item
        waitForElementVisible(submenuColunas);
        return this;
    }

    /**
     * Passa o mouse sobre o menu "Produtos"
     */
    @Step("Passar mouse sobre o menu Produtos")
    public HomePage clicarMenuProdutos() {
        hoverElement(menuProdutos);
        // Aguarda o submenu aparecer verificando visibilidade do primeiro item
        waitForElementVisible(submenuEmprestimos);
        return this;
    }

    /**
     * Passa o mouse sobre o menu "Serviços"
     */
    @Step("Passar mouse sobre o menu Serviços")
    public HomePage clicarMenuServicos() {
        hoverElement(menuServicos);
        return this;
    }

    /**
     * Passa o mouse sobre o menu "Suas finanças"
     */
    @Step("Passar mouse sobre o menu Suas finanças")
    public HomePage clicarMenuSuasFinancas() {
        hoverElement(menuSuasFinancas);
        return this;
    }

    /**
     * Passa o mouse sobre o menu "Seus benefícios"
     */
    @Step("Passar mouse sobre o menu Seus benefícios")
    public HomePage clicarMenuSeusBeneficios() {
        hoverElement(menuSeusBeneficios);
        return this;
    }

    /**
     * Passa o mouse sobre o menu "Sua segurança"
     */
    @Step("Passar mouse sobre o menu Sua segurança")
    public HomePage clicarMenuSuaSeguranca() {
        hoverElement(menuSuaSeguranca);
        return this;
    }

    /**
     * Passa o mouse sobre o menu "Stories"
     */
    @Step("Passar mouse sobre o menu Stories")
    public HomePage clicarMenuStories() {
        hoverElement(menuStories);
        return this;
    }

    /**
     * Clica no submenu "Colunas"
     */
    @Step("Clicar no submenu Colunas")
    public void clicarSubmenuColunas() {
        clicarMenuOAgibank();
        clickElement(submenuColunas);
    }

    /**
     * Clica no submenu "Notícias"
     */
    @Step("Clicar no submenu Notícias")
    public void clicarSubmenuNoticias() {
        clicarMenuOAgibank();
        clickElement(submenuNoticias);
    }

    /**
     * Clica no submenu "Carreira"
     */
    @Step("Clicar no submenu Carreira")
    public void clicarSubmenuCarreira() {
        clicarMenuOAgibank();
        clickElement(submenuCarreira);
    }

    /**
     * Passa o mouse sobre o submenu "Empréstimos" para revelar submenus de segundo nível
     */
    @Step("Passar mouse sobre submenu Empréstimos")
    public HomePage clicarSubmenuEmprestimos() {
        hoverElement(submenuEmprestimos);
        // Aguarda submenu de segundo nível aparecer
        waitForElementVisible(submenuEmprestimoConsignado);
        return this;
    }

    /**
     * Clica no submenu "Conta Corrente"
     */
    @Step("Clicar no submenu Conta Corrente")
    public void clicarSubmenuContaCorrente() {
        clicarMenuProdutos();
        clickElement(submenuContaCorrente);
    }

    /**
     * Clica no submenu "Empréstimo Consignado"
     */
    @Step("Clicar no submenu Empréstimo Consignado")
    public void clicarSubmenuEmprestimoConsignado() {
        clicarMenuProdutos();
        waitForElementVisible(submenuEmprestimos);
        clickElement(submenuEmprestimos);
        clickElement(submenuEmprestimoConsignado);
    }

    /**
     * Clica no submenu "Empréstimo Pessoal"
     */
    @Step("Clicar no submenu Empréstimo Pessoal")
    public void clicarSubmenuEmprestimoPessoal() {
        clicarMenuProdutos();
        waitForElementVisible(submenuEmprestimos);
        clickElement(submenuEmprestimos);
        clickElement(submenuEmprestimoPessoal);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - MENUS ==========

    /**
     * Verifica se todos os menus principais estão visíveis
     */
    @Step("Verificar se todos os menus principais estão visíveis")
    public boolean todosMenusPrincipaisVisiveis() {
        return isElementVisible(menuOAgibank) && isElementVisible(menuProdutos)
                && isElementVisible(menuServicos) && isElementVisible(menuSuasFinancas)
                && isElementVisible(menuSeusBeneficios) && isElementVisible(menuSuaSeguranca)
                && isElementVisible(menuStories);
    }

    /**
     * Verifica se menu específico está visível
     */
    @Step("Verificar se menu está visível: {nomeMenu}")
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

    /**
     * Verifica se submenus de "O Agibank" estão presentes
     */
    @Step("Verificar submenus de O Agibank")
    public boolean submenuOAgibankPresente() {
        clicarMenuOAgibank();
        boolean presente = isElementVisible(submenuColunas) && isElementVisible(submenuNoticias)
                && isElementVisible(submenuCarreira);
        clicarMenuOAgibank(); // Fecha o menu
        return presente;
    }

    /**
     * Verifica se submenus de "Produtos" estão presentes
     */
    @Step("Verificar submenus de Produtos")
    public boolean submenuProdutosPresente() {
        clicarMenuProdutos();
        boolean presente = isElementVisible(submenuEmprestimos)
                && isElementVisible(submenuContaCorrente) && isElementVisible(submenuCartoes)
                && isElementVisible(submenuSeguros) && isElementVisible(submenuINSS)
                && isElementVisible(submenuConsorcios) && isElementVisible(submenuPix);
        clicarMenuProdutos(); // Fecha o menu
        return presente;
    }

    /**
     * Verifica se submenus de segundo nível de "Empréstimos" estão presentes
     */
    @Step("Verificar submenus de Empréstimos")
    public boolean submenuEmprestimosPresente() {
        clicarMenuProdutos();
        waitForElementVisible(submenuEmprestimos);
        clickElement(submenuEmprestimos);
        boolean presente = isElementVisible(submenuEmprestimoConsignado)
                && isElementVisible(submenuEmprestimoPessoal)
                && isElementVisible(submenuEmprestimoContaLuz);
        clicarMenuProdutos(); // Fecha o menu
        return presente;
    }

    /**
     * Verifica se banner de Consignado está presente
     */
    @Step("Verificar se banner de Consignado está presente")
    public boolean isBannerConsignadoVisivel() {
        return isElementVisible(bannerConsignado);
    }

    /**
     * Verifica se widget de Newsletter está presente
     */
    @Step("Verificar se widget de Newsletter está presente")
    public boolean isWidgetNewsletterVisivel() {
        scrollToElement(widgetNewsletter);
        return isElementVisible(widgetNewsletter);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - CONTEÚDO ==========

    /**
     * Verifica se artigos estão sendo exibidos
     */
    @Step("Verificar se artigos estão visíveis")
    public boolean temArtigosVisiveis() {
        return !driver.findElements(artigosDestaque).isEmpty();
    }

    /**
     * Obtém quantidade de artigos na página
     */
    @Step("Contar artigos na página")
    public int getQuantidadeArtigos() {
        return driver.findElements(artigosDestaque).size();
    }

    /**
     * Obtém títulos de todos os artigos visíveis
     */
    @Step("Obter títulos dos artigos")
    public List<WebElement> getTitulosArtigos() {
        return driver.findElements(titulosArtigos);
    }

    /**
     * Clica no primeiro artigo
     */
    @Step("Clicar no primeiro artigo")
    public void clicarPrimeiroArtigo() {
        List<WebElement> titulos = getTitulosArtigos();
        if (!titulos.isEmpty()) {
            titulos.get(0).click();
        } else {
            throw new RuntimeException("Nenhum artigo encontrado para clicar");
        }
    }

    /**
     * Verifica se imagens dos artigos estão carregadas
     */
    @Step("Verificar se imagens dos artigos estão carregadas")
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

    /**
     * Verifica se sidebar está visível
     */
    @Step("Verificar se sidebar está visível")
    public boolean isSidebarVisivel() {
        return isElementVisible(sidebar);
    }

    /**
     * Verifica se widget de busca está presente
     */
    @Step("Verificar se widget de busca está presente")
    public boolean temWidgetBusca() {
        return isElementVisible(widgetBusca);
    }

    /**
     * Verifica se widget de categorias está presente
     */
    @Step("Verificar se widget de categorias está presente")
    public boolean temWidgetCategorias() {
        return isElementVisible(widgetCategorias);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - RODAPÉ ==========

    /**
     * Verifica se rodapé está visível
     */
    @Step("Verificar se rodapé está visível")
    public boolean isFooterVisivel() {
        scrollToElement(footer);
        return isElementVisible(footer);
    }

    /**
     * Obtém links do rodapé
     */
    @Step("Obter links do rodapé")
    public List<WebElement> getLinksFooter() {
        scrollToElement(footer);
        return driver.findElements(footerLinks);
    }

    /**
     * Verifica se redes sociais estão presentes
     */
    @Step("Verificar se links de redes sociais estão presentes")
    public boolean temRedesSociais() {
        scrollToElement(footer);
        return !driver.findElements(redesSociais).isEmpty();
    }

    /**
     * Verifica se todos os ícones de redes sociais estão visíveis
     */
    @Step("Verificar ícones de redes sociais")
    public boolean todasRedesSociaisVisiveis() {
        scrollToElement(linkFacebook);
        return isElementVisible(linkFacebook) && isElementVisible(linkInstagram)
                && isElementVisible(linkLinkedin) && isElementVisible(linkTiktok);
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

    // ========== MÉTODOS DE VALIDAÇÃO - PÁGINA ==========

    /**
     * Verifica se a página inicial foi carregada corretamente
     */
    @Step("Verificar se a página inicial foi carregada")
    public boolean isPaginaInicialCarregada() {
        String url = driver.getCurrentUrl();
        String title = driver.getTitle();

        boolean urlCorreta =
                url.toLowerCase().contains("agi") || url.toLowerCase().contains("blog");
        boolean tituloValido = !title.isEmpty();
        boolean temArtigos = temArtigosVisiveis();

        System.out.println("URL: " + url);
        System.out.println("Título: " + title);
        System.out.println("Tem artigos: " + temArtigos);

        return urlCorreta && tituloValido && temArtigos;
    }

    /**
     * Validação completa da página inicial
     */
    @Step("Validar página inicial completa")
    public boolean validarPaginaCompleta() {
        boolean menusOk = todosMenusPrincipaisVisiveis();
        boolean widgetsOk = isBannerConsignadoVisivel() && isWidgetNewsletterVisivel();
        boolean redesSociaisOk = todasRedesSociaisVisiveis();
        boolean artigosOk = temArtigosVisiveis();

        System.out.println("Menus principais: " + menusOk);
        System.out.println("Widgets laterais: " + widgetsOk);
        System.out.println("Redes sociais: " + redesSociaisOk);
        System.out.println("Artigos: " + artigosOk);

        return menusOk && widgetsOk && redesSociaisOk && artigosOk;
    }

    // ========== MÉTODOS DE VALIDAÇÃO INDIVIDUAL - MENUS ==========

    @Step("Verificar menu O Agibank")
    public boolean isMenuOAgibankVisivel() {
        return isElementVisible(menuOAgibank);
    }

    @Step("Verificar menu Produtos")
    public boolean isMenuProdutosVisivel() {
        return isElementVisible(menuProdutos);
    }

    @Step("Verificar menu Serviços")
    public boolean isMenuServicosVisivel() {
        return isElementVisible(menuServicos);
    }

    @Step("Verificar menu Suas finanças")
    public boolean isMenuSuasFinancasVisivel() {
        return isElementVisible(menuSuasFinancas);
    }

    @Step("Verificar menu Seus benefícios")
    public boolean isMenuSeusBeneficiosVisivel() {
        return isElementVisible(menuSeusBeneficios);
    }

    @Step("Verificar menu Sua segurança")
    public boolean isMenuSuaSegurancaVisivel() {
        return isElementVisible(menuSuaSeguranca);
    }

    @Step("Verificar menu Stories")
    public boolean isMenuStoriesVisivel() {
        return isElementVisible(menuStories);
    }

    // ========== MÉTODOS DE VALIDAÇÃO INDIVIDUAL - SUBMENUS ==========

    @Step("Verificar submenu Colunas")
    public boolean isSubmenuColunasVisivel() {
        return isElementVisible(submenuColunas);
    }

    @Step("Verificar submenu Notícias")
    public boolean isSubmenuNoticiasVisivel() {
        return isElementVisible(submenuNoticias);
    }

    @Step("Verificar submenu Carreira")
    public boolean isSubmenuCarreiraVisivel() {
        return isElementVisible(submenuCarreira);
    }

    @Step("Verificar submenu Empréstimos")
    public boolean isSubmenuEmprestimosVisivel() {
        return isElementVisible(submenuEmprestimos);
    }

    @Step("Verificar submenu Conta Corrente")
    public boolean isSubmenuContaCorrenteVisivel() {
        return isElementVisible(submenuContaCorrente);
    }

    @Step("Verificar submenu Cartões")
    public boolean isSubmenuCartoesVisivel() {
        return isElementVisible(submenuCartoes);
    }

    @Step("Verificar submenu Seguros")
    public boolean isSubmenuSegurosVisivel() {
        return isElementVisible(submenuSeguros);
    }

    @Step("Verificar submenu INSS")
    public boolean isSubmenuINSSVisivel() {
        return isElementVisible(submenuINSS);
    }

    @Step("Verificar submenu Consórcios")
    public boolean isSubmenuConsorciosVisivel() {
        return isElementVisible(submenuConsorcios);
    }

    @Step("Verificar submenu Pix")
    public boolean isSubmenuPixVisivel() {
        return isElementVisible(submenuPix);
    }

    @Step("Verificar submenu Portabilidade")
    public boolean isSubmenuPortabilidadeVisivel() {
        return isElementVisible(submenuPortabilidade);
    }

    @Step("Verificar submenu Open Finance")
    public boolean isSubmenuOpenFinanceVisivel() {
        return isElementVisible(submenuOpenFinance);
    }

    @Step("Verificar submenu Planejamento Financeiro")
    public boolean isSubmenuPlanejamentoFinanceiroVisivel() {
        return isElementVisible(submenuPlanejamentoFinanceiro);
    }

    @Step("Verificar submenu Educação Financeira")
    public boolean isSubmenuEducacaoFinanceiraVisivel() {
        return isElementVisible(submenuEducacaoFinanceira);
    }

    @Step("Verificar submenu Conta Digital")
    public boolean isSubmenuContaDigitalVisivel() {
        return isElementVisible(submenuContaDigital);
    }

    // ========== MÉTODOS DE VALIDAÇÃO - SUBMENUS DE SEGUNDO NÍVEL ==========

    @Step("Verificar submenu Empréstimo Consignado")
    public boolean isSubmenuEmprestimoConsignadoVisivel() {
        return isElementVisible(submenuEmprestimoConsignado);
    }

    @Step("Verificar submenu Empréstimo Pessoal")
    public boolean isSubmenuEmprestimoPessoalVisivel() {
        return isElementVisible(submenuEmprestimoPessoal);
    }

    @Step("Verificar submenu Empréstimo na Conta de Luz")
    public boolean isSubmenuEmprestimoContaLuzVisivel() {
        return isElementVisible(submenuEmprestimoContaLuz);
    }

    // ========== MÉTODOS DE VALIDAÇÃO INDIVIDUAL - REDES SOCIAIS ==========

    @Step("Verificar link Facebook")
    public boolean isLinkFacebookVisivel() {
        scrollToElement(linkFacebook);
        return isElementVisible(linkFacebook);
    }

    @Step("Verificar link Instagram")
    public boolean isLinkInstagramVisivel() {
        scrollToElement(linkInstagram);
        return isElementVisible(linkInstagram);
    }

    @Step("Verificar link LinkedIn")
    public boolean isLinkLinkedinVisivel() {
        scrollToElement(linkLinkedin);
        return isElementVisible(linkLinkedin);
    }

    @Step("Verificar link TikTok")
    public boolean isLinkTiktokVisivel() {
        scrollToElement(linkTiktok);
        return isElementVisible(linkTiktok);
    }
}
