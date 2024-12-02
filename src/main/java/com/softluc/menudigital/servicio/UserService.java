package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.UsuarioResponseDTO;
import com.softluc.menudigital.modelo.Provincia;
import com.softluc.menudigital.modelo.Usuario;
import com.softluc.menudigital.repositorio.UsuarioRepositorio;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final LocalidadServicio localidadServicio;
    private final ProvinciaServicio provinciaServicio;

    @Override
    public List<UsuarioResponseDTO> obtenerTodos() {
        try{
            List<Usuario> usuarioList = usuarioRepositorio.findAll();
            List<UsuarioResponseDTO> usuarioResponseDTOList = new ArrayList<>();
            for (Usuario u: usuarioList) {
                UsuarioResponseDTO dto = new UsuarioResponseDTO(u.getId(),
                        u.getRazonSocial(),
                        u.getTelefono(),
                        u.getLogo(),
                        u.getLocalidad(),
                        u.getDireccion());
                usuarioResponseDTOList.add(dto);
            }
            return usuarioResponseDTOList;
        }catch (Exception e){
            throw new RuntimeException("Error al obtener todos los usuarios");
        }
    }

    @Override
    public List<UsuarioResponseDTO> obtenerPorLocalidad(Long idLocalidad) {
        try{
            List<Usuario> usuarioList = usuarioRepositorio.findByLocalidad(localidadServicio.obtenerPorId(idLocalidad));
            List<UsuarioResponseDTO> usuarioResponseDTOList = new ArrayList<>();
            for (Usuario u: usuarioList) {
                UsuarioResponseDTO dto = new UsuarioResponseDTO(u.getId(),
                        u.getRazonSocial(),
                        u.getTelefono(),
                        u.getLogo(),
                        u.getLocalidad(),
                        u.getDireccion());
                usuarioResponseDTOList.add(dto);
            }
            return usuarioResponseDTOList;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al obtener usuarios por localidad");
        }
    }

    @Override
    public List<UsuarioResponseDTO> obtenerPorProvincia(Long idProvincia) {
        try{
            List<Usuario> usuarioList = usuarioRepositorio.findByProvincia(provinciaServicio.obtenerPorId(idProvincia));
            List<UsuarioResponseDTO> usuarioResponseDTOList = new ArrayList<>();
            for (Usuario u: usuarioList) {
                UsuarioResponseDTO dto = new UsuarioResponseDTO(u.getId(),
                        u.getRazonSocial(),
                        u.getTelefono(),
                        u.getLogo(),
                        u.getLocalidad(),
                        u.getDireccion());
                usuarioResponseDTOList.add(dto);
            }
            return usuarioResponseDTOList;
        }catch (Exception e){
            throw new RuntimeException("Error al obtener usuarios por provincia");
        }
    }

    public Usuario obtenerPorNombre(String nombre){
        try{
            return usuarioRepositorio.findUsuarioByNombre(nombre).orElse(null);
        }catch (Exception e){
            throw new RuntimeException("Error al buscar por nombre");
        }
    }
}
