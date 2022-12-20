package com.cdg.cadernog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdg.cadernog.models.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query("SELECT SUM(d.value) FROM Despesa d WHERE month(d.created_at) = ?1 and year(d.created_at) = ?2")
    float sumByPeriod(int month, int year);
}
