package com.softluc.menudigital.DTO;

import com.softluc.menudigital.modelo.Provincia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocalidadDTO {

    private String nombre;
    private Long provinciaId;

}
