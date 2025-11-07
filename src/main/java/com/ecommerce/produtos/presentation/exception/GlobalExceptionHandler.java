package com.ecommerce.produtos.presentation.exception;

import com.ecommerce.produtos.domain.exception.ProdutoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/** Essa classe é responsável por tratar as exceções globais da aplicação 
 *  Isso facilita o tratamento de exceções e garante que todas as exceções sejam tratadas de forma consistente
 *  A classe @RestControllerAdvice é um componente Spring que intercepta as exceções lançadas por controladores REST
 *  Ela é responsável por capturar as exceções e converti-las em respostas HTTP
 *  A classe ErrorResponse é um record que representa a resposta de erro
 *  Ele contém o status, a mensagem e a hora da exceção
 *  A classe HashMap é usada para armazenar os erros de validação
 *  A classe LocalDateTime é usada para armazenar a hora da exceção
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNotFound(ProdutoNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    public record ErrorResponse(int status, String message, LocalDateTime timestamp) {}
}