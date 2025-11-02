package com.ecommerce.produtos.controller;

import com.ecommerce.produtos.dto.ProdutoRequestDTO;
import com.ecommerce.produtos.dto.ProdutoResponseDTO;
import com.ecommerce.produtos.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCriarProduto() throws Exception {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO(
                "Notebook",
                "Notebook Dell Inspiron",
                new BigDecimal("2999.99"),
                10,
                "Eletrônicos"
        );

        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(
                1L,
                "Notebook",
                "Notebook Dell Inspiron",
                new BigDecimal("2999.99"),
                10,
                "Eletrônicos",
                LocalDateTime.now()
        );

        when(produtoService.criarProduto(any(ProdutoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Notebook"))
                .andExpect(jsonPath("$.preco").value(2999.99));
    }

    @Test
    void testListarTodosProdutos() throws Exception {
        List<ProdutoResponseDTO> produtos = Arrays.asList(
                new ProdutoResponseDTO(1L, "Notebook", "Descrição", new BigDecimal("2999.99"), 10, "Eletrônicos", LocalDateTime.now()),
                new ProdutoResponseDTO(2L, "Mouse", "Descrição", new BigDecimal("50.00"), 20, "Eletrônicos", LocalDateTime.now())
        );

        when(produtoService.listarTodosProdutos()).thenReturn(produtos);

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testBuscarProdutoPorId() throws Exception {
        ProdutoResponseDTO produto = new ProdutoResponseDTO(
                1L,
                "Notebook",
                "Notebook Dell Inspiron",
                new BigDecimal("2999.99"),
                10,
                "Eletrônicos",
                LocalDateTime.now()
        );

        when(produtoService.buscarProdutoPorId(1L)).thenReturn(produto);

        mockMvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Notebook"));
    }

    @Test
    void testAtualizarProduto() throws Exception {
        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO(
                "Notebook Atualizado",
                "Descrição atualizada",
                new BigDecimal("3499.99"),
                15,
                "Eletrônicos"
        );

        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO(
                1L,
                "Notebook Atualizado",
                "Descrição atualizada",
                new BigDecimal("3499.99"),
                15,
                "Eletrônicos",
                LocalDateTime.now()
        );

        when(produtoService.atualizarProduto(eq(1L), any(ProdutoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Notebook Atualizado"))
                .andExpect(jsonPath("$.preco").value(3499.99));
    }

    @Test
    void testDeletarProduto() throws Exception {
        mockMvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarPorCategoria() throws Exception {
        List<ProdutoResponseDTO> produtos = Arrays.asList(
                new ProdutoResponseDTO(1L, "Notebook", "Descrição", new BigDecimal("2999.99"), 10, "Eletrônicos", LocalDateTime.now())
        );

        when(produtoService.buscarPorCategoria("Eletrônicos")).thenReturn(produtos);

        mockMvc.perform(get("/api/produtos/categoria/Eletrônicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].categoria").value("Eletrônicos"));
    }
}


