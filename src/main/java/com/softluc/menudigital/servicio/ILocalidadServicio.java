package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.LocalidadDTO;
import com.softluc.menudigital.modelo.Localidad;
import com.softluc.menudigital.modelo.Provincia;

import java.util.HashMap;
import java.util.List;

public interface ILocalidadServicio {

    List<Localidad> obtenerTodas();
    Localidad obtenerPorId(Long id);
    Localidad crear (LocalidadDTO localidadDTO);
    Localidad actualizar(Long id, LocalidadDTO localidadDTO);
    HashMap<String, String> eliminar ( Long id );

    List<Localidad> obtenerPorProvincia(Long id);

}
