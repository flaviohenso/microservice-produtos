package com.ecommerce.produtos.dto;

import com.ecommerce.produtos.model.Produto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "DTO de resposta com informações do produto")
public record ProdutoResponseDTO(
        @Schema(description = "ID único do produto", example = "1")
        Long id,

        @Schema(description = "Nome do produto", example = "Notebook")
        String nome,

        @Schema(description = "Descrição do produto", example = "Notebook Dell Inspiron")
        String descricao,

        @Schema(description = "Preço do produto", example = "2999.99")
        BigDecimal preco,

        @Schema(description = "Quantidade em estoque", example = "10")
        Integer estoque,

        @Schema(description = "Categoria do produto", example = "Eletrônicos")
        String categoria,

        @Schema(description = "Data e hora de criação do produto", example = "2025-11-02T05:59:52")
        LocalDateTime dataCriacao
) {
    public static ProdutoResponseDTO fromEntity(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getCategoria(),
                produto.getDataCriacao()
        );
    }
}
