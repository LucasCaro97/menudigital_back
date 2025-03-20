package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.LocalidadResponseDTO;
import com.softluc.menudigital.DTO.UsuarioRequestDTO;
import com.softluc.menudigital.DTO.UsuarioResponseDTO;
import com.softluc.menudigital.modelo.Usuario;
import com.softluc.menudigital.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final LocalidadServicio localidadServicio;
    private final ProvinciaServicio provinciaServicio;
    private final PlanServicio planServicio;
    private final ImagenServicio imagenServicio;

    @Override
    public List<UsuarioResponseDTO> obtenerTodos() {
        try{
            List<Usuario> usuarioList = usuarioRepositorio.findAll();
            List<UsuarioResponseDTO> usuarioResponseDTOList = new ArrayList<>();
            for (Usuario u: usuarioList) {
                if(u.getNombre().equals("admin")) {
                    continue;
                }
                UsuarioResponseDTO dto = new UsuarioResponseDTO(u.getId(),
                    u.getRazonSocial(),
                    u.getTelefono(),
                    u.getLogo(),
                    u.getProvincia(),
                    new LocalidadResponseDTO(u.getLocalidad().getId(), u.getLocalidad().getNombre()),
                    u.getDireccion(),
                    u.getPlan());
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
                if(u.getNombre().equals("admin")) break;
                UsuarioResponseDTO dto = new UsuarioResponseDTO(u.getId(),
                        u.getRazonSocial(),
                        u.getTelefono(),
                        u.getLogo(),
                        u.getProvincia(),
                        new LocalidadResponseDTO(u.getLocalidad().getId(), u.getLocalidad().getNombre()),
                        u.getDireccion(),
                        u.getPlan());
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
                if(u.getNombre().equals("admin")) break;
                UsuarioResponseDTO dto = new UsuarioResponseDTO(u.getId(),
                        u.getRazonSocial(),
                        u.getTelefono(),
                        u.getLogo(),
                        u.getProvincia(),
                        new LocalidadResponseDTO(u.getLocalidad().getId(), u.getLocalidad().getNombre()),
                        u.getDireccion(),
                        u.getPlan());
                usuarioResponseDTOList.add(dto);
            }
            return usuarioResponseDTOList;
        }catch (Exception e){
            throw new RuntimeException("Error al obtener usuarios por provincia");
        }
    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        try{
            Usuario u = usuarioRepositorio.findById(id).orElse(null);
            if(u.getNombre().equals("admin")) return null;
            return new UsuarioResponseDTO(u.getId(),
                    u.getRazonSocial(),
                    u.getTelefono(),
                    u.getLogo(),
                    u.getProvincia(),
                    new LocalidadResponseDTO(u.getLocalidad().getId(), u.getLocalidad().getNombre()),
                    u.getDireccion(),
                    u.getPlan());
        }catch (Exception e ){
            throw new RuntimeException("Error al buscar usuario por id");
        }
    }

    @Override
    public UsuarioResponseDTO actualizarPorId(Long id, UsuarioRequestDTO dto) {
        try{
            Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
            if(usuario!=null){
                usuario.setRazonSocial(dto.getRazonSocial());
                usuario.setTelefono(Long.parseLong(dto.getTelefono()));
                usuario.setProvincia(provinciaServicio.obtenerPorId(dto.getIdProvincia()));
                usuario.setLocalidad(localidadServicio.obtenerPorId(dto.getIdLocalidad()));
                usuario.setDireccion(dto.getDireccion());
                usuario.setPlan(planServicio.obtenerPorId(dto.getIdPlan()));
                if(dto.getImagenPerfil() != null){
                    String nombreImagenEliminar = usuario.getLogo();
                    usuario.setLogo(imagenServicio.almacenarImagen(dto.getImagenPerfil()));
                    if(nombreImagenEliminar != null) imagenServicio.eliminarImagen(nombreImagenEliminar);
                }
            }
            usuarioRepositorio.save(usuario);
            UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO(usuario.getId(),
                    usuario.getRazonSocial(),
                    usuario.getTelefono(),
                    usuario.getLogo(),
                    usuario.getProvincia(),
                    new LocalidadResponseDTO(usuario.getLocalidad().getId(), usuario.getLocalidad().getNombre()),
                    usuario.getDireccion(),
                    usuario.getPlan());
            return usuarioDTO;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el usuario");
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
