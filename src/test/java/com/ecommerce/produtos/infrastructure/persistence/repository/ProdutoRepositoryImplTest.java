package com.ecommerce.produtos.infrastructure.persistence.repository;

import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.infrastructure.persistence.entity.ProdutoJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(ProdutoRepositoryImpl.class)
@DisplayName("Testes de Integração: Repositório de Produto")
class ProdutoRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProdutoRepositoryImpl produtoRepository;

    @Test
    @DisplayName("Deve salvar e buscar produto por ID")
    void deveSalvarEBuscarProdutoPorId() {
        // Arrange
        Produto produto = new Produto(
                "Notebook", 
                "Dell Inspiron", 
                new BigDecimal("2999.99"), 
                10, 
                "Eletrônicos"
        );

        // Act
        Produto produtoSalvo = produtoRepository.salvar(produto);
        entityManager.flush();
        entityManager.clear();
        
        Optional<Produto> produtoEncontrado = produtoRepository.buscarPorId(produtoSalvo.getId());

        // Assert
        assertThat(produtoEncontrado).isPresent();
        assertThat(produtoEncontrado.get().getNome()).isEqualTo("Notebook");
        assertThat(produtoEncontrado.get().getPreco()).isEqualByComparingTo(new BigDecimal("2999.99"));
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar produto inexistente")
    void deveRetornarVazioAoBuscarProdutoInexistente() {
        // Act
        Optional<Produto> resultado = produtoRepository.buscarPorId(999L);

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve buscar todos os produtos")
    void deveBuscarTodosOsProdutos() {
        // Arrange
        ProdutoJpaEntity entity1 = new ProdutoJpaEntity(
                null, "Notebook", "Dell", new BigDecimal("2999.99"), 
                10, "Eletrônicos", LocalDateTime.now()
        );
        ProdutoJpaEntity entity2 = new ProdutoJpaEntity(
                null, "Mouse", "Logitech", new BigDecimal("50.00"), 
                20, "Eletrônicos", LocalDateTime.now()
        );
        
        entityManager.persist(entity1);
        entityManager.persist(entity2);
        entityManager.flush();

        // Act
        List<Produto> produtos = produtoRepository.buscarTodos();

        // Assert
        assertThat(produtos).hasSize(2);
        assertThat(produtos).extracting("nome")
                .containsExactlyInAnyOrder("Notebook", "Mouse");
    }

    @Test
    @DisplayName("Deve buscar produtos por categoria")
    void deveBuscarProdutosPorCategoria() {
        // Arrange
        ProdutoJpaEntity entity1 = new ProdutoJpaEntity(
                null, "Notebook", "Dell", new BigDecimal("2999.99"), 
                10, "Eletrônicos", LocalDateTime.now()
        );
        ProdutoJpaEntity entity2 = new ProdutoJpaEntity(
                null, "Camiseta", "Nike", new BigDecimal("99.99"), 
                20, "Roupas", LocalDateTime.now()
        );
        
        entityManager.persist(entity1);
        entityManager.persist(entity2);
        entityManager.flush();

        // Act
        List<Produto> produtosEletronicos = produtoRepository.buscarPorCategoria("Eletrônicos");

        // Assert
        assertThat(produtosEletronicos).hasSize(1);
        assertThat(produtosEletronicos.get(0).getCategoria()).isEqualTo("Eletrônicos");
        assertThat(produtosEletronicos.get(0).getNome()).isEqualTo("Notebook");
    }

    @Test
    @DisplayName("Deve deletar produto por ID")
    void deveDeletarProdutoPorId() {
        // Arrange
        ProdutoJpaEntity entity = new ProdutoJpaEntity(
                null, "Notebook", "Dell", new BigDecimal("2999.99"), 
                10, "Eletrônicos", LocalDateTime.now()
        );
        entityManager.persist(entity);
        entityManager.flush();
        Long id = entity.getId();

        // Act
        produtoRepository.deletar(id);
        entityManager.flush();
        entityManager.clear();

        // Assert
        ProdutoJpaEntity deletado = entityManager.find(ProdutoJpaEntity.class, id);
        assertThat(deletado).isNull();
    }

    @Test
    @DisplayName("Deve verificar se produto existe")
    void deveVerificarSeProdutoExiste() {
        // Arrange
        ProdutoJpaEntity entity = new ProdutoJpaEntity(
                null, "Notebook", "Dell", new BigDecimal("2999.99"), 
                10, "Eletrônicos", LocalDateTime.now()
        );
        entityManager.persist(entity);
        entityManager.flush();
        Long id = entity.getId();

        // Act & Assert
        assertThat(produtoRepository.existe(id)).isTrue();
        assertThat(produtoRepository.existe(999L)).isFalse();
    }
}