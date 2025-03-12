package com.softluc.menudigital.controlador;

import com.softluc.menudigital.DTO.UsuarioRequestDTO;
import com.softluc.menudigital.DTO.UsuarioResponseDTO;
import com.softluc.menudigital.servicio.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UserService userService;

    @GetMapping()
    ResponseEntity<?> obtenerTodos(){
        try{
            return ResponseEntity.ok(userService.obtenerTodos());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/provincia/{id}")
    ResponseEntity<?> obtenerPorProvincia(@PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.obtenerPorProvincia(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/localidad/{id}")
    ResponseEntity<?> obtenerPorLocalidad(@PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.obtenerPorLocalidad(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{idUser}")
    ResponseEntity<?> obtenerPorId(@PathVariable Long idUser){
        try{
            return ResponseEntity.ok(userService.obtenerPorId(idUser));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestParam String razonSocial,
                                        @RequestParam String telefono,
                                        @RequestParam Long idProvincia,
                                        @RequestParam Long idLocalidad,
                                        @RequestParam Long idPlan,
                                        @RequestParam String direccion,
                                        @RequestParam(value = "imagenPerfil", required = false) MultipartFile imagenPerfil){
        try{
            UsuarioRequestDTO dto = new UsuarioRequestDTO(razonSocial, telefono, idProvincia, idLocalidad, idPlan, direccion, imagenPerfil);
            UsuarioResponseDTO usuarioResponseDTO = userService.actualizarPorId(id, dto);
            return ResponseEntity.ok(usuarioResponseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
