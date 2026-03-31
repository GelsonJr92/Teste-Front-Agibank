package com.agibank.steps;

import com.agibank.context.ScenarioContext;
import io.cucumber.java.pt.Dado;
import org.junit.jupiter.api.Assertions;

/**
 * Steps definitions comuns compartilhados entre diferentes features.
 */
public class CommonSteps {

    private final ScenarioContext context;

    public CommonSteps(ScenarioContext context) {
        this.context = context;
    }

    @Dado("que eu acesso a pagina inicial do Blog do Agi")
    public void queEuAcessoAPaginaInicialDoBlogDoAgi() {
        context.getHomePage().acessarPaginaInicial(context.getBaseUrl());
        Assertions.assertTrue(context.getHomePage().isPaginaInicialCarregada(),
                "Página inicial não foi carregada corretamente");
    }
}
