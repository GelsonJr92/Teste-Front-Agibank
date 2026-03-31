# 🧪 Blog do Agi - Automação de Testes Web

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green.svg)](https://selenium.dev/)
[![JUnit](https://img.shields.io/badge/JUnit-5.11.3-blue.svg)](https://junit.org/junit5/)
[![Cucumber](https://img.shields.io/badge/Cucumber-7.20.1-brightgreen.svg)](https://cucumber.io/)
[![ExtentReports](https://img.shields.io/badge/ExtentReports-5.1.2-orange.svg)](https://www.extentreports.com/)
[![SLF4J](https://img.shields.io/badge/SLF4J-2.0.16-blue.svg)](http://www.slf4j.org/)

## 📋 Descrição do Projeto

Este projeto contém a automação de testes Web para o **Blog do Agi** (https://blogdoagi.com.br/), focando especialmente na funcionalidade de busca de artigos. Foi desenvolvido como parte de um teste técnico de QA, implementando os cenários mais relevantes para automação da pesquisa de artigos utilizando a lupa no canto superior direito do site.

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
├── main/java/com/agibank/
│   ├── config/
│   │   ├── ConfigurationManager.java    # Gerenciamento de configurações
│   │   └── DriverFactory.java          # Factory para WebDriver
│   └── pages/
│       ├── BasePage.java               # Classe base para Page Objects
│       ├── HomePage.java               # Page Object da página inicial
│       ├── SearchPage.java             # Page Object da página de busca
│       ├── SearchResultPage.java       # Page Object da página de resultados
│       └── ResponsividadePage.java     # Page Object de responsividade
│
└── test/
    ├── java/com/agibank/
    │   ├── context/
    │   │   └── ScenarioContext.java    # Contexto compartilhado entre steps
    │   ├── hooks/
    │   │   └── Hooks.java              # Hooks do Cucumber (before/after)
    │   ├── steps/
    │   │   ├── BuscaSteps.java         # Steps para Cucumber (Busca)
    │   │   ├── CommonSteps.java        # Steps comuns compartilhados
    │   │   └── NavegacaoSteps.java     # Steps para Cucumber (Navegação)
    │   └── runners/
    │       └── TestRunner.java         # Runner JUnit 5 para todos os testes
    │
    └── resources/
        ├── features/
        │   ├── busca.feature           # Cenários BDD para busca
        │   └── navegacao.feature       # Cenários BDD para navegação
        ├── config.properties           # Configurações do projeto
        ├── extent.properties           # Configurações do ExtentReports
        └── junit-platform.properties   # Configurações do JUnit Platform
```

## 🛠️ Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Selenium WebDriver 4.27.0** - Automação web
- **JUnit 5.11.3** - Framework de testes
- **Cucumber 7.20.1** - BDD (Behavior Driven Development)
- **WebDriverManager 5.9.2** - Gerenciamento automático de drivers
- **ExtentReports 5.1.2** - Relatórios de teste
- **SLF4J 2.0.16** - Logging profissional
- **Lombok 1.18.36** - Redução de código boilerplate
- **PicoContainer** - Injeção de dependências entre steps
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
mvn clean test -Dcucumber.filter.tags="@critico"
```

### 🦭 Executar apenas os testes de Responsividade
```bash
mvn clean test -Dcucumber.filter.tags="@responsividade"
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
mvn clean test -Dcucumber.filter.tags="@critico"

# Apenas testes de smoke
mvn clean test -Dcucumber.filter.tags="@smoke"

# Excluir testes negativos
mvn clean test -Dcucumber.filter.tags="not @negativo"
```

## 📊 Relatórios

### 🎨 Relatório ExtentReports
Após a execução, o relatório HTML é gerado automaticamente em:
```
target/extent-reports/
```
Abra o arquivo `index.html` em qualquer navegador para visualizar o relatório detalhado dos testes.

### 🥒 Relatório Cucumber
Relatórios HTML do Cucumber estarão em:
```
target/cucumber-reports/
```

## ⚙️ Configurações

### 📝 Arquivo config.properties
Localize e edite: `src/test/resources/config.properties`

```properties
# URL da aplicação
base.url=https://blogdoagi.com.br/

# Configurações do navegador
browser=chrome
headless=false

# Timeouts (em segundos)
implicit.wait=5
explicit.wait=20
page.load.timeout=60
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
**Solução:** Verifique se https://blogdoagi.com.br/ está acessível. Os locators podem precisar ser atualizados se houve mudanças na interface.

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
- Geração e publicação de relatórios ExtentReports
- Notificações Slack/Email

**Configurar secrets (opcional):**
```
SLACK_WEBHOOK     - Notificações Slack
EMAIL_USERNAME    - Gmail para alertas
EMAIL_PASSWORD    - App password do Gmail
EMAIL_TO          - Destinatário dos alertas
```

**Relatórios de Execução:**
Os relatórios ExtentReports são disponibilizados como artefatos do workflow.
Acesse em: Actions → Workflow run → Artifacts → `extent-reports-{os}-{browser}`

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

