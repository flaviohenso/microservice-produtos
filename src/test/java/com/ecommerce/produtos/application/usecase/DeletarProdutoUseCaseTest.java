package com.ecommerce.produtos.application.usecase;

import com.ecommerce.produtos.domain.exception.ProdutoNotFoundException;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Use Case: Deletar Produto")
class DeletarProdutoUseCaseTest {

    @Mock
    private ProdutoRepositoryPort repositoryPort;

    private DeletarProdutoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletarProdutoUseCase(repositoryPort);
    }

    @Test
    @DisplayName("Deve deletar produto existente")
    void deveDeletarProdutoExistente() {
        // Arrange
        Long id = 1L;
        when(repositoryPort.existe(id)).thenReturn(true);

        // Act
        useCase.executar(id);

        // Assert
        verify(repositoryPort, times(1)).existe(id);
        verify(repositoryPort, times(1)).deletar(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar produto inexistente")
    void deveLancarExcecaoAoDeletarProdutoInexistente() {
        // Arrange
        Long idInexistente = 999L;
        when(repositoryPort.existe(idInexistente)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(idInexistente))
                .isInstanceOf(ProdutoNotFoundException.class);

        verify(repositoryPort, never()).deletar(any());
    }
}