package com.agibank.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner principal para executar testes com Cucumber + JUnit 5.
 *
 * A tag padrão é @regressivo. Para sobrescrever via linha de comando:
 * mvn test -Dcucumber.filter.tags="@smoke"
 * mvn test -Dcucumber.filter.tags="@busca"
 * mvn test -Dcucumber.filter.tags="@navegacao"
 * mvn test -Dcucumber.filter.tags="@responsividade"
 * mvn test -Dcucumber.filter.tags="@smoke or @critico"
 * mvn test -Dcucumber.filter.tags="@regressivo and not @negativo"
 *
 * Tags disponíveis:
 * 
 * @regressivo — todos os testes de regressão
 * @smoke — testes essenciais de fumaça
 * @critico — testes críticos
 * @blocker — testes que bloqueiam funcionalidades básicas
 * @busca — testes específicos de busca
 * @navegacao — testes específicos de navegação
 * @funcional — testes funcionais
 * @negativo — testes com cenários negativos
 * @interface — testes de elementos da interface
 * @responsividade — testes de responsividade
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.agibank.steps,com.agibank.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,html:target/cucumber-reports/html,json:target/cucumber-reports/cucumber.json,com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
// @IncludeTags("")
public class TestRunner {
}
