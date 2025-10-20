package com.southsystem.runners;

import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Runner principal para executar todos os testes com Cucumber + TestNG Executável diretamente pela
 * IDE com um clique
 * 
 * Tags disponíveis: -
 * @regressivo: Todos os testes de regressão -
 * @smoke: Testes essenciais de * fumaça
 * @critico: Testes críticos
 * @blocker: Testes que bloqueiam funcionalidades básicas
 * @busca: Testes específicos de busca - @navegacao: Testes específicos de navegação - @funcional:
 * Testes funcionais
 * @negativo: Testes com cenários negativos
 * @interface: Testes de elementos da  interface
 * @responsividade: Testes de responsividade
 */
@CucumberOptions(features = "src/test/resources/features",
        glue = {"com.southsystem.steps", "com.southsystem.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports/html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "@regressivo", // Por padrão executa todos os testes regressivos
        monochrome = true, publish = false)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Configura execução paralela dos cenários Habilite parallel=true para executar cenários em
     * paralelo e reduzir tempo de execução Certifique-se de que todos os recursos (WebDriver,
     * Pages) usam ThreadLocal
     */
    @Override
    @DataProvider(parallel = false) // Habilitado para execução paralela
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
