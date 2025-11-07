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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Use Case: Atualizar Produto")
class AtualizarProdutoUseCaseTest {

    @Mock
    private ProdutoRepositoryPort repositoryPort;

    private AtualizarProdutoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AtualizarProdutoUseCase(repositoryPort);
    }

    @Test
    @DisplayName("Deve atualizar produto existente")
    void deveAtualizarProdutoExistente() {
        // Arrange
        Long id = 1L;
        Produto produtoExistente = new Produto(
                id, "Notebook", "Dell", new BigDecimal("2999.99"), 
                10, "Eletrônicos", LocalDateTime.now()
        );

        when(repositoryPort.buscarPorId(id)).thenReturn(Optional.of(produtoExistente));
        when(repositoryPort.salvar(any(Produto.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Produto produtoAtualizado = useCase.executar(
                id, "Notebook Dell", "Dell Inspiron 15", 
                new BigDecimal("3499.99"), 15, "Eletrônicos"
        );

        // Assert
        assertThat(produtoAtualizado.getNome()).isEqualTo("Notebook Dell");
        assertThat(produtoAtualizado.getPreco()).isEqualTo(new BigDecimal("3499.99"));
        assertThat(produtoAtualizado.getEstoque()).isEqualTo(15);

        verify(repositoryPort, times(1)).buscarPorId(id);
        verify(repositoryPort, times(1)).salvar(any(Produto.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        // Arrange
        Long idInexistente = 999L;
        when(repositoryPort.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(
                idInexistente, "Nome", "Desc", 
                new BigDecimal("100.00"), 10, "Categoria"
        ))
        .isInstanceOf(ProdutoNotFoundException.class);

        verify(repositoryPort, never()).salvar(any());
    }
}