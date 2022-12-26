package com.cdg.cadernog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdg.cadernog.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
    Optional<UserModel> findByUsername(String username);
}
