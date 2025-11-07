package com.ecommerce.produtos.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Testes da Entidade: Produto")
class ProdutoTest {

    @Test
    @DisplayName("Deve criar produto com dados válidos")
    void deveCriarProdutoComDadosValidos() {
        // Act
        Produto produto = new Produto(
                "Notebook", 
                "Dell Inspiron", 
                new BigDecimal("2999.99"), 
                10, 
                "Eletrônicos"
        );

        // Assert
        assertThat(produto).isNotNull();
        assertThat(produto.getNome()).isEqualTo("Notebook");
        assertThat(produto.getPreco()).isEqualByComparingTo(new BigDecimal("2999.99"));
        assertThat(produto.getEstoque()).isEqualTo(10);
        assertThat(produto.getCategoria()).isEqualTo("Eletrônicos");
        assertThat(produto.getDataCriacao()).isNotNull();
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for nulo")
    void deveLancarExcecaoQuandoNomeForNulo() {
        assertThatThrownBy(() -> new Produto(
                null, 
                "Descrição", 
                new BigDecimal("100"), 
                10, 
                "Categoria"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Nome do produto é obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for vazio")
    void deveLancarExcecaoQuandoNomeForVazio() {
        assertThatThrownBy(() -> new Produto(
                "", 
                "Descrição", 
                new BigDecimal("100"), 
                10, 
                "Categoria"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Nome do produto é obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for muito curto")
    void deveLancarExcecaoQuandoNomeForMuitoCurto() {
        assertThatThrownBy(() -> new Produto(
                "AB", 
                "Descrição", 
                new BigDecimal("100"), 
                10, 
                "Categoria"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Nome deve ter entre 3 e 100 caracteres");
    }

    @Test
    @DisplayName("Deve lançar exceção quando preço for zero")
    void deveLancarExcecaoQuandoPrecoForZero() {
        assertThatThrownBy(() -> new Produto(
                "Notebook", 
                "Descrição", 
                BigDecimal.ZERO, 
                10, 
                "Categoria"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Preço deve ser maior que zero");
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque for negativo")
    void deveLancarExcecaoQuandoEstoqueForNegativo() {
        assertThatThrownBy(() -> new Produto(
                "Notebook", 
                "Descrição", 
                new BigDecimal("100"), 
                -1, 
                "Categoria"
        ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Estoque não pode ser negativo");
    }

    @Test
    @DisplayName("Deve atualizar produto corretamente")
    void deveAtualizarProdutoCorretamente() {
        // Arrange
        Produto produto = new Produto(
                "Notebook", 
                "Dell", 
                new BigDecimal("2999.99"), 
                10, 
                "Eletrônicos"
        );

        // Act
        produto.atualizar(
                "Notebook Dell", 
                "Dell Inspiron 15", 
                new BigDecimal("3499.99"), 
                15, 
                "Computadores"
        );

        // Assert
        assertThat(produto.getNome()).isEqualTo("Notebook Dell");
        assertThat(produto.getDescricao()).isEqualTo("Dell Inspiron 15");
        assertThat(produto.getPreco()).isEqualByComparingTo(new BigDecimal("3499.99"));
        assertThat(produto.getEstoque()).isEqualTo(15);
        assertThat(produto.getCategoria()).isEqualTo("Computadores");
    }

    @Test
    @DisplayName("Deve reduzir estoque corretamente")
    void deveReduzirEstoqueCorretamente() {
        // Arrange
        Produto produto = new Produto(
                "Notebook", 
                "Dell", 
                new BigDecimal("2999.99"), 
                10, 
                "Eletrônicos"
        );

        // Act
        produto.reduzirEstoque(3);

        // Assert
        assertThat(produto.getEstoque()).isEqualTo(7);
    }

    @Test
    @DisplayName("Deve lançar exceção ao reduzir estoque além do disponível")
    void deveLancarExcecaoAoReduzirEstoqueAlemDoDisponivel() {
        // Arrange
        Produto produto = new Produto(
                "Notebook", 
                "Dell", 
                new BigDecimal("2999.99"), 
                10, 
                "Eletrônicos"
        );

        // Act & Assert
        assertThatThrownBy(() -> produto.reduzirEstoque(15))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Estoque insuficiente");
    }

    @Test
    @DisplayName("Deve aumentar estoque corretamente")
    void deveAumentarEstoqueCorretamente() {
        // Arrange
        Produto produto = new Produto(
                "Notebook", 
                "Dell", 
                new BigDecimal("2999.99"), 
                10, 
                "Eletrônicos"
        );

        // Act
        produto.aumentarEstoque(5);

        // Assert
        assertThat(produto.getEstoque()).isEqualTo(15);
    }

    @Test
    @DisplayName("Deve verificar se tem estoque disponível")
    void deveVerificarSeTemEstoqueDisponivel() {
        // Arrange
        Produto comEstoque = new Produto(
                "Notebook", "Dell", new BigDecimal("2999.99"), 10, "Eletrônicos"
        );
        Produto semEstoque = new Produto(
                "Mouse", "Logitech", new BigDecimal("50.00"), 0, "Eletrônicos"
        );

        // Act & Assert
        assertThat(comEstoque.temEstoqueDisponivel()).isTrue();
        assertThat(semEstoque.temEstoqueDisponivel()).isFalse();
    }
}