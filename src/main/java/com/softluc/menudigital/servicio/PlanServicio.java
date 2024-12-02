package com.softluc.menudigital.servicio;

import com.softluc.menudigital.modelo.Plan;
import com.softluc.menudigital.repositorio.PlanRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServicio implements IPlanServicio {

    private final PlanRepositorio planRepositorio;

    @Override
    public Plan crear(Plan plan) {
        try{
            Plan nuevoPlan = Plan.builder()
                    .nombre(plan.getNombre())
                    .descripcion(plan.getDescripcion())
                    .cantidadProductos(plan.getCantidadProductos())
                    .precio(plan.getPrecio())
                    .build();

            return planRepositorio.save(nuevoPlan);

        }catch (Exception e){
            throw new RuntimeException("Error al crear el plan");
        }
    }

    @Override
    public List<Plan> listarTodos() {
        return planRepositorio.findAll();
    }

    @Override
    public Plan obtenerPorId(Long id) {
        return planRepositorio.findById(id).orElse(null);
    }

    @Override
    public Plan actualizar(Long id, Plan planDTO) throws Exception {
        try{
            Plan plan = this.obtenerPorId(id);
            plan.setNombre(planDTO.getNombre());
            plan.setDescripcion(planDTO.getDescripcion());
            plan.setCantidadProductos(planDTO.getCantidadProductos());
            plan.setPrecio(planDTO.getPrecio());
            return planRepositorio.save(plan);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public HashMap<String, String> eliminar(Long id) {
        try{
            HashMap<String, String> respuesta = new HashMap<>();
            planRepositorio.deleteById(id);
            respuesta.put("message", "Registro eliminado correctamente");
            return respuesta;
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el plan");
        }

    }
}
