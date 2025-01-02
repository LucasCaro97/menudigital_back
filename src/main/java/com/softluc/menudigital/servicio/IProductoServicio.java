package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.ProductoDTO;
import com.softluc.menudigital.DTO.ProductoResponseDTO;
import com.softluc.menudigital.modelo.Producto;

import java.util.HashMap;
import java.util.List;

public interface IProductoServicio {

    List<ProductoResponseDTO> listarTodos();
    ProductoResponseDTO obtenerPorId(Long id);
    Producto crear(ProductoDTO dto);
    Producto editar(Long id, ProductoDTO dto);
    HashMap<String, String> eliminar(Long id);


    Producto actualizarImagenes(Long id, List<String> nuevaListaImg);

    List<ProductoResponseDTO> obtenerPorUsuario(Long idUser);
}
