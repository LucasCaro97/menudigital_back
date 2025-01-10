package com.softluc.menudigital.controlador;

import com.softluc.menudigital.DTO.ProductoDTO;
import com.softluc.menudigital.DTO.ProductoResponseDTO;
import com.softluc.menudigital.Excepciones.LimitePlatosExcepcion;
import com.softluc.menudigital.modelo.Producto;
import com.softluc.menudigital.servicio.ImagenServicio;
import com.softluc.menudigital.servicio.ProductoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoControlador {

    private final ProductoServicio productoServicio;
    private final ImagenServicio imagenServicio;

    @GetMapping
    public ResponseEntity<?> getAll(){
        try{
            List<ProductoResponseDTO> productoLista = productoServicio.listarTodos();
            return ResponseEntity.ok(productoLista);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getById(@PathVariable Long id){
        try{
            ProductoResponseDTO producto = productoServicio.obtenerPorId(id);
            if(producto!=null) return ResponseEntity.ok(producto);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un producto con el id proporcionado");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestParam("nombre") String nombre,
                                    @RequestParam("categoria") Long categoria,
                                    @RequestParam("descripcion") String descripcion,
                                    @RequestParam("precio") BigDecimal precio,
                                    @RequestParam(value = "imagen", required = false) MultipartFile[] listaImagenes,
                                    @RequestParam("idUsuario") Long idUsuario){
        Producto producto = productoServicio.crear(new ProductoDTO(nombre, categoria, descripcion, precio, listaImagenes, idUsuario));
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestParam("nombre") String nombre,
                                    @RequestParam("categoria") Long categoria,
                                    @RequestParam("descripcion") String descripcion,
                                    @RequestParam("precio") BigDecimal precio,
                                    @RequestParam(value = "imagen", required = false) MultipartFile[] listaImagenes,
                                    @RequestParam("idUsuario") Long idUsuario){
        try{
            Producto producto = productoServicio.editar(id, new ProductoDTO(nombre, categoria, descripcion, precio, listaImagenes, idUsuario));
            return ResponseEntity.ok(producto);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        try{
            HashMap<String, String> response = productoServicio.eliminar(id);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/deleteImage/{nameImg}")
    public ResponseEntity<?> deleteImageByName(@PathVariable Long id, @PathVariable String nameImg){
        try{
            ProductoResponseDTO producto = productoServicio.obtenerPorId(id);
            List<String> nuevaListaImg = producto.getListaImagenes().stream().filter(item -> !item.equals(nameImg)).collect(Collectors.toList());
            productoServicio.actualizarImagenes(producto.getId(), nuevaListaImg);
            imagenServicio.eliminarImagen(nameImg);
            return ResponseEntity.ok("Imagen actualizada correctamente");

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error al eliminar la imagen");
        }

    }

    @GetMapping("/getAll/{idUser}")
    public ResponseEntity<?> getAllByUser(@PathVariable Long idUser){
        try{
            List<ProductoResponseDTO> productoList = productoServicio.obtenerPorUsuario(idUser);
            return ResponseEntity.ok(productoList);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/getAllByName/{nameUser}")
    public ResponseEntity<?> getAllByUser(@PathVariable String nameUser){
        try{
            List<ProductoResponseDTO> productoList = productoServicio.obtenerPorNombreUsuario(nameUser);
            return ResponseEntity.ok(productoList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }





}
