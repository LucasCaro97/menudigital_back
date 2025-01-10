package com.softluc.menudigital.Excepciones;

public class ProductoNoEncontradoExcepcion extends RuntimeException{

    public ProductoNoEncontradoExcepcion(String message) {
        super(message);
    }

    public ProductoNoEncontradoExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
