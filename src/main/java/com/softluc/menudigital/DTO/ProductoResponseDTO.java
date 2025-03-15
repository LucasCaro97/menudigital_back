package com.softluc.menudigital.DTO;

import com.softluc.menudigital.modelo.Categoria;
import com.softluc.menudigital.modelo.Producto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Categoria categoria;
    private BigDecimal precio;
    private List<String> listaImagenes;
    private String usuario;
    private boolean disponible;

    public ProductoResponseDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.categoria = producto.getCategoria();
        this.precio = producto.getPrecio();
        this.listaImagenes = producto.getListaImagenes();
        this.usuario = producto.getUsuario().getRazonSocial();
        this.disponible = producto.getDisponible();
    }
}
