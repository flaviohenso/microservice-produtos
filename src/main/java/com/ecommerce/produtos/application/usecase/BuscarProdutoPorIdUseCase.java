package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.exception.ProdutoNotFoundException;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;

public class BuscarProdutoPorIdUseCase {
    
    private final ProdutoRepositoryPort repositoryPort;

    public BuscarProdutoPorIdUseCase(ProdutoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public Produto executar(Long id) {
        return repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }
}