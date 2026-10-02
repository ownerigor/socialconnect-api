package br.com.socialconnect.api.produtos.controller;

import br.com.socialconnect.api.produtos.dto.ProdutoRequestDTO;
import br.com.socialconnect.api.produtos.dto.ProdutoResponseDTO;
import br.com.socialconnect.api.produtos.model.CategoriaProduto;
import br.com.socialconnect.api.produtos.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/produtos")
@Tag(name = "Produtos", description = "Cadastro e controle dos produtos do estoque")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista produtos", description = "Permite filtrar por nome parcial e categoria")
    @ApiResponse(responseCode = "200", description = "Pagina de produtos retornada com sucesso")
    public ResponseEntity<Page<ProdutoResponseDTO>> listar(
            @Parameter(description = "Trecho do nome", example = "Arroz")
            @RequestParam(required = false) String nome,
            @Parameter(description = "Categoria", example = "ALIMENTO")
            @RequestParam(required = false) CategoriaProduto categoria,
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        return ResponseEntity.ok(service.listar(nome, categoria, pageable));
    }

    @GetMapping("/{id_produto}")
    @Operation(summary = "Busca um produto pelo identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto nao encontrado", content = @Content)
    })
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @Parameter(description = "Identificador do produto", example = "1")
            @PathVariable("id_produto") Long idProduto
    ) {
        return ResponseEntity.ok(service.buscarPorId(idProduto));
    }

    @PostMapping
    @Operation(summary = "Cadastra um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Nome duplicado", content = @Content),
            @ApiResponse(responseCode = "422", description = "Estoque negativo", content = @Content)
    })
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produto = service.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.idProduto())
                .toUri();
        return ResponseEntity.created(location).body(produto);
    }

    @PutMapping("/{id_produto}")
    @Operation(summary = "Atualiza integralmente um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto nao encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Nome duplicado", content = @Content),
            @ApiResponse(responseCode = "422", description = "Estoque negativo", content = @Content)
    })
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable("id_produto") Long idProduto,
            @Valid @RequestBody ProdutoRequestDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(idProduto, dto));
    }

    @DeleteMapping("/{id_produto}")
    @Operation(summary = "Remove um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido"),
            @ApiResponse(responseCode = "404", description = "Produto nao encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletar(@PathVariable("id_produto") Long idProduto) {
        service.deletar(idProduto);
        return ResponseEntity.noContent().build();
    }
}
