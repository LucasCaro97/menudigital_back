package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.ProductoDTO;
import com.softluc.menudigital.modelo.Producto;
import com.softluc.menudigital.repositorio.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServicio implements  IProductoServicio{

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaServicio categoriaServicio;
    private final ImagenServicio imagenServicio;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        try{
            return productoRepositorio.findAll();
        }catch (Exception e){
            throw new RuntimeException("Error al listar los productos");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        try{
            return productoRepositorio.findById(id).orElse(null);
        }catch (Exception e){
            throw new RuntimeException("Error al buscar el producto por id");
        }
    }

    @Override
    public Producto crear(ProductoDTO dto) {
        try{
            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setCategoria(categoriaServicio.obtenerPorId(dto.getCategoria()));
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setListaImagenes(imagenServicio.almacenarImagenes(dto.getListaImagenes()));
            return productoRepositorio.save(producto);
        }catch (Exception e){
            throw new RuntimeException("Error al crear el producto");
        }
    }

    @Override
    public Producto editar(Long id, ProductoDTO dto) {
        try{
            Producto producto = this.obtenerPorId(id);
            producto.setNombre(dto.getNombre());
            producto.setCategoria(categoriaServicio.obtenerPorId(dto.getCategoria()));
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setListaImagenes(imagenServicio.almacenarImagenes(dto.getListaImagenes(), producto.getListaImagenes()));
            return productoRepositorio.save(producto);
        }catch (Exception e){
            throw new RuntimeException("Error al editar el producto");
        }
    }

    @Override
    public HashMap<String, String> eliminar(Long id) {
        try{
            productoRepositorio.deleteById(id);
            HashMap<String, String> response = new HashMap<>();
            response.put("mensaje", "Se ha eliminado el registro correctamente");
            return response;
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el registro");
        }
    }

    @Override
    public Producto actualizarImagenes(Long id, List<String> nuevaListaImg) {
        try{
            Producto producto = this.obtenerPorId(id);
            producto.setListaImagenes(nuevaListaImg);
            return productoRepositorio.save(producto);
        }catch (Exception e){
            throw new RuntimeException("Error al actualizar la imagen");
        }
    }
}
