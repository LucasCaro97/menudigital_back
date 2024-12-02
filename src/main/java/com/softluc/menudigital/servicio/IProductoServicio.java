package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.ProductoDTO;
import com.softluc.menudigital.modelo.Producto;

import java.util.HashMap;
import java.util.List;

public interface IProductoServicio {

    List<Producto> listarTodos();
    Producto obtenerPorId(Long id);
    Producto crear(ProductoDTO dto);
    Producto editar(Long id, ProductoDTO dto);
    HashMap<String, String> eliminar(Long id);


    Producto actualizarImagenes(Long id, List<String> nuevaListaImg);

    List<Producto> obtenerPorUsuario(Long idUser);
}
