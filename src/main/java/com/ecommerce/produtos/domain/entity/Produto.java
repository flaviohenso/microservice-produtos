package com.ecommerce.produtos.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de domionio - Representa a entidade Produto no sistema
 * Importante: Não tem anotações de Framework (JPA, Hibernate, etc)
 */
public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private String categoria;
    private LocalDateTime dataCriacao;

    // Construtor para criação (sem ID)
    public Produto(String nome, String descricao, BigDecimal preco,
            Integer estoque, String categoria) {
        this.validarCamposObrigatorios(nome, preco, estoque, categoria);
        this.validarRegrasDeNegocio(preco, estoque, nome);

        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
        this.dataCriacao = LocalDateTime.now();
    }

    // Construtor para reconstituição (com ID - vindo do BD)
    public Produto(Long id, String nome, String descricao, BigDecimal preco,
                   Integer estoque, String categoria, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
        this.dataCriacao = dataCriacao;
    }

    // ===== REGRAS DE NEGÓCIO =====
    
    private void validarCamposObrigatorios(String nome, BigDecimal preco, 
                                           Integer estoque, String categoria) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (preco == null) {
            throw new IllegalArgumentException("Preço é obrigatório");
        }
        if (estoque == null) {
            throw new IllegalArgumentException("Estoque é obrigatório");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
    }

    private void validarRegrasDeNegocio(BigDecimal preco, Integer estoque, String nome) {
        if (preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        if (nome.length() < 3 || nome.length() > 100) {
            throw new IllegalArgumentException("Nome deve ter entre 3 e 100 caracteres");
        }
    }

    public void atualizar(String nome, String descricao, BigDecimal preco,
                         Integer estoque, String categoria) {
        this.validarCamposObrigatorios(nome, preco, estoque, categoria);
        this.validarRegrasDeNegocio(preco, estoque, nome);
        
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
    }

    public void reduzirEstoque(Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        if (this.estoque < quantidade) {
            throw new IllegalArgumentException(
                "Estoque insuficiente. Disponível: " + this.estoque);
        }
        this.estoque -= quantidade;
    }

    public void aumentarEstoque(Integer quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        this.estoque += quantidade;
    }

    public boolean temEstoqueDisponivel() {
        return this.estoque > 0;
    }

    // ===== GETTERS (sem setters para imutabilidade) =====
    
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }
    public Integer getEstoque() { return estoque; }
    public String getCategoria() { return categoria; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
