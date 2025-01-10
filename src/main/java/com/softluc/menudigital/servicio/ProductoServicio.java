package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.ProductoDTO;
import com.softluc.menudigital.DTO.ProductoResponseDTO;
import com.softluc.menudigital.Excepciones.CategoriaNoEncontradaExcepcion;
import com.softluc.menudigital.Excepciones.LimitePlatosExcepcion;
import com.softluc.menudigital.Excepciones.ProductoNoEncontradoExcepcion;
import com.softluc.menudigital.Excepciones.UsuarioNoEncontradoExcepcion;
import com.softluc.menudigital.modelo.Categoria;
import com.softluc.menudigital.modelo.Producto;
import com.softluc.menudigital.modelo.Usuario;
import com.softluc.menudigital.repositorio.ProductoRepositorio;
import com.softluc.menudigital.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
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


    /**
     * <p>Este metodo realiza las siguientes validaciones antes de crear el producto</p>
     * <ul>
     *     <li>Verifica si el usuario existe en la BD.</li>
     *     <li>Comprueba si el usuario no ha superado el limite permitido de platos para el plan que tiene seleccionado.</li>
     *     <li>Verifica si la categoria especificada existe.</li>
     *     <li></li>
     * </ul>
     * @param dto un {@link ProductoDTO} que contiene la informacion necesaria para crear el producto.
     *            <ul>
     *              <li><b>idUsuario</b>: ID del usuario al que se asociará el producto.</li>
     *              <li><b>categoria</b>: ID de la categoría del producto.</li>
     *              <li><b>nombre</b>: Nombre del producto.</li>
     *              <li><b>descripcion</b>: Descripción del producto.</li>
     *              <li><b>precio</b>: Precio del producto.</li>
     *              <li><b>listaImagenes</b>: Lista de imágenes asociadas al producto.</li>
     *            </ul>
     * @return el {@link Producto} creado y guardado en la base de datos.
     * @throws UsuarioNoEncontradoExcepcion si el usuario especificado no existe en la BD.
     * @throws LimitePlatosExcepcion si el usuario ha alcanzado el limite de platos permitido
     * @throws CategoriaNoEncontradaExcepcion si la categoria especificada no existe.
     */
    @Override
    public Producto crear(ProductoDTO dto) {
            //Obtengo el usuario de la BD
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null);
            //Obtengo la cantidad de productos del usuario
            Integer cantidadProductos = productoRepositorio.findByUsuario(usuario).size();
            //Obtengo la categoria
            Categoria categoria = categoriaServicio.obtenerPorId(dto.getCategoria());

            if(usuario == null) throw new UsuarioNoEncontradoExcepcion("El usuario no existe");
            if(cantidadProductos >= usuario.getPlan().getCantidadProductos()) throw new LimitePlatosExcepcion("Usted alcanzo el limite de productos creados");
            if(categoria == null) throw new CategoriaNoEncontradaExcepcion("La categoria no existe");

            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setCategoria(categoria);
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setListaImagenes(imagenServicio.almacenarImagenes(dto.getListaImagenes()));
            producto.setUsuario(usuario);
            return productoRepositorio.save(producto);
    }

    @Override
    public Producto editar(Long id, ProductoDTO dto) {
            Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario()).orElse(null);
            Producto producto = productoRepositorio.findById(id).orElse(null);
            Categoria categoria = categoriaServicio.obtenerPorId(dto.getCategoria());

            if(usuario == null) throw new UsuarioNoEncontradoExcepcion("El usuario no existe");
            if(producto == null) throw new ProductoNoEncontradoExcepcion("El producto no existe");
            if(categoria == null) throw new CategoriaNoEncontradaExcepcion("La categoria no existe");

            producto.setNombre(dto.getNombre());
            producto.setCategoria(categoria);
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setListaImagenes(imagenServicio.almacenarImagenes(dto.getListaImagenes(), producto.getListaImagenes()));
            producto.setUsuario(usuario);
            return productoRepositorio.save(producto);
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
