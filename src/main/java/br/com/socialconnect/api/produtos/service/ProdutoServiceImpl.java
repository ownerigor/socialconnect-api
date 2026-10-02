package br.com.socialconnect.api.produtos.service;

import br.com.socialconnect.api.exception.EstoqueNegativoException;
import br.com.socialconnect.api.exception.NomeProdutoDuplicadoException;
import br.com.socialconnect.api.exception.RecursoNaoEncontradoException;
import br.com.socialconnect.api.produtos.dto.ProdutoRequestDTO;
import br.com.socialconnect.api.produtos.dto.ProdutoResponseDTO;
import br.com.socialconnect.api.produtos.model.CategoriaProduto;
import br.com.socialconnect.api.produtos.model.Produto;
import br.com.socialconnect.api.produtos.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoServiceImpl(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> listar(
            String nome,
            CategoriaProduto categoria,
            Pageable pageable
    ) {
        boolean possuiNome = nome != null && !nome.isBlank();

        Page<Produto> produtos;
        if (possuiNome && categoria != null) {
            produtos = repository.findByNomeContainingIgnoreCaseAndCategoria(
                    nome.trim(), categoria, pageable);
        } else if (possuiNome) {
            produtos = repository.findByNomeContainingIgnoreCase(nome.trim(), pageable);
        } else if (categoria != null) {
            produtos = repository.findByCategoria(categoria, pageable);
        } else {
            produtos = repository.findAll(pageable);
        }

        return produtos.map(this::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidade(id));
    }

    @Override
    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        validarEstoque(dto.estoqueAtual());

        String nome = dto.nome().trim();
        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new NomeProdutoDuplicadoException(
                    "Ja existe um produto cadastrado com o nome: " + nome);
        }

        Produto produto = Produto.builder()
                .nome(nome)
                .categoria(dto.categoria())
                .estoqueAtual(dto.estoqueAtual())
                .estoqueMinimo(dto.estoqueMinimo())
                .unidadeMedida(dto.unidadeMedida().trim())
                .dataCadastro(LocalDate.now())
                .build();

        return toResponseDTO(repository.save(produto));
    }

    @Override
    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        validarEstoque(dto.estoqueAtual());

        Produto produto = buscarEntidade(id);
        String nome = dto.nome().trim();
        if (repository.existsByNomeIgnoreCaseAndIdProdutoNot(nome, id)) {
            throw new NomeProdutoDuplicadoException(
                    "Ja existe outro produto cadastrado com o nome: " + nome);
        }

        produto.setNome(nome);
        produto.setCategoria(dto.categoria());
        produto.setEstoqueAtual(dto.estoqueAtual());
        produto.setEstoqueMinimo(dto.estoqueMinimo());
        produto.setUnidadeMedida(dto.unidadeMedida().trim());

        return toResponseDTO(repository.save(produto));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarEntidade(id);
        repository.delete(produto);
    }

    private Produto buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto nao encontrado: " + id));
    }

    private void validarEstoque(Integer estoqueAtual) {
        if (estoqueAtual != null && estoqueAtual < 0) {
            throw new EstoqueNegativoException("O estoque atual nao pode ser negativo.");
        }
    }

    private ProdutoResponseDTO toResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getEstoqueAtual(),
                produto.getEstoqueMinimo(),
                produto.getUnidadeMedida(),
                produto.getDataCadastro(),
                produto.getEstoqueAtual() < produto.getEstoqueMinimo()
        );
    }
}
