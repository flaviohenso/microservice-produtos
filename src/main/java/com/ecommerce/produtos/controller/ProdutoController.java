package com.ecommerce.produtos.controller;

import com.ecommerce.produtos.dto.ProdutoRequestDTO;
import com.ecommerce.produtos.dto.ProdutoResponseDTO;
import com.ecommerce.produtos.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Criar novo produto", description = "Cria um novo produto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(
            @Parameter(description = "Dados do produto a ser criado", required = true)
            @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoCriado = produtoService.criarProduto(produtoRequestDTO);
        return new ResponseEntity<>(produtoCriado, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos() {
        List<ProdutoResponseDTO> produtos = produtoService.listarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(
            @Parameter(description = "ID do produto", required = true, example = "1")
            @PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @Parameter(description = "ID do produto", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do produto", required = true)
            @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoRequestDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Deletar produto", description = "Remove um produto do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(
            @Parameter(description = "ID do produto", required = true, example = "1")
            @PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar produtos por categoria", description = "Retorna uma lista de produtos filtrados por categoria")
    @ApiResponse(responseCode = "200", description = "Lista de produtos da categoria retornada com sucesso")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorCategoria(
            @Parameter(description = "Categoria dos produtos", required = true, example = "Eletrônicos")
            @PathVariable String categoria) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }
}


