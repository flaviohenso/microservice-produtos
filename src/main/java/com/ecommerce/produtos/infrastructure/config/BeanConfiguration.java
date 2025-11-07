package com.ecommerce.produtos.infrastructure.config;

import com.ecommerce.produtos.application.usecase.*;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração dos Beans - Injeção de Dependências
 * Aqui criamos as instâncias dos Use Cases
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public CriarProdutoUseCase criarProdutoUseCase(ProdutoRepositoryPort repositoryPort) {
        return new CriarProdutoUseCase(repositoryPort);
    }

    @Bean
    public BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase(ProdutoRepositoryPort repositoryPort) {
        return new BuscarProdutoPorIdUseCase(repositoryPort);
    }

    @Bean
    public ListarTodosProdutosUseCase listarTodosProdutosUseCase(ProdutoRepositoryPort repositoryPort) {
        return new ListarTodosProdutosUseCase(repositoryPort);
    }

    @Bean
    public AtualizarProdutoUseCase atualizarProdutoUseCase(ProdutoRepositoryPort repositoryPort) {
        return new AtualizarProdutoUseCase(repositoryPort);
    }

    @Bean
    public DeletarProdutoUseCase deletarProdutoUseCase(ProdutoRepositoryPort repositoryPort) {
        return new DeletarProdutoUseCase(repositoryPort);
    }

    @Bean
    public BuscarPorCategoriaUseCase buscarPorCategoriaUseCase(ProdutoRepositoryPort repositoryPort) {
        return new BuscarPorCategoriaUseCase(repositoryPort);
    }
}