package com.agibank.steps;

import com.agibank.context.ScenarioContext;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

/**
 * Steps definitions para os testes de navegação com Cucumber.
 */
public class NavegacaoSteps {

    private final ScenarioContext context;

    public NavegacaoSteps(ScenarioContext context) {
        this.context = context;
    }

    @Quando("eu acesso a URL do Blog do Agi")
    public void euAcessoAURLDoBlogDoAgi() {
        context.getHomePage().acessarPaginaInicial(context.getBaseUrl());
    }

    @Então("a página inicial deve carregar corretamente")
    public void aPaginaInicialDeveCarregarCorretamente() {
        Assertions.assertTrue(context.getHomePage().isPaginaInicialCarregada(),
                "Página inicial deve carregar corretamente");
    }

    @Então("a página deve carregar corretamente")
    public void aPaginaDeveCarregarCorretamente() {
        Assertions.assertTrue(context.getHomePage().isPaginaInicialCarregada(),
                "Página deve carregar corretamente");
    }

    @Então("a URL deve estar correta")
    public void aURLDeveEstarCorreta() {
        String currentUrl = context.getDriver().getCurrentUrl();
        // blogdoagi.com.br redireciona para blog.agibank.com.br — ambos são válidos
        Assertions.assertTrue(
                currentUrl.contains("blogdoagi.com.br") || currentUrl.contains("agibank.com.br"),
                "URL atual (" + currentUrl + ") não corresponde ao Blog do Agi");
    }

    @Então("o título da página não deve estar vazio")
    public void oTituloDaPaginaNaoDeveEstarVazio() {
        Assertions.assertFalse(context.getDriver().getTitle().isEmpty(),
                "Título da página não deve estar vazio");
    }

    @Então("o ícone de busca deve estar presente")
    public void oIconeDeBuscaDeveEstarPresente() {
        Assertions.assertTrue(context.getHomePage().isSearchIconPresente(),
                "Ícone de busca não está presente na página");
    }

    @Então("o ícone de busca deve ser clicável")
    public void oIconeDeBuscaDeveSerClicavel() {
        try {
            context.getHomePage().clicarLupaBusca();
        } catch (Exception e) {
            Assertions.fail("Ícone de busca não é clicável: " + e.getMessage());
        }
    }

    @Quando("eu acesso a pagina em resolucao {int} e {int}")
    public void euAcessoAPaginaEmResolucaoIntInt(int largura, int altura) {
        context.getResponsividadePage().definirResolucao(largura, altura);
        context.getHomePage().acessarPaginaInicial(context.getBaseUrl());
    }

    @Então("os elementos principais devem estar visíveis")
    public void osElementosPrincipaisDevemEstarVisiveis() {
        Assertions.assertFalse(context.getDriver().getTitle().toLowerCase().contains("error"),
                "Página não deve conter erros");
        Assertions.assertTrue(context.getHomePage().isPaginaInicialCarregada(),
                "Elementos principais devem estar visíveis");
    }

    @Então("o layout deve estar adaptado para {string}")
    public void oLayoutDeveEstarAdaptadoPara(String tipoDispositivo) {
        Assertions.assertTrue(context.getResponsividadePage().isLayoutAdaptado(tipoDispositivo),
                "O layout não está adaptado para " + tipoDispositivo);
    }

    // ========== STEPS DE VALIDAÇÃO DE MENUS ==========

    @Então("todos os menus principais devem estar visíveis")
    public void todosOsMenusPrincipaisDevemEstarVisiveis() {
        Assertions.assertTrue(context.getHomePage().todosMenusPrincipaisVisiveis(),
                "Todos os menus principais devem estar visíveis");
    }

