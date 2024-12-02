package com.softluc.menudigital.servicio;

import com.softluc.menudigital.modelo.Plan;

import java.util.HashMap;
import java.util.List;

public interface IPlanServicio {

    Plan crear(Plan plan);
    List<Plan> listarTodos();
    Plan obtenerPorId(Long id);
    Plan actualizar(Long id, Plan plan) throws Exception;
    HashMap<String, String> eliminar(Long id);


}
