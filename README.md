# 🧪 Blog do Agi - Automação de Testes Web

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green.svg)](https://selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-blue.svg)](https://testng.org/doc/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.20.1-brightgreen.svg)](https://cucumber.io/)
[![Allure](https://img.shields.io/badge/Allure-2.29.0-yellow.svg)](https://docs.qameta.io/allure/)
[![SLF4J](https://img.shields.io/badge/SLF4J-2.0.16-blue.svg)](http://www.slf4j.org/)

## 📋 Descrição do Projeto

Este projeto contém a automação de testes Web para o **Blog do Agi** (https://blog.agibank.com.br/), focando especialmente na funcionalidade de busca de artigos. Foi desenvolvido como parte de um teste técnico de QA, implementando os cenários mais relevantes para automação da pesquisa de artigos utilizando a lupa no canto superior direito do site.

## 🎯 Cenários de Teste Implementados

### 🔍 **Funcionalidade de Busca (Cenários Principais)**

1. **Busca com termo válido** - Verifica se a busca retorna resultados para termos populares
2. **Busca com termo inexistente** - Valida o comportamento com termos que não existem
3. **Busca com caracteres especiais** - Testa acentos e caracteres da língua portuguesa
4. **Busca com múltiplas palavras** - Verifica busca com espaços e múltiplos termos
5. **Interface de busca** - Valida se os elementos da interface estão funcionando corretamente

### 🧭 **Funcionalidade de Navegação (Cenários Complementares)**

1. **Acesso à página inicial** - Verifica se a página carrega corretamente
2. **Presença do ícone de busca** - Valida se a lupa está presente e clicável
3. **Responsividade** - Testa o comportamento em diferentes resoluções

## 🏗️ Arquitetura do Projeto

```
src/
├── main/java/com/southsystem/
│   ├── config/
│   │   ├── ConfigurationManager.java    # Gerenciamento de configurações
│   │   └── DriverFactory.java          # Factory para WebDriver
│   └── pages/
│       ├── BasePage.java               # Classe base para Page Objects
│       ├── HomePage.java               # Page Object da página inicial
│       └── SearchPage.java             # Page Object da página de busca
│
└── test/
    ├── java/com/southsystem/
    │   ├── tests/
    │   │   ├── BaseTest.java           # Classe base para testes
    │   │   ├── SearchTest.java         # Testes da funcionalidade de busca
    │   │   └── NavigationTest.java     # Testes de navegação
    │   ├── steps/
    │   │   ├── BuscaSteps.java         # Steps para Cucumber (Busca)
    │   │   └── NavegacaoSteps.java     # Steps para Cucumber (Navegação)
    │   └── runners/
    │       ├── BuscaTestRunner.java    # Runner específico para busca
    │       └── AllTestsRunner.java     # Runner para todos os testes
    │
    └── resources/
        ├── features/
        │   ├── busca.feature           # Cenários BDD para busca
        │   └── navegacao.feature       # Cenários BDD para navegação
        ├── config.properties           # Configurações do projeto
        └── testng.xml                  # Configuração do TestNG
```

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Selenium WebDriver 4.27.0** - Automação web
- **TestNG 7.10.2** - Framework de testes
- **Cucumber 7.20.1** - BDD (Behavior Driven Development)
- **WebDriverManager 5.9.2** - Gerenciamento automático de drivers
- **Allure 2.29.0** - Relatórios de teste
- **SLF4J 2.0.16** - Logging profissional
- **Maven 3.9+** - Gerenciamento de dependências
- **Page Object Pattern** - Padrão de design para organização do código
- **Selenium Grid 4.27.0** - Distribuição de testes em múltiplas máquinas
- **GitHub Actions** - CI/CD para execução automática
- **Docker Compose** - Orquestração do Selenium Grid

## 🚀 Pré-requisitos

### Obrigatórios
- **Java 17** ou superior ([Download](https://openjdk.java.net/projects/jdk/17/))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))
- **Git** ([Download](https://git-scm.com/downloads))

### Navegadores Suportados
- **Google Chrome** (recomendado)
- **Mozilla Firefox**
- **Microsoft Edge**

> ⚠️ **Nota:** Os drivers dos navegadores são gerenciados automaticamente pelo WebDriverManager, não sendo necessário baixá-los manualmente.

## 📦 Instalação e Configuração

### 1. Clone o repositório
```bash
git clone <URL_DO_REPOSITORIO>
cd blog-agi-automation-tests
```

### 2. Verifique as versões instaladas
```bash
java -version
mvn -version
```

### 3. Instale as dependências
```bash
mvn clean install
```

### 4. Compile o projeto
```bash
mvn compile test-compile
```

## ▶️ Executando os Testes

### 🎯 Execução Completa (Todos os Testes)
```bash
mvn clean test
```

### 🔍 Executar apenas os testes de Busca
```bash
mvn clean test -Dtest=SearchTest
```

### 🧭 Executar apenas os testes de Navegação
```bash
mvn clean test -Dtest=NavigationTest
```

### 🥒 Executar com Cucumber (BDD)
```bash
# Todos os testes BDD
mvn clean test -Dtest=AllTestsRunner

# Apenas testes de busca BDD
mvn clean test -Dtest=BuscaTestRunner
```

### ⚙️ Execução com Parâmetros Personalizados

#### Escolher navegador
```bash
mvn clean test -Dbrowser=chrome    # Chrome (padrão)
mvn clean test -Dbrowser=firefox   # Firefox
mvn clean test -Dbrowser=edge      # Edge
```

#### Executar em modo headless
```bash
mvn clean test -Dheadless=true
```

#### Executar testes específicos por tags (Cucumber)
```bash
# Apenas testes críticos
mvn clean test -Dtest=AllTestsRunner -Dcucumber.filter.tags="@critico"

# Apenas testes de smoke
mvn clean test -Dtest=AllTestsRunner -Dcucumber.filter.tags="@smoke"

# Excluir testes negativos
mvn clean test -Dtest=AllTestsRunner -Dcucumber.filter.tags="not @negativo"
```

## 📊 Relatórios

### 📋 Relatório TestNG
Após a execução, o relatório HTML estará disponível em:
```
target/surefire-reports/index.html
```

### 🎨 Relatório Allure
```bash
# Gerar relatório Allure
mvn allure:report

# Abrir relatório no navegador
mvn allure:serve
```
O relatório ficará disponível em: `target/site/allure-maven-plugin/index.html`

### 🥒 Relatório Cucumber
Relatórios HTML do Cucumber estarão em:
```
target/cucumber-reports/all/index.html     # Todos os testes
target/cucumber-reports/busca/index.html   # Apenas testes de busca
```

## ⚙️ Configurações

### 📝 Arquivo config.properties
Localize e edite: `src/test/resources/config.properties`

```properties
# URL da aplicação
base.url=https://blog.agibank.com.br/

# Configurações do navegador
browser=chrome
headless=false

# Timeouts (em segundos)
implicit.wait=10
explicit.wait=15
page.load.timeout=30
```

### 🎛️ Configurações via Linha de Comando
Todas as propriedades podem ser sobrescritas via parâmetros:
```bash
mvn clean test -Dbase.url=https://blogdoagi.com.br/ -Dbrowser=firefox -Dheadless=true
```

## 🔧 Troubleshooting

### ❌ Problema: Erro de driver do navegador
**Solução:** O WebDriverManager gerencia automaticamente. Verifique se o navegador está instalado.

### ❌ Problema: Testes falhando por timeout
**Solução:** Ajuste os timeouts no `config.properties`:
```properties
implicit.wait=15
explicit.wait=20
page.load.timeout=45
```

### ❌ Problema: Site fora do ar ou alterações na interface
**Solução:** Verifique se https://blog.agibank.com.br/ está acessível. Os locators podem precisar ser atualizados se houve mudanças na interface.

### ❌ Problema: Falha na execução em modo headless
**Solução:** Execute primeiro sem headless para depuração:
```bash
mvn clean test -Dheadless=false
```

---

## 🌐 Execução Avançada

### 🐳 Selenium Grid com Docker

Execute testes distribuídos em múltiplas máquinas usando Grid (2 Chrome + 2 Firefox + 1 Edge = 23 sessões paralelas).

```bash
# Iniciar Grid
docker-compose -f docker-compose-grid.yml up -d

# Executar testes no Grid
mvn clean test -Dgrid.url=http://localhost:4444

# Executar com tags específicas
mvn clean test -Dgrid.url=http://localhost:4444 -Dcucumber.filter.tags="@smoke"

# Monitorar execução ao vivo (VNC - senha: secret)
# Chrome Node 1: http://localhost:7901
# Chrome Node 2: http://localhost:7902
# Firefox Node 1: http://localhost:7903
# Firefox Node 2: http://localhost:7904
# Edge Node: http://localhost:7905

# Dashboard do Grid
http://localhost:4444

# Parar Grid
docker-compose -f docker-compose-grid.yml down
```

### 🚀 CI/CD com GitHub Actions

Workflow automatizado que executa em push, PR, agendamento e manual.

**Triggers:**
- Push/PR em `main` e `develop`
- Agendamento: Diário 6h UTC, Semanal segunda 9h UTC
- Manual: `gh workflow run "CI/CD - Testes Automatizados"`

**Jobs:**
- Matrix: Ubuntu/Windows/macOS × Chrome/Firefox/Edge (7 combinações)
- Smoke tests (@smoke)
- Critical tests (@critico)
- Consolidação de relatórios Allure + GitHub Pages
- Notificações Slack/Email

**Configurar secrets (opcional):**
```
SLACK_WEBHOOK     - Notificações Slack
EMAIL_USERNAME    - Gmail para alertas
EMAIL_PASSWORD    - App password do Gmail
EMAIL_TO          - Destinatário dos alertas
```

**GitHub Pages:**
Settings → Pages → Branch: `gh-pages`
URL: `https://{usuario}.github.io/{repo}/reports/{run-number}/`

## 📝 Estrutura dos Testes

### 🧪 Tipos de Teste Implementados

1. **Testes Funcionais** - Validam a funcionalidade de busca
2. **Testes de Interface** - Verificam elementos da UI
3. **Testes Negativos** - Validam comportamentos com dados inválidos
4. **Testes de Responsividade** - Verificam diferentes resoluções

### 🏷️ Tags do Cucumber

- `@smoke` - Testes essenciais de fumaça
- `@critico` - Testes críticos para funcionamento
- `@blocker` - Testes que bloqueiam funcionalidades básicas
- `@funcional` - Testes de funcionalidades específicas
- `@negativo` - Testes com cenários negativos
- `@interface` - Testes de elementos da interface
- `@responsividade` - Testes de responsividade

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto é parte de um teste técnico e está disponível para fins educacionais e de avaliação.

## 📞 Suporte

Em caso de dúvidas ou problemas:
1. Verifique a seção de Troubleshooting
2. Consulte os logs dos testes em `target/surefire-reports/`
3. Execute os testes em modo debug com `-X` para logs detalhados:
   ```bash
   mvn clean test -X
   ```

---

**Desenvolvido com ❤️ para o teste técnico QA - South System**