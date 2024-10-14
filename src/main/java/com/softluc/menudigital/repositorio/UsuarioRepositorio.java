package com.softluc.menudigital.repositorio;

import com.softluc.menudigital.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByNombre(String nombre);

}
