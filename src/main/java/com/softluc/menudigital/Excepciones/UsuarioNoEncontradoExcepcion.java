package com.softluc.menudigital.Excepciones;

public class UsuarioNoEncontradoExcepcion extends RuntimeException{

    public UsuarioNoEncontradoExcepcion(String message) {
        super(message);
    }

    public UsuarioNoEncontradoExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
