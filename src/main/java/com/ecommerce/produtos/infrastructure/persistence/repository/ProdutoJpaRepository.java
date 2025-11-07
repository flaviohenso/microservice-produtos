package com.ecommerce.produtos.infrastructure.persistence.repository;

import com.ecommerce.produtos.infrastructure.persistence.entity.ProdutoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interface do Spring Data JPA
 * Responsável pela comunicação com o banco de dados
 */
public interface ProdutoJpaRepository extends JpaRepository<ProdutoJpaEntity, Long> {
    List<ProdutoJpaEntity> findByCategoria(String categoria);
}