package com.ecommerce.produtos.infrastructure.persistence.repository;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.repository.ProdutoRepositoryPort;
import com.ecommerce.produtos.infrastructure.persistence.mapper.ProdutoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementação do Port (Interface) do Repositório
 * Adapter que conecta o domínio com o Spring Data JPA
 */
@Component
public class ProdutoRepositoryImpl implements ProdutoRepositoryPort {

    private final ProdutoJpaRepository jpaRepository;

    public ProdutoRepositoryImpl(ProdutoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Produto salvar(Produto produto) {
        var jpaEntity = ProdutoMapper.toJpaEntity(produto);
        var savedEntity = jpaRepository.save(jpaEntity);
        return ProdutoMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(ProdutoMapper::toDomain);
    }

    @Override
    public List<Produto> buscarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Produto> buscarPorCategoria(String categoria) {
        return jpaRepository.findByCategoria(categoria)
                .stream()
                .map(ProdutoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existe(Long id) {
        return jpaRepository.existsById(id);
    }
}