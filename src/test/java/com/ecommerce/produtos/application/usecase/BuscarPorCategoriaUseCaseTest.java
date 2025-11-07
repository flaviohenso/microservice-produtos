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
@DisplayName("Testes do Use Case: Buscar Por Categoria")
class BuscarPorCategoriaUseCaseTest {

    @Mock
    private ProdutoRepositoryPort repositoryPort;

    private BuscarPorCategoriaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new BuscarPorCategoriaUseCase(repositoryPort);
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria")
    void deveBuscarProdutosPorCategoria() {
        // Arrange
        String categoria = "Eletrônicos";
        List<Produto> produtosEsperados = Arrays.asList(
                new Produto(1L, "Notebook", "Dell", new BigDecimal("2999.99"), 
                           10, categoria, LocalDateTime.now()),
                new Produto(2L, "Mouse", "Logitech", new BigDecimal("50.00"), 
                           20, categoria, LocalDateTime.now())
        );

        when(repositoryPort.buscarPorCategoria(categoria)).thenReturn(produtosEsperados);

        // Act
        List<Produto> produtos = useCase.executar(categoria);

        // Assert
        assertThat(produtos).hasSize(2);
        assertThat(produtos).allMatch(p -> p.getCategoria().equals(categoria));

        verify(repositoryPort, times(1)).buscarPorCategoria(categoria);
    }
}