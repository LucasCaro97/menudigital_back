package com.softluc.menudigital.servicio;

import com.softluc.menudigital.DTO.AuthCreateUserRequest;
import com.softluc.menudigital.DTO.AuthLoginRequst;
import com.softluc.menudigital.DTO.AuthResponse;
import com.softluc.menudigital.jwt.JwtUtils;
import com.softluc.menudigital.modelo.Roles;
import com.softluc.menudigital.modelo.Usuario;
import com.softluc.menudigital.repositorio.RolesRepositorio;
import com.softluc.menudigital.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepositorio rolesRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findUsuarioByNombre(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        usuario.getRoles()
                .forEach( roles -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roles.getRoleEnum().name()))));

        usuario.getRoles().stream()
                .flatMap(roles -> roles.getPermisos().stream())
                .forEach(permisos -> authorityList.add(new SimpleGrantedAuthority(permisos.getNombre())));

        return new User(usuario.getNombre(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNoExpired(),
                usuario.isCredentialsNoExpired(),
                usuario.isAccountNoLocked(),
                authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequst authLoginRequst){
        String username = authLoginRequst.username();
        String password = authLoginRequst.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponse authResponse = new AuthResponse(username, "Usuario logueado correctamente", accessToken, true);
        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Usuario y/o contraseña invalidos");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Contraseña invalida");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }


    public AuthResponse createUser(AuthCreateUserRequest authCreateUser) {
        String username = authCreateUser.username();
        String password = authCreateUser.password();
        List<String> roleRequest = authCreateUser.roleRequest().roleListName();

        Set<Roles> rolesSet = rolesRepositorio.findRolesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());
        if(rolesSet.isEmpty()){
            throw new IllegalArgumentException("Los roles especificados no existen");
        }
        Usuario usuario = Usuario.builder()
                .nombre(username)
                .password(passwordEncoder.encode(password))
                .roles(rolesSet)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialsNoExpired(true)
                .build();

        Usuario usuarioCreado = usuarioRepositorio.save(usuario);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        usuarioCreado.getRoles().forEach(roles -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roles.getRoleEnum().name()))));
        usuarioCreado.getRoles().stream()
                .flatMap(roles -> roles.getPermisos().stream())
                .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getNombre())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioCreado.getNombre(), usuarioCreado.getPassword(), authorityList);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponse authResponse = new AuthResponse(usuarioCreado.getNombre(), "Usuario creado correctamente", accessToken, true);
        return authResponse;
    }
}
