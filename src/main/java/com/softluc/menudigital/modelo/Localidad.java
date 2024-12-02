package com.softluc.menudigital.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Localidad {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(nullable = false, unique = true)
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "fk_provincia")
    private Provincia provincia;




}
