package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.exception.ProdutoNotFoundException;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Use Case: Buscar Produto Por ID")
class BuscarProdutoPorIdUseCaseTest {

    @Mock
    private ProdutoRepositoryPort repositoryPort;

    private BuscarProdutoPorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new BuscarProdutoPorIdUseCase(repositoryPort);
    }

    @Test
    @DisplayName("Deve buscar produto existente por ID")
    void deveBuscarProdutoExistentePorId() {
        // Arrange
        Long id = 1L;
        Produto produtoEsperado = new Produto(
                id, "Notebook", "Dell Inspiron", 
                new BigDecimal("2999.99"), 10, "Eletrônicos", 
                LocalDateTime.now()
        );
        
        when(repositoryPort.buscarPorId(id)).thenReturn(Optional.of(produtoEsperado));

        // Act
        Produto produtoEncontrado = useCase.executar(id);

        // Assert
        assertThat(produtoEncontrado).isNotNull();
        assertThat(produtoEncontrado.getId()).isEqualTo(id);
        assertThat(produtoEncontrado.getNome()).isEqualTo("Notebook");
        
        verify(repositoryPort, times(1)).buscarPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando produto não existir")
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        // Arrange
        Long idInexistente = 999L;
        when(repositoryPort.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(idInexistente))
                .isInstanceOf(ProdutoNotFoundException.class)
                .hasMessageContaining("999");

        verify(repositoryPort, times(1)).buscarPorId(idInexistente);
    }
}