package com.ecommerce.produtos.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado com id: %d", resourceName, id));
    }
}


