package com.ecommerce.produtos.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "DTO para criação e atualização de produtos")
public record ProdutoRequestDTO(
        @Schema(description = "Nome do produto", example = "Notebook", required = true, minLength = 3, maxLength = 100)
        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @Schema(description = "Descrição detalhada do produto", example = "Notebook Dell Inspiron", maxLength = 500)
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String descricao,

        @Schema(description = "Preço do produto", example = "2999.99", required = true, minimum = "0.01")
        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @Schema(description = "Quantidade disponível em estoque", example = "10", required = true, minimum = "0")
        @NotNull(message = "A quantidade em estoque é obrigatória")
        @Min(value = 0, message = "O estoque não pode ser negativo")
        Integer estoque,

        @Schema(description = "Categoria do produto", example = "Eletrônicos", required = true, maxLength = 50)
        @NotBlank(message = "A categoria é obrigatória")
        @Size(max = 50, message = "A categoria deve ter no máximo 50 caracteres")
        String categoria
) {
}
