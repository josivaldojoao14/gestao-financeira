package com.cdg.cadernog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.models.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Long>{
    Optional<RoleModel> findByName(Cargos cargo);
}
