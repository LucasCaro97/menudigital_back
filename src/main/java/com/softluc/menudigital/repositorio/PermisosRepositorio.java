package com.softluc.menudigital.repositorio;

import com.softluc.menudigital.modelo.Permisos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisosRepositorio extends JpaRepository<Permisos, Long> {
}