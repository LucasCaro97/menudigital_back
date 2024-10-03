package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.CategoriaDTO;
import com.softluc.menudigital.modelo.Categoria;

import java.util.HashMap;
import java.util.List;

public interface ICategoriaServicio {

    List<Categoria> listarTodos();
    Categoria obtenerPorId(Long id);
    Categoria crear(CategoriaDTO dto);
    Categoria editar(Long id, CategoriaDTO dto);
    HashMap<String, String> eliminar(Long id);


}
