package com.cdg.cadernog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdg.cadernog.models.DespesaModel;

public interface DespesaRepository extends JpaRepository<DespesaModel, Long> {

    @Override
    @Query("SELECT d from DespesaModel d WHERE d.user.username = ?#{principal.username}")
    List<DespesaModel> findAll();

    @Override
    @Query("SELECT d from DespesaModel d WHERE d.id = ?1 AND d.user.username = ?#{principal.username}")
    Optional<DespesaModel> findById(Long id);

    @Query("SELECT SUM(d.value) FROM DespesaModel d WHERE d.user.username = ?#{principal.username} AND year(d.created_at) = ?1 AND month(d.created_at) = ?2")
    float monthlyTotal(int year, int month);

    @Query("SELECT SUM(d.value) FROM DespesaModel d WHERE d.user.username = ?#{principal.username} AND year(d.created_at) = ?1")
    float annualTotal(int year);

    @Query("SELECT d FROM DespesaModel d WHERE d.user.username = ?#{principal.username} AND year(d.created_at) = ?1 AND month(d.created_at) = ?2")
    List<DespesaModel> findAllByPeriod(int year, int month);

}
