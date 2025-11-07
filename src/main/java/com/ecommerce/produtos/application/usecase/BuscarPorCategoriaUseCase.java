package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import java.util.List;

public class BuscarPorCategoriaUseCase {
    
    private final ProdutoRepositoryPort repositoryPort;

    public BuscarPorCategoriaUseCase(ProdutoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<Produto> executar(String categoria) {
        return repositoryPort.buscarPorCategoria(categoria);
    }
}