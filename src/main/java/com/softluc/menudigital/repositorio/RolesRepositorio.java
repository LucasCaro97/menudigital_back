package com.softluc.menudigital.repositorio;

import com.softluc.menudigital.modelo.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepositorio extends JpaRepository<Roles, Long> {

    List<Roles> findRolesByRoleEnumIn(List<String> roleNames);

}
