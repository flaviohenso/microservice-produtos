package com.ecommerce.produtos.presantion.controller;

import com.ecommerce.produtos.application.usecase.*;
import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.domain.exception.ProdutoNotFoundException;
import com.ecommerce.produtos.presentation.controller.ProdutoController;
import com.ecommerce.produtos.presentation.dto.ProdutoRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
@DisplayName("Testes de Integração: Controller de Produto")
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CriarProdutoUseCase criarProdutoUseCase;

    @MockBean
    private BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase;

    @MockBean
    private ListarTodosProdutosUseCase listarTodosProdutosUseCase;

    @MockBean
    private AtualizarProdutoUseCase atualizarProdutoUseCase;

    @MockBean
    private DeletarProdutoUseCase deletarProdutoUseCase;

    @MockBean
    private BuscarPorCategoriaUseCase buscarPorCategoriaUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/produtos - Deve criar produto com sucesso")
    void deveCriarProdutoComSucesso() throws Exception {
        // Arrange
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO(
                "Notebook",
                "Notebook Dell Inspiron",
                new BigDecimal("2999.99"),
                10,
                "Eletrônicos"
        );

        Produto produtoCriado = new Produto(
                1L, "Notebook", "Notebook Dell Inspiron",
                new BigDecimal("2999.99"), 10, "Eletrônicos",
                LocalDateTime.now()
        );

        when(criarProdutoUseCase.executar(
                anyString(), anyString(), any(BigDecimal.class), 
                anyInt(), anyString()
        )).thenReturn(produtoCriado);

        // Act & Assert
        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Notebook"))
                .andExpect(jsonPath("$.preco").value(2999.99))
                .andExpect(jsonPath("$.estoque").value(10))
                .andExpect(jsonPath("$.categoria").value("Eletrônicos"));

        verify(criarProdutoUseCase, times(1)).executar(
                anyString(), anyString(), any(BigDecimal.class), 
                anyInt(), anyString()
        );
    }

    @Test
    @DisplayName("POST /api/produtos - Deve retornar 400 com dados inválidos")
    void deveRetornar400ComDadosInvalidos() throws Exception {
        // Arrange
        ProdutoRequestDTO requestInvalido = new ProdutoRequestDTO(
                "AB", // Nome muito curto
                "Descrição",
                new BigDecimal("2999.99"),
                10,
                "Eletrônicos"
        );

        // Act & Assert
        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());

        verify(criarProdutoUseCase, never()).executar(
                anyString(), anyString(), any(), anyInt(), anyString()
        );
    }

    @Test
    @DisplayName("GET /api/produtos - Deve listar todos os produtos")
    void deveListarTodosOsProdutos() throws Exception {
        // Arrange
        List<Produto> produtos = Arrays.asList(
                new Produto(1L, "Notebook", "Dell", new BigDecimal("2999.99"), 
                           10, "Eletrônicos", LocalDateTime.now()),
                new Produto(2L, "Mouse", "Logitech", new BigDecimal("50.00"), 
                           20, "Eletrônicos", LocalDateTime.now())
        );

        when(listarTodosProdutosUseCase.executar()).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Notebook"))
                .andExpect(jsonPath("$[1].nome").value("Mouse"));

        verify(listarTodosProdutosUseCase, times(1)).executar();
    }

    @Test
    @DisplayName("GET /api/produtos/{id} - Deve buscar produto por ID")
    void deveBuscarProdutoPorId() throws Exception {
        // Arrange
        Produto produto = new Produto(
                1L, "Notebook", "Dell Inspiron",
                new BigDecimal("2999.99"), 10, "Eletrônicos",
                LocalDateTime.now()
        );

        when(buscarProdutoPorIdUseCase.executar(1L)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Notebook"))
                .andExpect(jsonPath("$.preco").value(2999.99));

        verify(buscarProdutoPorIdUseCase, times(1)).executar(1L);
    }

    @Test
    @DisplayName("GET /api/produtos/{id} - Deve retornar 404 quando produto não existir")
    void deveRetornar404QuandoProdutoNaoExistir() throws Exception {
        // Arrange
        when(buscarProdutoPorIdUseCase.executar(999L))
                .thenThrow(new ProdutoNotFoundException(999L));

        // Act & Assert
        mockMvc.perform(get("/api/produtos/999"))
                .andExpect(status().isNotFound());

        verify(buscarProdutoPorIdUseCase, times(1)).executar(999L);
    }

    @Test
    @DisplayName("PUT /api/produtos/{id} - Deve atualizar produto")
    void deveAtualizarProduto() throws Exception {
        // Arrange
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO(
                "Notebook Atualizado",
                "Descrição atualizada",
                new BigDecimal("3499.99"),
                15,
                "Eletrônicos"
        );

        Produto produtoAtualizado = new Produto(
                1L, "Notebook Atualizado", "Descrição atualizada",
                new BigDecimal("3499.99"), 15, "Eletrônicos",
                LocalDateTime.now()
        );

        when(atualizarProdutoUseCase.executar(
                eq(1L), anyString(), anyString(), 
                any(BigDecimal.class), anyInt(), anyString()
        )).thenReturn(produtoAtualizado);

        // Act & Assert
        mockMvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Notebook Atualizado"))
                .andExpect(jsonPath("$.preco").value(3499.99))
                .andExpect(jsonPath("$.estoque").value(15));

        verify(atualizarProdutoUseCase, times(1)).executar(
                eq(1L), anyString(), anyString(), 
                any(BigDecimal.class), anyInt(), anyString()
        );
    }

    @Test
    @DisplayName("DELETE /api/produtos/{id} - Deve deletar produto")
    void deveDeletarProduto() throws Exception {
        // Arrange
        doNothing().when(deletarProdutoUseCase).executar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNoContent());

        verify(deletarProdutoUseCase, times(1)).executar(1L);
    }

    @Test
    @DisplayName("GET /api/produtos/categoria/{categoria} - Deve buscar por categoria")
    void deveBuscarPorCategoria() throws Exception {
        // Arrange
        List<Produto> produtos = Arrays.asList(
                new Produto(1L, "Notebook", "Dell", new BigDecimal("2999.99"), 
                           10, "Eletrônicos", LocalDateTime.now())
        );

        when(buscarPorCategoriaUseCase.executar("Eletrônicos")).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/produtos/categoria/Eletrônicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].categoria").value("Eletrônicos"));

        verify(buscarPorCategoriaUseCase, times(1)).executar("Eletrônicos");
    }
}