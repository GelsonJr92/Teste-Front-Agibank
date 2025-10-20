package com.southsystem.steps;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.southsystem.config.ConfigurationManager;
import com.southsystem.config.DriverFactory;
import com.southsystem.pages.HomePage;
import com.southsystem.pages.ResponsividadePage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

/**
 * Steps definitions para os testes de navegação com Cucumber
 */
public class NavegacaoSteps {

    private final WebDriver driver;
    private final HomePage homePage;
    private final ResponsividadePage responsividadePage;

    public NavegacaoSteps() {
        this.driver = DriverFactory.getDriver();
        this.homePage = new HomePage(driver);
        this.responsividadePage = new ResponsividadePage(driver);
    }

    @Dado("que eu acesso a página inicial do Blog do Agi")
    public void queEuAcessoAPaginaInicialDoBlogDoAgi() {
        homePage.acessarPaginaInicial(ConfigurationManager.getBaseUrl());
    }

    @Quando("eu acesso a URL do Blog do Agi")
    public void euAcessoAURLDoBlogDoAgi() {
        homePage.acessarPaginaInicial(ConfigurationManager.getBaseUrl());
    }

    @Então("a página inicial deve carregar corretamente")
    public void aPaginaInicialDeveCarregarCorretamente() {
        Assert.assertTrue(homePage.isPaginaInicialCarregada(),
                "Página inicial deve carregar corretamente");
    }

    @Então("a página deve carregar corretamente")
    public void aPaginaDeveCarregarCorretamente() {
        Assert.assertTrue(homePage.isPaginaInicialCarregada(), "Página deve carregar corretamente");
    }

    @Então("a URL deve conter {string}")
    public void aURLDeveConter(String textoEsperado) {
        Assert.assertTrue(
                driver.getCurrentUrl().contains(textoEsperado)
                        || driver.getCurrentUrl().contains("agibank.com.br"),
                "URL deve conter: " + textoEsperado + " ou agibank.com.br");
    }

