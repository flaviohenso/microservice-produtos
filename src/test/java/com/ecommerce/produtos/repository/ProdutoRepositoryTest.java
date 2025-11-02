package com.ecommerce.produtos.repository;

import com.ecommerce.produtos.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void testSaveAndFindById() {
        Produto produto = new Produto();
        produto.setNome("Notebook");
        produto.setDescricao("Notebook Dell Inspiron");
        produto.setPreco(new BigDecimal("2999.99"));
        produto.setEstoque(10);
        produto.setCategoria("Eletrônicos");
        produto.setDataCriacao(LocalDateTime.now());

        entityManager.persist(produto);
        entityManager.flush();

        Optional<Produto> produtoEncontrado = produtoRepository.findById(produto.getId());
        assertThat(produtoEncontrado).isPresent();
        assertThat(produtoEncontrado.get().getNome()).isEqualTo("Notebook");
    }

    @Test
    void testFindByCategoria() {
        Produto produto1 = new Produto();
        produto1.setNome("Notebook");
        produto1.setPreco(new BigDecimal("2999.99"));
        produto1.setEstoque(10);
        produto1.setCategoria("Eletrônicos");
        produto1.setDataCriacao(LocalDateTime.now());

        Produto produto2 = new Produto();
        produto2.setNome("Camiseta");
        produto2.setPreco(new BigDecimal("49.99"));
        produto2.setEstoque(20);
        produto2.setCategoria("Roupas");
        produto2.setDataCriacao(LocalDateTime.now());

        entityManager.persist(produto1);
        entityManager.persist(produto2);
        entityManager.flush();

        List<Produto> produtosEletronicos = produtoRepository.findByCategoria("Eletrônicos");
        assertThat(produtosEletronicos).hasSize(1);
        assertThat(produtosEletronicos.get(0).getCategoria()).isEqualTo("Eletrônicos");
    }

    @Test
    void testFindByNomeContainingIgnoreCase() {
        Produto produto = new Produto();
        produto.setNome("Notebook Dell");
        produto.setPreco(new BigDecimal("2999.99"));
        produto.setEstoque(10);
        produto.setCategoria("Eletrônicos");
        produto.setDataCriacao(LocalDateTime.now());

        entityManager.persist(produto);
        entityManager.flush();

        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCase("notebook");
        assertThat(produtos).hasSize(1);
        assertThat(produtos.get(0).getNome()).containsIgnoringCase("notebook");
    }
}


