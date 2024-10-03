package com.softluc.menudigital.controlador;

import com.softluc.menudigital.DTO.CategoriaDTO;
import com.softluc.menudigital.modelo.Categoria;
import com.softluc.menudigital.servicio.CategoriaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        try{
            List<Categoria> categoriaList = categoriaServicio.listarTodos();
            if(categoriaList.size() == 0) return ResponseEntity.ok(categoriaList);
            else return ResponseEntity.ok(categoriaList);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            Categoria categoria = categoriaServicio.obtenerPorId(id);
            if(categoria!=null) return ResponseEntity.ok(categoria);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una categoria con el id proporcionado");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CategoriaDTO categoriaDTO){
        try{
            Categoria categoria = categoriaServicio.crear(categoriaDTO);
            return ResponseEntity.ok(categoria);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO){
        try{
            Categoria categoria = categoriaServicio.editar(id, categoriaDTO);
            return ResponseEntity.ok(categoria);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        try{
            HashMap<String, String> response = categoriaServicio.eliminar(id);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
