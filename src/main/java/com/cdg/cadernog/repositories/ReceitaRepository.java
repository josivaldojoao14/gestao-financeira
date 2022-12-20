package com.cdg.cadernog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdg.cadernog.models.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    @Query("SELECT SUM(d.value) FROM Receita d WHERE month(d.created_at) = ?1 and year(d.created_at) = ?2")
    float sumByPeriod(int month, int year);

}
