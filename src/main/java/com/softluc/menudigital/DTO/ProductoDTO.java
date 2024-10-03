package com.softluc.menudigital.DTO;

import jakarta.persistence.ElementCollection;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductoDTO {

    private String nombre;
    private Long categoria;
    private String descripcion;
    private BigDecimal precio;
    @ElementCollection
    private MultipartFile[] listaImagenes;


}
