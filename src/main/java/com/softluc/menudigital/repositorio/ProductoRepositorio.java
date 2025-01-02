package com.softluc.menudigital.repositorio;

import com.softluc.menudigital.modelo.Producto;
import com.softluc.menudigital.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {


    List<Producto> findByUsuario(Usuario User);
}
