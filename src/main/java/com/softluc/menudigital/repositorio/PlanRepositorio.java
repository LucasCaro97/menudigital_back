package com.softluc.menudigital.repositorio;

import com.softluc.menudigital.modelo.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepositorio extends JpaRepository<Plan, Long> {

}
