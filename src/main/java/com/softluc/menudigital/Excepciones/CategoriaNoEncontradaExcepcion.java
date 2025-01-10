package com.softluc.menudigital.Excepciones;

public class CategoriaNoEncontradaExcepcion extends RuntimeException{

    public CategoriaNoEncontradaExcepcion(String message) {
        super(message);
    }

    public CategoriaNoEncontradaExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
