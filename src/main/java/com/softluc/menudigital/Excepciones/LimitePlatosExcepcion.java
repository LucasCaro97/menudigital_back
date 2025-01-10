package com.softluc.menudigital.Excepciones;

public class LimitePlatosExcepcion extends RuntimeException {

    public LimitePlatosExcepcion(String message) {
        super("Limite de platos alcanzado");
    }

    public LimitePlatosExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
