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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do Use Case: Criar Produto")
class CriarProdutoUseCaseTest {

    @Mock
    private ProdutoRepositoryPort repositoryPort;

    private CriarProdutoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CriarProdutoUseCase(repositoryPort);
    }

    @Test
    @DisplayName("Deve criar produto com dados válidos")
    void deveCriarProdutoComDadosValidos() {
        // Arrange
        String nome = "Notebook";
        String descricao = "Notebook Dell Inspiron";
        BigDecimal preco = new BigDecimal("2999.99");
        Integer estoque = 10;
        String categoria = "Eletrônicos";

        Produto produtoEsperado = new Produto(nome, descricao, preco, estoque, categoria);
        
        when(repositoryPort.salvar(any(Produto.class))).thenReturn(produtoEsperado);

        // Act
        Produto produtoSalvo = useCase.executar(nome, descricao, preco, estoque, categoria);

        // Assert
        assertThat(produtoSalvo).isNotNull();
        assertThat(produtoSalvo.getNome()).isEqualTo(nome);
        assertThat(produtoSalvo.getPreco()).isEqualTo(preco);
        assertThat(produtoSalvo.getEstoque()).isEqualTo(estoque);
        assertThat(produtoSalvo.getCategoria()).isEqualTo(categoria);
        
        verify(repositoryPort, times(1)).salvar(any(Produto.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for inválido")
    void deveLancarExcecaoQuandoNomeForInvalido() {
        // Arrange
        String nomeInvalido = "AB"; // Menos de 3 caracteres
        BigDecimal preco = new BigDecimal("2999.99");
        Integer estoque = 10;

        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(
                nomeInvalido, "Descrição", preco, estoque, "Eletrônicos"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Nome deve ter entre 3 e 100 caracteres");

        verify(repositoryPort, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando preço for negativo")
    void deveLancarExcecaoQuandoPrecoForNegativo() {
        // Arrange
        BigDecimal precoNegativo = new BigDecimal("-100.00");

        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(
                "Notebook", "Descrição", precoNegativo, 10, "Eletrônicos"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Preço deve ser maior que zero");

        verify(repositoryPort, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque for negativo")
    void deveLancarExcecaoQuandoEstoqueForNegativo() {
        // Arrange
        Integer estoqueNegativo = -5;

        // Act & Assert
        assertThatThrownBy(() -> useCase.executar(
                "Notebook", "Descrição", new BigDecimal("2999.99"), 
                estoqueNegativo, "Eletrônicos"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Estoque não pode ser negativo");

        verify(repositoryPort, never()).salvar(any());
    }
}