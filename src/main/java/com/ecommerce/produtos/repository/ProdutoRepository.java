package com.ecommerce.produtos.repository;

import com.ecommerce.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    Optional<Produto> findByNome(String nome);

    List<Produto> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    List<Produto> findByEstoqueGreaterThan(Integer estoqueMin);
}

