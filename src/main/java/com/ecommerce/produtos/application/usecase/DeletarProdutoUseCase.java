package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.exception.ProdutoNotFoundException;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;

public class DeletarProdutoUseCase {
    
    private final ProdutoRepositoryPort repositoryPort;

    public DeletarProdutoUseCase(ProdutoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public void executar(Long id) {
        if (!repositoryPort.existe(id)) {
            throw new ProdutoNotFoundException(id);
        }
        repositoryPort.deletar(id);
    }
}