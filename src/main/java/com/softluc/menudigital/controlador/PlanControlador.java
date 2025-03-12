package com.softluc.menudigital.controlador;

import com.softluc.menudigital.modelo.Plan;
import com.softluc.menudigital.servicio.PlanServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanControlador {

    private final PlanServicio planServicio;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        try{
            List<Plan> planList = planServicio.listarTodos();
            return ResponseEntity.ok(planList);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            Plan plan = planServicio.obtenerPorId(id);
            if(plan!=null) return ResponseEntity.ok(plan);
            else return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Plan planDTO){
        try{
            Plan plan = planServicio.crear(planDTO);
            return ResponseEntity.ok(plan);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,  @RequestBody Plan planDTO){
        try{
            System.out.println("Id: " + id + " Plan: " + planDTO);

            Plan planActualizado = planServicio.actualizar(id, planDTO);
            return ResponseEntity.ok(planActualizado);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long id){
        try{
            HashMap<String, String> respuesta = planServicio.eliminar(id);
            return ResponseEntity.ok(respuesta);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
