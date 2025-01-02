package com.softluc.menudigital.servicio;


import com.softluc.menudigital.DTO.UsuarioRequestDTO;
import com.softluc.menudigital.DTO.UsuarioResponseDTO;
import com.softluc.menudigital.modelo.Usuario;

import java.util.List;

public interface IUserService {

    List<UsuarioResponseDTO> obtenerTodos();
    List<UsuarioResponseDTO> obtenerPorLocalidad(Long idLocalidad);
    List<UsuarioResponseDTO> obtenerPorProvincia(Long idProvincia);
     UsuarioResponseDTO obtenerPorId(Long id);

     UsuarioResponseDTO actualizarPorId(Long id, UsuarioRequestDTO dto);

}
