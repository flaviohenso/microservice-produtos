package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;

/**
 * Caso de Uso: Criar um novo produto
 * Contém a lógica de aplicação (orquestração)
 */
public class CriarProdutoUseCase {
    
    private final ProdutoRepositoryPort repositoryPort;

    public CriarProdutoUseCase(ProdutoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Produto executar(String nome, String descricao, 
                           java.math.BigDecimal preco, 
                           Integer estoque, String categoria) {
        // A validação já acontece no construtor da entidade
        Produto produto = new Produto(nome, descricao, preco, estoque, categoria);
        
        return repositoryPort.salvar(produto);
    }
}