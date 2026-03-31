#language: pt
@busca @regressivo
Funcionalidade: Busca de artigos no Blog do Agi
  Como um usuário do Blog do Agi
  Eu quero poder buscar por artigos
  Para encontrar conteúdo relevante sobre os temas de meu interesse

  Contexto:
    Dado que eu acesso a pagina inicial do Blog do Agi

  @smoke @critico @regressivo
  Cenário: Validar ícone de busca está presente
    Então o ícone de busca deve estar presente

  @smoke @critico @regressivo
  Cenário: Validar funcionalidade do campo de busca
    Quando eu clico na lupa de busca
    Então o campo de busca deve estar visivel
    E o campo de busca deve aceitar entrada de texto
    E deve ser possível submeter a busca

  @critico @regressivo
  Cenário: Busca com termo válido retorna resultados
    Quando eu clico na lupa de busca
    E preencho o campo de busca com "empréstimo"
    E pressiono Enter para buscar
    Então devo ver resultados da busca
    E os resultados devem conter o termo buscado
    E a busca deve ser executada sem erros

  @negativo @regressivo
  Cenário: Busca com termo inexistente exibe mensagem adequada
    Quando eu clico na lupa de busca
    E preencho o campo de busca com "xyzabcdefghijklmnop123456"
    E pressiono Enter para buscar
    Então devo ver uma mensagem de "nenhum resultado"
    E a busca deve ser executada sem erros

  @regressivo
  Cenário: Busca com caracteres especiais não gera erro
    Quando eu clico na lupa de busca
    E preencho o campo de busca com "pix & conta"
    E pressiono Enter para buscar
    Então a busca deve ser executada sem erros

  @regressivo
  Cenário: Busca com múltiplas palavras retorna resultados
    Quando eu clico na lupa de busca
    E preencho o campo de busca com "conta digital agibank"
    E pressiono Enter para buscar
    Então a busca deve ser executada sem erros
    E todos os elementos da página de resultados devem estar presentes
