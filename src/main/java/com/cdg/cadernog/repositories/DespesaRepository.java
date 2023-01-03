package com.cdg.cadernog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdg.cadernog.models.DespesaModel;

public interface DespesaRepository extends JpaRepository<DespesaModel, Long> {
    @Query("SELECT SUM(r.value) FROM DespesaModel r WHERE year(r.created_at) = ?1 AND month(r.created_at) = ?2")
    float monthlyTotal(int year, int month);

    @Query("SELECT SUM(r.value) FROM DespesaModel r WHERE year(r.created_at) = ?1")
    float annualTotal(int year);

    @Query("SELECT r FROM DespesaModel r WHERE year(r.created_at) = ?1 AND month(r.created_at) = ?2")
    List<DespesaModel> findAllByPeriod(int year, int month);

}
