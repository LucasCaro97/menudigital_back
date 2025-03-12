package com.softluc.menudigital.controlador;

import com.softluc.menudigital.DTO.ProvinciaDTO;
import com.softluc.menudigital.modelo.Provincia;
import com.softluc.menudigital.servicio.ProvinciaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/provincia")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProvinciaControlador {

    private final ProvinciaServicio provinciaServicio;

    @GetMapping
    ResponseEntity<?> obtenerProvincias(){
        try{
            return ResponseEntity.ok(provinciaServicio.obtenerTodas());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        try{
            Provincia provincia = provinciaServicio.obtenerPorId(id);
            return ResponseEntity.ok(provincia);
        }catch (Exception e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @PostMapping
    ResponseEntity<?> crearProvincia(@RequestBody ProvinciaDTO provincia){
        try{
            Provincia nuevaProv = provinciaServicio.crear(provincia);
            return ResponseEntity.ok(nuevaProv);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> actualizarProvincia(@PathVariable Long id, @RequestBody ProvinciaDTO provincia){
        try{
            Provincia provActualizada = provinciaServicio.actualizar(id, provincia);
            return ResponseEntity.ok(provActualizada);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> eliminarProvincia(@PathVariable Long id){
        try{
            HashMap<String, String> response = provinciaServicio.eliminar(id);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



}
