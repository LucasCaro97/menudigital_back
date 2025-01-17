package com.softluc.menudigital.Excepciones;

import com.softluc.menudigital.DTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejar excepciones específicas (por ejemplo, LimiteProductosAlcanzadoException)
    @ExceptionHandler(LimitePlatosExcepcion.class)
    public ResponseEntity<ErrorResponse> handleLimiteProductosAlcanzadosException(LimitePlatosExcepcion ex){
        // Crea una respuesta estructurada con el código y el mensaje del error
        ErrorResponse errorResponse = new ErrorResponse("LIMITE_PRODUCTOS_ALCANZADOS", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoriaNoEncontradaExcepcion.class)
    public ResponseEntity<ErrorResponse> handleCategoriaNoEncontradaExcepcion(CategoriaNoEncontradaExcepcion ex){
        // Crea una respuesta estructurada con el código y el mensaje del error
        ErrorResponse errorResponse = new ErrorResponse("CATEGORIA_NO_ENCONTRADA", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioNoEncontradoExcepcion.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNoEncontradoExcepcion(UsuarioNoEncontradoExcepcion ex){
        // Crea una respuesta estructurada con el código y el mensaje del error
        ErrorResponse errorResponse = new ErrorResponse("USUARIO_NO_ENCONTRADO", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // Crea una respuesta genérica para cualquier otra excepción no específica
        ErrorResponse errorResponse = new ErrorResponse("ERROR_INTERNO", "Ocurrió un error inesperado");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
