package com.cdg.cadernog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdg.cadernog.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    @Query("SELECT SUM(r.value) FROM Despesa r WHERE year(r.created_at) = ?1 AND month(r.created_at) = ?2")
    float sumByPeriod(int year, int month);
}
