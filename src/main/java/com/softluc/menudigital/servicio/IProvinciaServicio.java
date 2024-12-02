package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.ProvinciaDTO;
import com.softluc.menudigital.modelo.Provincia;

import java.util.HashMap;
import java.util.List;

public interface IProvinciaServicio {

    List<Provincia> obtenerTodas();
    Provincia obtenerPorId(Long id);
    Provincia crear(ProvinciaDTO provinciaDTO);
    Provincia actualizar (Long id, ProvinciaDTO provinciaDTO);
    HashMap<String, String> eliminar ( Long id );

}
