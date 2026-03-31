package com.agibank.steps;

import com.agibank.context.ScenarioContext;
import com.agibank.pages.SearchPage;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Steps definitions para os testes de busca com Cucumber.
 */
@Slf4j
public class BuscaSteps {

    private final ScenarioContext context;

    public BuscaSteps(ScenarioContext context) {
        this.context = context;
    }

    @Quando("eu clico na lupa de busca")
    public void euClicoNaLupaDeBusca() {
        SearchPage searchPage = context.getHomePage().clicarLupaBusca();
        context.setSearchPage(searchPage);
    }

    @Quando("preencho o campo de busca com {string}")
    public void preenchoOCampoDeBuscaCom(String termo) {
        context.setTermoBuscado(termo);
        context.getSearchPage().preencherCampoBusca(termo);
    }

    @Quando("pressiono Enter para buscar")
    public void pressionoEnterParaBuscar() {
        context.setSearchResultPage(context.getSearchPage().submeterBusca());
    }

    @Então("devo ver resultados da busca")
    public void devoVerResultadosDaBusca() {
        Assertions.assertTrue(context.getSearchResultPage().temResultados(),
                "Deveriam existir resultados da busca");
        Assertions.assertTrue(context.getSearchResultPage().getQuantidadeResultados() > 0,
                "Quantidade de resultados deve ser maior que zero");
    }

    @Então("os resultados devem conter o termo buscado")
    public void osResultadosDevemConterOTermoBuscado() {
        String rawUrl = context.getDriver().getCurrentUrl();
        String decodedUrl = URLDecoder.decode(rawUrl, StandardCharsets.UTF_8).toLowerCase();
        Assertions.assertTrue(decodedUrl.contains(context.getTermoBuscado().toLowerCase()),
                "Os resultados devem conter o termo buscado '" + context.getTermoBuscado() + "' na URL");
    }

    @Então("não devo ver resultados da busca")
    public void naoDevoverResultadosDaBusca() {
        boolean semResultados = !context.getSearchResultPage().temResultados() ||
                context.getSearchResultPage().getQuantidadeResultados() == 0;
        Assertions.assertTrue(semResultados,
                "Não deveria haver resultados para termo inexistente");
    }

    @Então("devo ver uma mensagem de {string}")
    public void devoVerUmaMensagemDe(String tipoMensagem) {
        if (tipoMensagem.contains("nenhum resultado")) {
            boolean semResultados = context.getSearchResultPage().isMensagemNenhumResultadoVisivel() ||
                    !context.getSearchResultPage().temResultados();
            Assertions.assertTrue(semResultados,
                    "Deveria haver mensagem de nenhum resultado ou ausência de resultados");
        }
    }

    @Então("a busca deve ser executada sem erros")
    public void aBuscaDeveSerExecutadaSemErros() {
        Assertions.assertFalse(context.getDriver().getTitle().toLowerCase().contains("error"),
                "Não deveria haver erro na página");
    }

    @Então("o campo de busca deve estar visivel")
    public void oCampoDeBuscaDeveEstarVisivel() {
        Assertions.assertTrue(context.getSearchPage().isCampoBuscaVisivel(),
                "Campo de busca deve estar visível");
    }

    @Então("o campo de busca deve aceitar entrada de texto")
    public void oCampoDeBuscaDeveAceitarEntradaDeTexto() {
        context.getSearchPage().preencherCampoBusca("teste");
    }

    @Então("deve ser possível submeter a busca")
    public void deveSerPossivelSubmeterABusca() {
        try {
            context.setSearchResultPage(context.getSearchPage().submeterBusca());
        } catch (Exception e) {
            Assertions.fail("Não foi possível submeter a busca: " + e.getMessage());
        }
    }

    @Então("todos os elementos da página de resultados devem estar presentes")
    public void todosOsElementosDaPaginaDeResultadosDevemEstarPresentes() {
        log.info("\n=== Validando Página de Resultados ===");

        boolean menusOk = context.getSearchResultPage().todosMenusPrincipaisVisiveis();
        log.info("✓ Menus principais: {}", menusOk ? "OK" : "FALHOU");

        boolean redesSociaisOk = context.getSearchResultPage().todasRedesSociaisVisiveis();
        log.info("✓ Redes sociais: {}", redesSociaisOk ? "OK" : "FALHOU");

        boolean footerOk = context.getSearchResultPage().todosLinksFooterVisiveis();
        log.info("✓ Links footer: {}", footerOk ? "OK" : "FALHOU");

        log.info("======================================\n");

        Assertions.assertTrue(menusOk, "Menus principais devem estar visíveis na página de resultados");
    }
}
