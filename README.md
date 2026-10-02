# SocialConnect API

> API RESTful de gestão para instituições sociais (ONGs, bancos de alimentos,
> CRAS, abrigos). Conecta doadores, voluntários e beneficiários.

**Disciplina:** Tópicos Especiais em Sistemas para Internet III
**Stack:** Java 21 · Spring Boot 4.1.1 · JPA · H2 (dev) · PostgreSQL (prod)

---

## Como Rodar

### Pré-requisitos

- JDK 21 LTS ([Adoptium](https://adoptium.net/))
- Maven 3.9+ (ou use o wrapper: `./mvnw`)
- IDE: IntelliJ IDEA (recomendado) ou VS Code

### Passos

```bash
# 1. Clone o repositório
git clone <url-do-repo>
cd socialconnect-api

# 2. Compile o projeto
mvn clean compile

# 3. Rode a aplicação
mvn spring-boot:run
```

O Swagger UI fica disponivel em `http://localhost:8080/swagger-ui.html`.

## Modulo de Produtos

O modulo permite cadastrar, consultar, atualizar e remover produtos do estoque.

| Metodo | Endpoint | Descricao |
|---|---|---|
| `GET` | `/api/v1/produtos` | Lista paginada, com filtros opcionais `nome` e `categoria` |
| `GET` | `/api/v1/produtos/{id_produto}` | Busca um produto pelo identificador |
| `POST` | `/api/v1/produtos` | Cadastra um produto |
| `PUT` | `/api/v1/produtos/{id_produto}` | Atualiza integralmente um produto |
| `DELETE` | `/api/v1/produtos/{id_produto}` | Remove um produto |

Categorias aceitas: `ALIMENTO`, `ROUPA`, `HIGIENE` e `OUTROS`.

Exemplo de cadastro:

```json
{
  "nome": "Arroz 5kg",
  "categoria": "ALIMENTO",
  "estoqueAtual": 3,
  "estoqueMinimo": 10,
  "unidadeMedida": "unidade"
}
```

Para executar todos os testes:

```bash
mvn clean test
```

Os testes de integracao do modulo de produtos utilizam PostgreSQL 17 via
Testcontainers e, portanto, requerem Docker em execucao.
