package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Use Case: Listar Todos os Produtos")
class ListarTodosProdutosUseCaseTest {

    @Mock
    private ProdutoRepositoryPort repositoryPort;

    private ListarTodosProdutosUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListarTodosProdutosUseCase(repositoryPort);
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosOsProdutos() {
        // Arrange
        List<Produto> produtosEsperados = Arrays.asList(
                new Produto(1L, "Notebook", "Dell", new BigDecimal("2999.99"), 
                           10, "Eletrônicos", LocalDateTime.now()),
                new Produto(2L, "Mouse", "Logitech", new BigDecimal("50.00"), 
                           20, "Eletrônicos", LocalDateTime.now())
        );

        when(repositoryPort.buscarTodos()).thenReturn(produtosEsperados);

        // Act
        List<Produto> produtos = useCase.executar();

        // Assert
        assertThat(produtos).hasSize(2);
        assertThat(produtos.get(0).getNome()).isEqualTo("Notebook");
        assertThat(produtos.get(1).getNome()).isEqualTo("Mouse");

        verify(repositoryPort, times(1)).buscarTodos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver produtos")
    void deveRetornarListaVaziaQuandoNaoHouverProdutos() {
        // Arrange
        when(repositoryPort.buscarTodos()).thenReturn(List.of());

        // Act
        List<Produto> produtos = useCase.executar();

        // Assert
        assertThat(produtos).isEmpty();
        verify(repositoryPort, times(1)).buscarTodos();
    }
}