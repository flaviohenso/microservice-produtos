package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.exception.ProdutoNotFoundException;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import java.math.BigDecimal;

public class AtualizarProdutoUseCase {
    
    private final ProdutoRepositoryPort repositoryPort;

    public AtualizarProdutoUseCase(ProdutoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Produto executar(Long id, String nome, String descricao,
                           BigDecimal preco, Integer estoque, String categoria) {
        Produto produto = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        
        produto.atualizar(nome, descricao, preco, estoque, categoria);
        
        return repositoryPort.salvar(produto);
    }
}