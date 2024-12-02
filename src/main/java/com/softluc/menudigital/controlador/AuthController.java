package com.softluc.menudigital.controlador;

import com.softluc.menudigital.DTO.AuthCreateUserRequest;
import com.softluc.menudigital.DTO.AuthLoginRequst;
import com.softluc.menudigital.DTO.AuthResponse;
import com.softluc.menudigital.servicio.UserDetailServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequst userRequest){
        try{
            return new ResponseEntity<>(this.userDetailServiceImp.loginUser(userRequest), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){
        try{
            System.out.println(authCreateUser);
            return new ResponseEntity<>(this.userDetailServiceImp.createUser(authCreateUser), HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
