package com.ecommerce.produtos.infrastructure.persistence.mapper;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.infrastructure.persistence.entity.ProdutoJpaEntity;

/**
 * Mapper para converter entre Entidade de Domínio e Entidade JPA
 */
public class ProdutoMapper {

    public static Produto toDomain(ProdutoJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        return new Produto(
                jpaEntity.getId(),
                jpaEntity.getNome(),
                jpaEntity.getDescricao(),
                jpaEntity.getPreco(),
                jpaEntity.getEstoque(),
                jpaEntity.getCategoria(),
                jpaEntity.getDataCriacao()
        );
    }

    public static ProdutoJpaEntity toJpaEntity(Produto produto) {
        if (produto == null) {
            return null;
        }
        
        return new ProdutoJpaEntity(
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