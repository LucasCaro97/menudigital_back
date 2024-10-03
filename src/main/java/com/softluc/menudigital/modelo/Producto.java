package com.softluc.menudigital.modelo;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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

}
