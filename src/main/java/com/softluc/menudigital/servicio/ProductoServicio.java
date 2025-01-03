package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.ProductoDTO;
import com.softluc.menudigital.DTO.ProductoResponseDTO;
import com.softluc.menudigital.modelo.Producto;
import com.softluc.menudigital.modelo.Usuario;
import com.softluc.menudigital.repositorio.ProductoRepositorio;
import com.softluc.menudigital.repositorio.UsuarioRepositorio;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServicio implements  IProductoServicio{

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaServicio categoriaServicio;
    private final ImagenServicio imagenServicio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarTodos() {
        try{

            List<Producto> productoList = productoRepositorio.findAll();
            List<ProductoResponseDTO> productoResponseDTOList = new ArrayList<>();
            for (Producto p: productoList) {
                productoResponseDTOList.add(new ProductoResponseDTO(p));
            }
            return productoResponseDTOList;
        }catch (Exception e){
            throw new RuntimeException("Error al listar los productos");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        try{

            Producto producto = productoRepositorio.findById(id).orElse(null);
            return new ProductoResponseDTO(producto);
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
            producto.setUsuario(usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null));
            return productoRepositorio.save(producto);
        }catch (Exception e){
            throw new RuntimeException("Error al crear el producto");
        }
    }

    @Override
    public Producto editar(Long id, ProductoDTO dto) {
        try{
            Producto producto = productoRepositorio.findById(id).orElse(null);
            producto.setNombre(dto.getNombre());
            producto.setCategoria(categoriaServicio.obtenerPorId(dto.getCategoria()));
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setListaImagenes(imagenServicio.almacenarImagenes(dto.getListaImagenes(), producto.getListaImagenes()));
            producto.setUsuario(usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null));
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
            Producto producto = productoRepositorio.findById(id).orElse(null);
            producto.setListaImagenes(nuevaListaImg);
            return productoRepositorio.save(producto);
        }catch (Exception e){
            throw new RuntimeException("Error al actualizar la imagen");
        }
    }

    @Override
    public List<ProductoResponseDTO> obtenerPorUsuario(Long idUser) {
        try {
            Usuario usuario = usuarioRepositorio.findById(idUser).orElse(null);
            List<Producto> productoList = productoRepositorio.findByUsuario(usuario);
            List<ProductoResponseDTO> productoResponseDTOList = new ArrayList<>();
            for (Producto p: productoList) {
                productoResponseDTOList.add(new ProductoResponseDTO(p));
            }

            return productoResponseDTOList;
        }catch (Exception e){
            throw new RuntimeException("Error al obtener los productos por usuario");
        }
    }

    public List<ProductoResponseDTO> obtenerPorNombreUsuario(String nameUser) {
        try{
            Usuario usuario = usuarioRepositorio.findUsuarioByRazonSocial(nameUser).orElse(null);
            List<Producto> productoList = productoRepositorio.findByUsuario(usuario);
            List<ProductoResponseDTO> productoResponseDTOList = new ArrayList<>();
            for (Producto p: productoList) {
                productoResponseDTOList.add(new ProductoResponseDTO(p));
            }

            return productoResponseDTOList;
        }catch (Exception e){
            throw new RuntimeException("Error al obtener los productos por nombre de usuario");
        }
    }
}
