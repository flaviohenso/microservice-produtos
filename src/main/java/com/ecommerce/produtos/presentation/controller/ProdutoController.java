package com.ecommerce.produtos.presentation.controller;

import com.ecommerce.produtos.application.usecase.*;
import com.ecommerce.produtos.domain.entity.Produto;
import com.ecommerce.produtos.presentation.dto.ProdutoRequestDTO;
import com.ecommerce.produtos.presentation.dto.ProdutoResponseDTO;
import com.ecommerce.produtos.presentation.mapper.ProdutoDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProdutoController {

    private final CriarProdutoUseCase criarProdutoUseCase;
    private final BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase;
    private final ListarTodosProdutosUseCase listarTodosProdutosUseCase;
    private final AtualizarProdutoUseCase atualizarProdutoUseCase;
    private final DeletarProdutoUseCase deletarProdutoUseCase;
    private final BuscarPorCategoriaUseCase buscarPorCategoriaUseCase;

    public ProdutoController(
            CriarProdutoUseCase criarProdutoUseCase,
            BuscarProdutoPorIdUseCase buscarProdutoPorIdUseCase,
            ListarTodosProdutosUseCase listarTodosProdutosUseCase,
            AtualizarProdutoUseCase atualizarProdutoUseCase,
            DeletarProdutoUseCase deletarProdutoUseCase,
            BuscarPorCategoriaUseCase buscarPorCategoriaUseCase) {
        this.criarProdutoUseCase = criarProdutoUseCase;
        this.buscarProdutoPorIdUseCase = buscarProdutoPorIdUseCase;
        this.listarTodosProdutosUseCase = listarTodosProdutosUseCase;
        this.atualizarProdutoUseCase = atualizarProdutoUseCase;
        this.deletarProdutoUseCase = deletarProdutoUseCase;
        this.buscarPorCategoriaUseCase = buscarPorCategoriaUseCase;
    }

    @Operation(summary = "Criar novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(
            @Valid @RequestBody ProdutoRequestDTO request) {
        
        Produto produto = criarProdutoUseCase.executar(
                request.nome(),
                request.descricao(),
                request.preco(),
                request.estoque(),
                request.categoria()
        );
        
        ProdutoResponseDTO response = ProdutoDTOMapper.toResponseDTO(produto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos os produtos")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos() {
        List<Produto> produtos = listarTodosProdutosUseCase.executar();
        
        List<ProdutoResponseDTO> response = produtos.stream()
                .map(ProdutoDTOMapper::toResponseDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        
        Produto produto = buscarProdutoPorIdUseCase.executar(id);
        ProdutoResponseDTO response = ProdutoDTOMapper.toResponseDTO(produto);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO request) {
        
        Produto produto = atualizarProdutoUseCase.executar(
                id,
                request.nome(),
                request.descricao(),
                request.preco(),
                request.estoque(),
                request.categoria()
        );
        
        ProdutoResponseDTO response = ProdutoDTOMapper.toResponseDTO(produto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        deletarProdutoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar produtos por categoria")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorCategoria(
            @PathVariable String categoria) {
        
        List<Produto> produtos = buscarPorCategoriaUseCase.executar(categoria);
        
        List<ProdutoResponseDTO> response = produtos.stream()
                .map(ProdutoDTOMapper::toResponseDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
}