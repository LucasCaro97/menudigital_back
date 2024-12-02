package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.LocalidadDTO;
import com.softluc.menudigital.modelo.Localidad;
import com.softluc.menudigital.modelo.Provincia;
import com.softluc.menudigital.repositorio.LocalidadRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalidadServicio implements ILocalidadServicio{

    private final LocalidadRepositorio localidadRepositorio;
    private final ProvinciaServicio provinciaServicio;

    @Override
    public List<Localidad> obtenerTodas() {
        try{
            return localidadRepositorio.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener localidades");
        }
    }

    @Override
    public Localidad obtenerPorId(Long id) {
        try{
            return localidadRepositorio.findById(id).orElse(null);
        }catch (Exception e){
            throw new RuntimeException("Error al obtener localidad");
        }
    }

    @Override
    public Localidad crear(LocalidadDTO localidadDTO) {
        try{
            Localidad localidad = Localidad.builder()
                    .nombre(localidadDTO.getNombre())
                    .provincia(provinciaServicio.obtenerPorId(localidadDTO.getProvinciaId()))
                    .build();
            return localidadRepositorio.save(localidad);
        }catch (Exception e){
            throw new RuntimeException("Error al crear localidad");
        }
    }

    @Override
    public Localidad actualizar(Long id, LocalidadDTO localidadDTO) {
        try{
            Localidad localidad = this.obtenerPorId(id);
            localidad.setNombre(localidadDTO.getNombre());
            localidad.setProvincia(localidad.getProvincia());
            return localidadRepositorio.save(localidad);
        }catch (Exception e){
            throw new RuntimeException("Error al editar localidad");
        }
    }

    @Override
    public HashMap<String, String> eliminar(Long id) {
        try{
            HashMap<String, String> response = new HashMap<>();
            localidadRepositorio.deleteById(id);
            response.put("mensaje: ", "Se ha eliminado la localidad correctamente");
            return response;
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar localidad");
        }
    }

    @Override
    public List<Localidad> obtenerPorProvincia(Long idProvincia) {
        try{
            List<Localidad> localidadList = localidadRepositorio.findByProvincia(provinciaServicio.obtenerPorId(idProvincia));
            return localidadList;
        }catch (Exception e){
            throw new RuntimeException("Error al buscar localidad por provincia");
        }
    }

}
