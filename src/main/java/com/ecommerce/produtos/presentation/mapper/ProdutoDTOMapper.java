package com.ecommerce.produtos.presentation.mapper;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.presentation.dto.ProdutoResponseDTO;

/**
 * Mapper para converter Entidade de Domínio em DTOs da API
 */
public class ProdutoDTOMapper {

    public static ProdutoResponseDTO toResponseDTO(Produto produto) {
        if (produto == null) {
            return null;
        }
        
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