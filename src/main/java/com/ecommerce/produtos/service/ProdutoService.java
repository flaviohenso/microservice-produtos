package com.ecommerce.produtos.service;

import com.ecommerce.produtos.dto.ProdutoRequestDTO;
import com.ecommerce.produtos.dto.ProdutoResponseDTO;
import com.ecommerce.produtos.exception.ResourceNotFoundException;
import com.ecommerce.produtos.model.Produto;
import com.ecommerce.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequestDTO) {
        // Validação de regra de negócio: estoque não pode ser negativo
        if (produtoRequestDTO.estoque() < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo");
        }

        Produto produto = new Produto();
        produto.setNome(produtoRequestDTO.nome());
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setPreco(produtoRequestDTO.preco());
        produto.setEstoque(produtoRequestDTO.estoque());
        produto.setCategoria(produtoRequestDTO.categoria());

        Produto produtoSalvo = produtoRepository.save(produto);
        return ProdutoResponseDTO.fromEntity(produtoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodosProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));
        return ProdutoResponseDTO.fromEntity(produto);
    }

    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));

        // Validação de regra de negócio: estoque não pode ser negativo
        if (produtoRequestDTO.estoque() < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo");
        }

        produto.setNome(produtoRequestDTO.nome());
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setPreco(produtoRequestDTO.preco());
        produto.setEstoque(produtoRequestDTO.estoque());
        produto.setCategoria(produtoRequestDTO.categoria());

        Produto produtoAtualizado = produtoRepository.save(produto);
        return ProdutoResponseDTO.fromEntity(produtoAtualizado);
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto", id);
        }
        produtoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> buscarPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);
        return produtos.stream()
                .map(ProdutoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}