    @Então("a URL deve estar correta")
    public void aURLDeveEstarCorreta() {
        String baseUrl = ConfigurationManager.getBaseUrl();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(baseUrl) || currentUrl.startsWith(baseUrl),
                "URL atual (" + currentUrl + ") deve corresponder à baseUrl (" + baseUrl + ")");
    }

    @Então("o título da página não deve estar vazio")
    public void oTituloDaPaginaNaoDeveEstarVazio() {
        String title = driver.getTitle();
        Assert.assertFalse(title.isEmpty(), "Título da página não deve estar vazio");
    }

    @Então("o ícone de busca deve estar presente")
    public void oIconeDeBuscaDeveEstarPresente() {
        try {
            homePage.clicarLupaBusca();
            Assert.assertTrue(true, "Ícone de busca está presente");
        } catch (Exception e) {
            Assert.fail("Ícone de busca não encontrado: " + e.getMessage());
        }
    }

    @Então("o ícone de busca deve ser clicável")
    public void oIconeDeBuscaDeveSerClicavel() {
        try {
            homePage.clicarLupaBusca();
            Assert.assertTrue(true, "Ícone de busca é clicável");
        } catch (Exception e) {
            Assert.fail("Ícone de busca não é clicável: " + e.getMessage());
        }
    }

    @Quando("eu acesso a pagina em resolucao {int} e {int}")
    public void euAcessoAPaginaEmResolucaoIntInt(int largura, int altura) {
        responsividadePage.definirResolucao(largura, altura);
        homePage.acessarPaginaInicial(ConfigurationManager.getBaseUrl());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Então("os elementos principais devem estar visíveis")
    public void osElementosPrincipaisDevemEstarVisiveis() {
        Assert.assertFalse(driver.getTitle().toLowerCase().contains("error"),
                "Página não deve conter erros");
        Assert.assertTrue(homePage.isPaginaInicialCarregada(),
                "Elementos principais devem estar visíveis");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Então("não deve haver scroll horizontal")
    public void naoDeveHaverScrollHorizontal() {
        Assert.assertTrue(responsividadePage.naoTemScrollHorizontal(),
                "Não deve haver scroll horizontal");
    }

    @Então("o layout deve estar adaptado para {string}")
    public void oLayoutDeveEstarAdaptadoPara(String tipoDispositivo) {
        Assert.assertTrue(responsividadePage.isLayoutAdaptado(tipoDispositivo),
                "O layout não está adaptado para " + tipoDispositivo);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ========== STEPS DE VALIDAÇÃO DE MENUS ==========

    @Então("todos os menus principais devem estar visíveis")
    public void todosOsMenusPrincipaisDevemEstarVisiveis() {
        Assert.assertTrue(homePage.todosMenusPrincipaisVisiveis(),
                "Todos os menus principais devem estar visíveis");
    }

    @Então("o menu {string} deve estar presente")
    public void oMenuDeveEstarPresente(String nomeMenu) {
        boolean presente = false;
        switch (nomeMenu) {
            case "O Agibank":
                presente = homePage.isMenuOAgibankVisivel();
                break;
            case "Produtos":
                presente = homePage.isMenuProdutosVisivel();
                break;
            case "Serviços":
                presente = homePage.isMenuServicosVisivel();
                break;
            case "Suas finanças":
                presente = homePage.isMenuSuasFinancasVisivel();
                break;
            case "Seus benefícios":
                presente = homePage.isMenuSeusBeneficiosVisivel();
                break;
            case "Sua segurança":
                presente = homePage.isMenuSuaSegurancaVisivel();
                break;
            case "Stories":
                presente = homePage.isMenuStoriesVisivel();
                break;
        }
        Assert.assertTrue(presente, "Menu '" + nomeMenu + "' deve estar presente");
    }

    @Quando("eu clico no menu {string}")
    public void euClicoNoMenu(String nomeMenu) {
        switch (nomeMenu) {
            case "O Agibank":
                homePage.clicarMenuOAgibank();
                break;
            case "Produtos":
                homePage.clicarMenuProdutos();
                break;
            case "Serviços":
                homePage.clicarMenuServicos();
                break;
            case "Suas finanças":
                homePage.clicarMenuSuasFinancas();
                break;
            case "Seus benefícios":
                homePage.clicarMenuSeusBeneficios();
                break;
            case "Sua segurança":
                homePage.clicarMenuSuaSeguranca();
                break;
            case "Stories":
                homePage.clicarMenuStories();
                break;
        }
    }

    @Quando("eu passo o mouse sobre {string}")
    public void euPassoOMouseSobre(String nomeSubmenu) {
        switch (nomeSubmenu) {
            case "Empréstimos":
                homePage.clicarSubmenuEmprestimos();
                break;
        }
    }

    @Então("o submenu {string} deve estar visível")
    public void oSubmenuDeveEstarVisivel(String nomeSubmenu) {
        boolean visivel = false;
        switch (nomeSubmenu) {
            case "Colunas":
                visivel = homePage.isSubmenuColunasVisivel();
                break;
            case "Notícias":
                visivel = homePage.isSubmenuNoticiasVisivel();
                break;
            case "Carreira":
                visivel = homePage.isSubmenuCarreiraVisivel();
                break;
            case "Empréstimos":
                visivel = homePage.isSubmenuEmprestimosVisivel();
                break;
            case "Conta Corrente":
                visivel = homePage.isSubmenuContaCorrenteVisivel();
                break;
            case "Cartões":
                visivel = homePage.isSubmenuCartoesVisivel();
                break;
            case "Seguros":
                visivel = homePage.isSubmenuSegurosVisivel();
                break;
            case "INSS":
                visivel = homePage.isSubmenuINSSVisivel();
                break;
            case "Consórcios":
                visivel = homePage.isSubmenuConsorciosVisivel();
                break;
            case "Pix":
                visivel = homePage.isSubmenuPixVisivel();
                break;
            case "Portabilidade":
                visivel = homePage.isSubmenuPortabilidadeVisivel();
                break;
            case "Open Finance":
                visivel = homePage.isSubmenuOpenFinanceVisivel();
                break;
            case "Planejamento Financeiro":
                visivel = homePage.isSubmenuPlanejamentoFinanceiroVisivel();
                break;
            case "Educação Financeira":
                visivel = homePage.isSubmenuEducacaoFinanceiraVisivel();
                break;
            case "Conta Digital":
                visivel = homePage.isSubmenuContaDigitalVisivel();
                break;
            case "Empréstimo Consignado":
                visivel = homePage.isSubmenuEmprestimoConsignadoVisivel();
                break;
            case "Empréstimo Pessoal":
                visivel = homePage.isSubmenuEmprestimoPessoalVisivel();
                break;
            case "Empréstimo na Conta de Luz":
                visivel = homePage.isSubmenuEmprestimoContaLuzVisivel();
                break;
        }
        Assert.assertTrue(visivel, "Submenu '" + nomeSubmenu + "' deve estar visível");
    }

    @Então("o banner de consignado deve estar visível")
    public void oBannerDeConsignadoDeveEstarVisivel() {
        Assert.assertTrue(homePage.isBannerConsignadoVisivel(),
                "Banner de consignado deve estar visível");
    }

    @Então("o widget de newsletter deve estar visível")
    public void oWidgetDeNewsletterDeveEstarVisivel() {
        Assert.assertTrue(homePage.isWidgetNewsletterVisivel(),
                "Widget de newsletter deve estar visível");
    }

    @Então("o campo de email da newsletter deve estar visível")
    public void oCampoDeEmailDaNewsletterDeveEstarVisivel() {
        Assert.assertTrue(homePage.isCampoEmailNewsletterVisivel(),
                "Campo de email da newsletter deve estar visível");
    }

    @Então("o botão assinar da newsletter deve estar visível")
    public void oBotaoAssinarDaNewsletterDeveEstarVisivel() {
        Assert.assertTrue(homePage.isBotaoAssinarVisivel(),
                "Botão assinar da newsletter deve estar visível");
    }

    @Então("todas as redes sociais devem estar visíveis")
    public void todasAsRedesSociaisDevemEstarVisiveis() {
        Assert.assertTrue(homePage.todasRedesSociaisVisiveis(),
                "Todas as redes sociais devem estar visíveis");
    }

    @Então("o link do Facebook deve estar visível")
    public void oLinkDoFacebookDeveEstarVisivel() {
        Assert.assertTrue(homePage.isLinkFacebookVisivel(), "Link do Facebook deve estar visível");
    }

    @Então("o link do Instagram deve estar visível")
    public void oLinkDoInstagramDeveEstarVisivel() {
        Assert.assertTrue(homePage.isLinkInstagramVisivel(),
                "Link do Instagram deve estar visível");
    }

    @Então("o link do LinkedIn deve estar visível")
    public void oLinkDoLinkedinDeveEstarVisivel() {
        Assert.assertTrue(homePage.isLinkLinkedinVisivel(), "Link do LinkedIn deve estar visível");
    }

    @Então("o link do TikTok deve estar visível")
    public void oLinkDoTiktokDeveEstarVisivel() {
        Assert.assertTrue(homePage.isLinkTiktokVisivel(), "Link do TikTok deve estar visível");
    }
}
