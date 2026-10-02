package br.com.socialconnect.api.produtos.dto;

import br.com.socialconnect.api.produtos.model.CategoriaProduto;
import br.com.socialconnect.api.produtos.validation.EstoqueNaoNegativo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro ou atualizacao de um produto")
public record ProdutoRequestDTO(
        @NotBlank(message = "{produto.nome.obrigatorio}")
        @Size(max = 150, message = "{produto.nome.tamanho}")
        @Schema(example = "Arroz 5kg")
        String nome,

        @NotNull(message = "{produto.categoria.obrigatoria}")
        @Schema(example = "ALIMENTO")
        CategoriaProduto categoria,

        @NotNull(message = "{produto.estoque-atual.obrigatorio}")
        @EstoqueNaoNegativo
        @Schema(example = "3", minimum = "0")
        Integer estoqueAtual,

        @NotNull(message = "{produto.estoque-minimo.obrigatorio}")
        @Min(value = 0, message = "{produto.estoque-minimo.nao-negativo}")
        @Schema(example = "10", minimum = "0")
        Integer estoqueMinimo,

        @NotBlank(message = "{produto.unidade-medida.obrigatoria}")
        @Size(max = 20, message = "{produto.unidade-medida.tamanho}")
        @Schema(example = "unidade")
        String unidadeMedida
) {
}
