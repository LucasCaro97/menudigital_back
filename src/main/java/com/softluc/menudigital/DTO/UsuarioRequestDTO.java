package com.softluc.menudigital.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    private String razonSocial;
    private String telefono;
    private Long idProvincia;
    private Long idLocalidad;
    private Long idPlan;
    private String direccion;
    private MultipartFile imagenPerfil;

}
