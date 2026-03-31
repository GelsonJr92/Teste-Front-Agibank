# language: pt
@navegacao @regressivo
Funcionalidade: Navegação no Blog do Agi
  Como um usuário do Blog do Agi
  Eu quero poder navegar pela aplicação
  Para acessar o conteúdo disponível

  Contexto:
    Dado que eu acesso a pagina inicial do Blog do Agi

  @smoke @blocker @regressivo
  Cenário: Acesso à página inicial
    Quando eu acesso a URL do Blog do Agi
    Então a página inicial deve carregar corretamente
    E a URL deve estar correta
    E o título da página não deve estar vazio

  @interface @critico @regressivo
  Cenário: Presença do ícone de busca
    Então o ícone de busca deve estar presente
    E o ícone de busca deve ser clicável

  @menus @critico @regressivo
  Cenário: Validar presença de todos os menus principais
    Então todos os menus principais devem estar visíveis
    E o menu "O Agibank" deve estar presente
    E o menu "Produtos" deve estar presente
    E o menu "Serviços" deve estar presente
    E o menu "Suas finanças" deve estar presente
    E o menu "Seus benefícios" deve estar presente
    E o menu "Sua segurança" deve estar presente
    E o menu "Stories" deve estar presente

  @submenus @critico @regressivo
  Cenário: Validar submenus do menu "O Agibank"
    Quando eu clico no menu "O Agibank"
    Então o submenu "Colunas" deve estar visível
    E o submenu "Notícias" deve estar visível
    E o submenu "Carreira" deve estar visível

  @submenus @critico @regressivo
  Cenário: Validar submenus do menu "Produtos"
    Quando eu clico no menu "Produtos"
    Então o submenu "Empréstimos" deve estar visível
    E o submenu "Conta Corrente" deve estar visível
    E o submenu "Cartões" deve estar visível
    E o submenu "Seguros" deve estar visível
    E o submenu "INSS" deve estar visível
    E o submenu "Consórcios" deve estar visível
    E o submenu "Pix" deve estar visível

  @submenus @critico @regressivo
  Cenário: Validar submenus de segundo nível em "Empréstimos"
    Quando eu clico no menu "Produtos"
    E eu passo o mouse sobre "Empréstimos"
    Então o submenu "Empréstimo Consignado" deve estar visível
    E o submenu "Empréstimo Pessoal" deve estar visível

  @rodape @funcional @regressivo
  Cenário: Validar redes sociais no rodapé
    Então todas as redes sociais devem estar visíveis

  @responsividade @regressivo
  Esquema do Cenário: Verificar responsividade em diferentes resoluções
    Quando eu acesso a pagina em resolucao <largura> e <altura>
    Então a página deve carregar corretamente
    E os elementos principais devem estar visíveis
    E o layout deve estar adaptado para "<tipo_dispositivo>"

    Exemplos:
      | largura | altura | tipo_dispositivo |
      | 1200    | 800    | desktop          |
      | 768     | 1024   | tablet           |
      | 375     | 667    | mobile           |