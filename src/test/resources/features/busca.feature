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