package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.CategoriaDTO;
import com.softluc.menudigital.modelo.Categoria;
import com.softluc.menudigital.repositorio.CategoriaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicio implements ICategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;


    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listarTodos() {
        try{
            return categoriaRepositorio.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar todas las categorias");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Categoria obtenerPorId(Long id) {
        try{
            return categoriaRepositorio.findById(id).orElse(null);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error al buscar categoria por id");
        }
    }

    @Override
    @Transactional
    public Categoria crear(CategoriaDTO dto) {
        try{
            Categoria categoria = new Categoria();
            categoria.setNombre(dto.getNombre());
            return categoriaRepositorio.save(categoria);
        }catch (Exception e){
            throw new RuntimeException("Error al crear la categoria");
        }

    }

    @Override
    @Transactional
    public Categoria editar(Long id, CategoriaDTO dto) {
        try{
            Categoria categoria = this.obtenerPorId(id);
            categoria.setNombre(dto.getNombre());
            return categoriaRepositorio.save(categoria);
        }catch (Exception e){
            throw new RuntimeException("Error al editar la categoria");
        }
    }

    @Override
    @Transactional
    public HashMap<String, String> eliminar(Long id) {
        try{
            categoriaRepositorio.deleteById(id);
            HashMap<String, String> response = new HashMap<>();
            response.put("mensaje", "Categoria eliminada exitosamente");
            return response;
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar la categoria");
        }
    }
}
