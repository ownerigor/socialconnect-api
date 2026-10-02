package br.com.socialconnect.api.produtos.service;

import br.com.socialconnect.api.exception.EstoqueNegativoException;
import br.com.socialconnect.api.exception.NomeProdutoDuplicadoException;
import br.com.socialconnect.api.produtos.dto.ProdutoRequestDTO;
import br.com.socialconnect.api.produtos.dto.ProdutoResponseDTO;
import br.com.socialconnect.api.produtos.model.CategoriaProduto;
import br.com.socialconnect.api.produtos.model.Produto;
import br.com.socialconnect.api.produtos.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoServiceImpl service;

    @Test
    void deveCriarProdutoQuandoDadosValidos() {
        // Arrange
        ProdutoRequestDTO dto = novoDto(3);
        when(repository.existsByNomeIgnoreCase("Arroz 5kg")).thenReturn(false);
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> {
            Produto produto = invocation.getArgument(0);
            produto.setIdProduto(1L);
            return produto;
        });

        // Act
        ProdutoResponseDTO resposta = service.criar(dto);

        // Assert
        assertThat(resposta.idProduto()).isEqualTo(1L);
        assertThat(resposta.nome()).isEqualTo("Arroz 5kg");
        assertThat(resposta.estoqueBaixo()).isTrue();
        verify(repository).save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueNegativo() {
        // Arrange
        ProdutoRequestDTO dto = novoDto(-1);

        // Act / Assert
        assertThatThrownBy(() -> service.criar(dto))
                .isInstanceOf(EstoqueNegativoException.class)
                .hasMessageContaining("nao pode ser negativo");
        verify(repository, never()).save(any(Produto.class));
    }

    @Test
    void deveLancarExcecaoQuandoNomeDuplicado() {
        // Arrange
        ProdutoRequestDTO dto = novoDto(3);
        when(repository.existsByNomeIgnoreCase("Arroz 5kg")).thenReturn(true);

        // Act / Assert
        assertThatThrownBy(() -> service.criar(dto))
                .isInstanceOf(NomeProdutoDuplicadoException.class)
                .hasMessageContaining("Arroz 5kg");
        verify(repository, never()).save(any(Produto.class));
    }

    private ProdutoRequestDTO novoDto(int estoqueAtual) {
        return new ProdutoRequestDTO(
                "Arroz 5kg",
                CategoriaProduto.ALIMENTO,
                estoqueAtual,
                10,
                "unidade"
        );
    }
}
