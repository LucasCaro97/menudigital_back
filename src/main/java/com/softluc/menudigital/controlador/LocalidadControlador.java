package com.softluc.menudigital.controlador;

import com.softluc.menudigital.DTO.LocalidadDTO;
import com.softluc.menudigital.modelo.Localidad;
import com.softluc.menudigital.modelo.Provincia;
import com.softluc.menudigital.servicio.LocalidadServicio;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/localidad")
public class LocalidadControlador {

    private final LocalidadServicio localidadServicio;

    @GetMapping
    ResponseEntity<?> obtenerLocalidades(){
        try{
            return ResponseEntity.ok(localidadServicio.obtenerTodas());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        try{
            Localidad localidad = localidadServicio.obtenerPorId(id);
            return ResponseEntity.ok(localidad);
        }catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @PostMapping
    ResponseEntity<?> crearLocalidad(@RequestBody LocalidadDTO localidadDTO){
        try{
            Localidad nuevaLocalidad = localidadServicio.crear(localidadDTO);
            return ResponseEntity.ok(nuevaLocalidad);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> actualizarLocalidad(@PathVariable Long id, @RequestBody LocalidadDTO localidadDTO){
        try{
            Localidad LocalidadActualizada = localidadServicio.actualizar(id, localidadDTO);
            return ResponseEntity.ok(LocalidadActualizada);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> eliminarLocalidad(@PathVariable Long id){
        try{
            HashMap<String, String> response = localidadServicio.eliminar(id);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/obtenerPorProvincia/{idProvincia}")
    ResponseEntity<?> obtenerPorProvincia(@PathVariable Long idProvincia){
        try{
            return ResponseEntity.ok(localidadServicio.obtenerPorProvincia(idProvincia));
        }catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}

