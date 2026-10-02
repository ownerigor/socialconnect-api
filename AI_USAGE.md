# Registro de Uso de IA Generativa

> **Política da disciplina:** O uso de IA generativa é permitido como
> assistente. O discente é **integralmente responsável** por testar, auditar e
> defender todo o código entregue, independentemente de como foi gerado.

## Instruções

Para cada aula ou entrega, registre abaixo:
- **Data**
- **Ferramenta** (ChatGPT, Copilot, Claude, etc.)
- **Prompt(s) utilizado(s)** (resumo ou cópia)
- **O que foi feito com a saída** (copiado integralmente, adaptado, usado como referência, descartado)

---

## 1. DTOs de entrada e saída

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Em uma API Spring Boot, por que é interessante ter um DTO para receber
> os dados de cadastro e outro DTO para retornar os dados da API? Qual é
> a diferença entre RequestDTO, ResponseDTO e a entidade JPA? Explique
> usando um cadastro de Produto como exemplo.

### Como utilizei

-   Usei IA para entender melhor a separação entre DTOs de entrada, DTOs
    de saída e entidades.
-   Comparei a explicação com a estrutura utilizada nos DTOs do projeto.

### O que eu entendo 100%

-   A diferença entre DTO e entidade.
-   A finalidade de separar os dados recebidos dos dados retornados pela
    API.
-   Por que a entidade JPA não precisa ser exposta diretamente pelo
    Controller.

### O que precisei estudar mais

-   Boas práticas para definir quais campos devem fazer parte de cada
    DTO.

------------------------------------------------------------------------

## 2. Validações personalizadas com `ConstraintValidator`

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Como funciona uma validação personalizada no Jakarta Bean Validation
> usando uma annotation própria e `ConstraintValidator`? Por exemplo, se
> eu tiver uma annotation chamada `@EstoqueNaoNegativo`, como o Spring
> sabe qual classe deve executar essa validação?

### Como utilizei

-   Usei IA para entender a ligação entre uma annotation de validação e
    sua implementação.
-   Comparei a explicação com a validação de estoque existente no
    projeto.

### O que eu entendo 100%

-   A finalidade de uma validação personalizada.
-   O papel do `ConstraintValidator`.
-   Como o método `isValid` determina se um valor é válido.

### O que precisei estudar mais

-   Como o Jakarta Bean Validation descobre e executa automaticamente os
    Validators.

------------------------------------------------------------------------

## 3. Validação de CPF

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Como funciona a validação de um CPF? Explique principalmente o cálculo
> dos dois dígitos verificadores e por que CPFs com determinados padrões
> repetidos também precisam ser considerados inválidos.

### Como utilizei

-   Usei IA para compreender o algoritmo utilizado na validação de CPF.
-   Comparei a explicação com o `CpfValidator` utilizado no projeto.
-   Analisei alguns exemplos de entradas válidas e inválidas.

### O que eu entendo 100%

-   A finalidade dos dígitos verificadores.
-   Por que apenas verificar a quantidade de números não é suficiente.
-   Como uma validação de CPF pode ser utilizada em um DTO.

### O que precisei estudar mais

-   O cálculo matemático completo utilizado para obter os dois dígitos
    verificadores.

------------------------------------------------------------------------

## 4. Tratamento global de exceções

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Qual é a vantagem de usar `@ControllerAdvice` e `@ExceptionHandler` em
> uma API Spring Boot em vez de tratar as exceções separadamente dentro
> de cada Controller? Como isso ajuda a padronizar as respostas de erro?

### Como utilizei

-   Usei IA para compreender melhor o funcionamento do tratamento global
    de exceções.
-   Comparei a explicação com o `GlobalExceptionHandler` do projeto.

### O que eu entendo 100%

-   A função do `@ControllerAdvice`.
-   A finalidade do `@ExceptionHandler`.
-   A vantagem de centralizar o tratamento de erros da API.

### O que precisei estudar mais

-   A escolha dos códigos HTTP mais adequados para diferentes erros de
    negócio.

------------------------------------------------------------------------

## 5. Diferença entre `PUT` e `PATCH`

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Qual é a diferença prática entre PUT e PATCH em uma API REST? Em um
> cadastro de Beneficiário com nome, CPF, telefone e data de nascimento,
> o que deveria acontecer se eu quiser alterar somente o telefone usando
> PATCH?

### Como utilizei

-   Usei IA para entender a diferença entre atualização completa e
    atualização parcial.
-   Comparei a explicação com o `BeneficiarioPatchDTO` e os endpoints do
    projeto.

### O que eu entendo 100%

-   PUT normalmente representa uma atualização completa do recurso.
-   PATCH pode ser utilizado para alterações parciais.
-   Em uma atualização parcial, campos não enviados não devem ser
    alterados.

