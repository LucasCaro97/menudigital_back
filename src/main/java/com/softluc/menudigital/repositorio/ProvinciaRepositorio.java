package com.softluc.menudigital.repositorio;

import com.softluc.menudigital.modelo.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepositorio extends JpaRepository<Provincia, Long> {

}