    @Então("o menu {string} deve estar presente")
    public void oMenuDeveEstarPresente(String nomeMenu) {
        boolean presente = switch (nomeMenu) {
            case "O Agibank" -> context.getHomePage().isMenuOAgibankVisivel();
            case "Produtos" -> context.getHomePage().isMenuProdutosVisivel();
            case "Serviços" -> context.getHomePage().isMenuServicosVisivel();
            case "Suas finanças" -> context.getHomePage().isMenuSuasFinancasVisivel();
            case "Seus benefícios" -> context.getHomePage().isMenuSeusBeneficiosVisivel();
            case "Sua segurança" -> context.getHomePage().isMenuSuaSegurancaVisivel();
            case "Stories" -> context.getHomePage().isMenuStoriesVisivel();
            default -> false;
        };
        Assertions.assertTrue(presente, "Menu '" + nomeMenu + "' deve estar presente");
    }

    @Quando("eu clico no menu {string}")
    public void euClicoNoMenu(String nomeMenu) {
        switch (nomeMenu) {
            case "O Agibank" -> context.getHomePage().clicarMenuOAgibank();
            case "Produtos" -> context.getHomePage().clicarMenuProdutos();
            case "Serviços" -> context.getHomePage().clicarMenuServicos();
            case "Suas finanças" -> context.getHomePage().clicarMenuSuasFinancas();
            case "Seus benefícios" -> context.getHomePage().clicarMenuSeusBeneficios();
            case "Sua segurança" -> context.getHomePage().clicarMenuSuaSeguranca();
            case "Stories" -> context.getHomePage().clicarMenuStories();
        }
    }

    @Quando("eu passo o mouse sobre {string}")
    public void euPassoOMouseSobre(String nomeSubmenu) {
        if ("Empréstimos".equals(nomeSubmenu)) {
            context.getHomePage().clicarSubmenuEmprestimos();
        }
    }

    @Então("o submenu {string} deve estar visível")
    public void oSubmenuDeveEstarVisivel(String nomeSubmenu) {
        boolean visivel = switch (nomeSubmenu) {
            case "Colunas" -> context.getHomePage().isSubmenuColunasVisivel();
            case "Notícias" -> context.getHomePage().isSubmenuNoticiasVisivel();
            case "Carreira" -> context.getHomePage().isSubmenuCarreiraVisivel();
            case "Empréstimos" -> context.getHomePage().isSubmenuEmprestimosVisivel();
            case "Conta Corrente" -> context.getHomePage().isSubmenuContaCorrenteVisivel();
            case "Cartões" -> context.getHomePage().isSubmenuCartoesVisivel();
            case "Seguros" -> context.getHomePage().isSubmenuSegurosVisivel();
            case "INSS" -> context.getHomePage().isSubmenuINSSVisivel();
            case "Consórcios" -> context.getHomePage().isSubmenuConsorciosVisivel();
            case "Pix" -> context.getHomePage().isSubmenuPixVisivel();
            case "Portabilidade" -> context.getHomePage().isSubmenuPortabilidadeVisivel();
            case "Open Finance" -> context.getHomePage().isSubmenuOpenFinanceVisivel();
            case "Planejamento Financeiro" -> context.getHomePage().isSubmenuPlanejamentoFinanceiroVisivel();
            case "Educação Financeira" -> context.getHomePage().isSubmenuEducacaoFinanceiraVisivel();
            case "Conta Digital" -> context.getHomePage().isSubmenuContaDigitalVisivel();
            case "Empréstimo Consignado" -> context.getHomePage().isSubmenuEmprestimoConsignadoVisivel();
            case "Empréstimo Pessoal" -> context.getHomePage().isSubmenuEmprestimoPessoalVisivel();
            case "Empréstimo na Conta de Luz" -> context.getHomePage().isSubmenuEmprestimoContaLuzVisivel();
            default -> false;
        };
        Assertions.assertTrue(visivel, "Submenu '" + nomeSubmenu + "' deve estar visível");
    }

    @Então("todas as redes sociais devem estar visíveis")
    public void todasAsRedesSociaisDevemEstarVisiveis() {
        Assertions.assertTrue(context.getHomePage().todasRedesSociaisVisiveis(),
                "Todas as redes sociais devem estar visíveis");
    }

}
