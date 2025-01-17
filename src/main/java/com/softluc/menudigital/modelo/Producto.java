package com.softluc.menudigital.modelo;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * Entidad que representa un producto en el sistema
 *
 * <p>Incluye informacion como nombre, categoria, descripcion, precio, lista de imagenes y usuario propietario. <p/>
 * Utiliza JPA para mapear las relaciones con la base de datos.
 */
@Entity
@Data
public class Producto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;
    private String descripcion;
    private BigDecimal precio;

    @ElementCollection // @ElementCollection genera una tabla extra donde se almacenan los valores de los nombres de las imagenes con una clave foranea de dicho producto.
    private List<String> listaImagenes;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
    private Boolean disponible;

}
