package com.softluc.menudigital.controlador;

import com.softluc.menudigital.servicio.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UserService userService;

    @GetMapping()
    ResponseEntity<?> obtenerTodos(){
        try{
            return ResponseEntity.ok(userService.obtenerTodos());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/provincia/{id}")
    ResponseEntity<?> obtenerPorProvincia(@PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.obtenerPorProvincia(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/localidad/{id}")
    ResponseEntity<?> obtenerPorLocalidad(@PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.obtenerPorLocalidad(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
