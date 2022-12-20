package com.cdg.cadernog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdg.cadernog.models.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    
}
