package com.softluc.menudigital.repositorio;

import com.softluc.menudigital.modelo.Localidad;
import com.softluc.menudigital.modelo.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalidadRepositorio extends JpaRepository<Localidad, Long> {

    List<Localidad> findByProvincia(Provincia obtenerPorId);
}