### O que precisei estudar mais

-   Como APIs diferenciam campos ausentes de campos enviados
    explicitamente com valor `null`.

------------------------------------------------------------------------

## 6. Verificação de dados duplicados

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Em uma aplicação Spring Boot com JPA, qual é a diferença entre
> verificar se um dado já existe através do Repository antes de salvar e
> criar uma restrição UNIQUE diretamente no banco? É recomendado
> utilizar as duas abordagens?

### Como utilizei

-   Usei IA para compreender melhor as formas de evitar dados
    duplicados.
-   Relacionei a explicação com as regras de CPF e nome de produto
    existentes no projeto.

### O que eu entendo 100%

-   Como consultar a existência de um registro através do Repository.
-   Por que dados duplicados podem representar uma violação de regra de
    negócio.
-   A finalidade de uma restrição de unicidade no banco.

### O que precisei estudar mais

-   Situações de concorrência em que duas requisições podem passar pela
    validação da aplicação quase simultaneamente.

------------------------------------------------------------------------

## 7. Testes unitários com Mockito

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [x] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Em um teste unitário de uma classe Service no Spring Boot, por que
> normalmente o Repository é mockado com Mockito? Explique a diferença
> entre `when`, `verify` e um assert, e qual é a responsabilidade de
> cada um no teste.

### Como utilizei

-   Usei IA para entender melhor o papel dos mocks nos testes unitários.
-   Usei o Copilot como auxílio de autocomplete durante a escrita de
    alguns testes.
-   Comparei os conceitos com os testes de Service existentes no
    projeto.

### O que eu entendo 100%

-   A finalidade de isolar o Service do acesso real ao banco.
-   Como `when` define um comportamento esperado para um mock.
-   A diferença entre verificar um resultado e verificar uma interação.

### O que precisei estudar mais

-   Quando uma verificação com `verify` realmente agrega valor ao teste.

------------------------------------------------------------------------

## 8. Testes de integração e Testcontainers

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Qual é a diferença entre um teste unitário com Repository mockado e um
> teste de integração utilizando Testcontainers? Por que utilizar um
> banco real em container pode ser melhor do que utilizar apenas mocks
> para testar Repository e Controller?

### Como utilizei

-   Usei IA para compreender a finalidade dos testes de integração.
-   Analisei a explicação junto da configuração de Testcontainers
    utilizada pelo projeto.

### O que eu entendo 100%

-   Testes unitários procuram isolar pequenas partes da aplicação.
-   Testes de integração verificam a comunicação entre diferentes
    componentes.
-   Testcontainers permite executar testes utilizando um banco isolado.

### O que precisei estudar mais

-   O ciclo de vida dos containers durante a execução da suíte de
    testes.

------------------------------------------------------------------------

## 9. Funcionamento das migrations do Flyway

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Como o Flyway controla quais migrations já foram executadas? O que
> acontece se eu alterar o conteúdo de um arquivo como
> `V2__alguma_alteracao.sql` depois que essa migration já foi aplicada
> no banco?

### Como utilizei

-   Usei IA para entender o versionamento das alterações do banco com
    Flyway.
-   Comparei a explicação com as migrations existentes no projeto.

### O que eu entendo 100%

-   As migrations possuem uma ordem de execução.
-   O Flyway mantém um histórico das migrations executadas.
-   Novas alterações estruturais devem ser versionadas.

### O que precisei estudar mais

-   Como funcionam checksum, `repair` e estratégias para corrigir
    migrations que já foram aplicadas.

------------------------------------------------------------------------

## 10. Responsabilidades de Controller, Service e Repository

### Ferramentas utilizadas

-   [x] ChatGPT / Claude / Gemini
-   [ ] Copilot / Codeium
-   [ ] Nenhuma

### Prompt utilizado

> Em uma API Spring Boot organizada em Controller, Service e Repository,
> onde deveriam ficar as validações de entrada, as regras de negócio e
> as consultas ao banco? Explique usando como exemplo uma operação de
> cadastro de Produto que precisa verificar se o nome já existe.

### Como utilizei

-   Usei IA para entender melhor a responsabilidade de cada camada da
    aplicação.
-   Comparei a explicação com a organização utilizada no projeto.

### O que eu entendo 100%

-   O Controller é responsável pela camada HTTP.
-   O Service concentra as regras de negócio.
-   O Repository realiza o acesso aos dados.

### O que precisei estudar mais

-   Como evitar que regras de negócio fiquem espalhadas entre
    Controller, Service e classes de validação.

_Declaração: Ao submeter este repositório, confirmo que todo o código foi
revisado, testado e compreendido por mim._