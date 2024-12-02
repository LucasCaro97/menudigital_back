package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.ProvinciaDTO;
import com.softluc.menudigital.modelo.Provincia;
import com.softluc.menudigital.repositorio.ProvinciaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinciaServicio implements IProvinciaServicio{

    private final ProvinciaRepositorio provinciaRepositorio;


    @Override
    public List<Provincia> obtenerTodas() {
        try{
            return provinciaRepositorio.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al obtener los registros");
        }
    }

    @Override
    public Provincia obtenerPorId(Long id) {
        try{
            return provinciaRepositorio.findById(id).orElse(null);
        }catch (Exception e){
            throw new RuntimeException("Error al obtener el registro");
        }
    }

    @Override
    public Provincia crear(ProvinciaDTO provinciaDTO) {
        try{
            Provincia provincia = Provincia.builder()
                    .nombre(provinciaDTO.getNombre())
                    .build();
            return provinciaRepositorio.save(provincia);
        }catch (Exception e){
            throw new RuntimeException("Error al crear la provincia");
        }
    }

    @Override
    public Provincia actualizar(Long id, ProvinciaDTO provinciaDTO) {
        try{
            Provincia provincia = this.obtenerPorId(id);
            provincia.setNombre(provincia.getNombre());
            return provinciaRepositorio.save(provincia);
        }catch (Exception e){
            throw new RuntimeException("Error al actualizar la provincia");
        }
    }

    @Override
    public HashMap<String, String> eliminar(Long id) {
        try{
            HashMap<String, String> response = new HashMap<>();
            provinciaRepositorio.deleteById(id);
            response.put("mensaje:", "Registro eliminado correctamente");
            return response;
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar la provincia");
        }

    }
}
