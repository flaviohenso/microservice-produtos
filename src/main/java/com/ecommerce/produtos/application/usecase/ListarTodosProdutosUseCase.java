package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import java.util.List;

public class ListarTodosProdutosUseCase {
    
    private final ProdutoRepositoryPort repositoryPort;

    public ListarTodosProdutosUseCase(ProdutoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<Produto> executar() {
        return repositoryPort.buscarTodos();
    }
}