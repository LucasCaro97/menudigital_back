package com.softluc.menudigital.DTO;

import com.softluc.menudigital.modelo.Localidad;
import com.softluc.menudigital.modelo.Provincia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String razonSocial;
    private Long telefono;
    private String logo;
    private Localidad localidad;
    private String direccion;

}
