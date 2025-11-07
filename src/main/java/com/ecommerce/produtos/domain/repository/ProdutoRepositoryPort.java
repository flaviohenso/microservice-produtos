package com.ecommerce.produtos.domain.repository;

import com.ecommerce.produtos.domain.entity.Produto;
import java.util.Optional;
import java.util.List;

/**
 * Port (Interface) do Repositório - Define o contrato
 * A implementação estará na camada de Infrastructure
 */
public interface ProdutoRepositoryPort {
    Produto salvar(Produto produto);
    Optional<Produto> buscarPorId(Long id);
    List<Produto> buscarTodos();
    List<Produto> buscarPorCategoria(String categoria);
    void deletar(Long id);
    boolean existe(Long id);
}
