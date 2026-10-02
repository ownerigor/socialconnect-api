package br.com.socialconnect.api.produtos.controller;

import br.com.socialconnect.api.produtos.dto.ProdutoRequestDTO;
import br.com.socialconnect.api.produtos.dto.ProdutoResponseDTO;
import br.com.socialconnect.api.produtos.model.CategoriaProduto;
import br.com.socialconnect.api.produtos.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@Testcontainers(disabledWithoutDocker = true)
class ProdutoControllerIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:17-alpine")
                    .withDatabaseName("socialconnect_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configurarPostgres(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRES::getDriverClassName);
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProdutoRepository repository;

    @BeforeEach
    void limparProdutos() {
        repository.deleteAll();
    }

    @Test
    void deveCriarProdutoQuandoDadosValidos() {
        // Arrange
        ProdutoRequestDTO dto = novoDto("Arroz 5kg", 3);

        // Act
        ResponseEntity<ProdutoResponseDTO> resposta = restTemplate.postForEntity(
                "/api/v1/produtos", dto, ProdutoResponseDTO.class);

        // Assert
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getHeaders().getLocation()).isNotNull();
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().estoqueBaixo()).isTrue();
    }

    @Test
    void deveRetornar409QuandoNomeDuplicado() {
        // Arrange
        ProdutoRequestDTO dto = novoDto("Arroz 5kg", 3);
        restTemplate.postForEntity("/api/v1/produtos", dto, ProdutoResponseDTO.class);

        // Act
        ResponseEntity<String> resposta = restTemplate.postForEntity(
                "/api/v1/produtos", dto, String.class);

        // Assert
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(resposta.getBody()).contains("produto-duplicado");
    }

    @Test
    void deveRetornar422QuandoEstoqueNegativo() {
        // Arrange
        ProdutoRequestDTO dto = novoDto("Feijao 1kg", -1);

        // Act
        ResponseEntity<String> resposta = restTemplate.postForEntity(
                "/api/v1/produtos", dto, String.class);

        // Assert
        assertThat(resposta.getStatusCode().value()).isEqualTo(422);
        assertThat(resposta.getBody()).contains("estoque-negativo");
    }

    private ProdutoRequestDTO novoDto(String nome, int estoqueAtual) {
        return new ProdutoRequestDTO(
                nome,
                CategoriaProduto.ALIMENTO,
                estoqueAtual,
                10,
                "unidade"
        );
    }
}
