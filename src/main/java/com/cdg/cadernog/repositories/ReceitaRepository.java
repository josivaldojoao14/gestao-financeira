package com.cdg.cadernog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdg.cadernog.models.ReceitaModel;

public interface ReceitaRepository extends JpaRepository<ReceitaModel, Long> {
    // @Override
    // @Query("SELECT d from ReceitaModel d WHERE d.user.username = ?#{principal.username}")
    // List<ReceitaModel> findAll();

    // @Override
    // @Query("SELECT d from ReceitaModel d WHERE d.id = ?1 AND d.user.username = ?#{principal.username}")
    // Optional<ReceitaModel> findById(Long id);

    // @Query("SELECT SUM(d.value) FROM ReceitaModel d WHERE d.user.username = ?#{principal.username} AND year(d.created_at) = ?1 AND month(d.created_at) = ?2")
    // float monthlyTotal(int year, int month);

    // @Query("SELECT SUM(d.value) FROM ReceitaModel d WHERE d.user.username = ?#{principal.username} AND year(d.created_at) = ?1")
    // float annualTotal(int year);

    // @Query("SELECT d FROM ReceitaModel d WHERE d.user.username = ?#{principal.username} AND year(d.created_at) = ?1 AND month(d.created_at) = ?2")
    // List<ReceitaModel> findAllByPeriod(int year, int month);

    @Query("SELECT SUM(d.value) FROM ReceitaModel d WHERE year(d.created_at) = ?1 AND month(d.created_at) = ?2")
    float monthlyTotal(int year, int month);

    @Query("SELECT SUM(d.value) FROM ReceitaModel d WHERE year(d.created_at) = ?1")
    float annualTotal(int year);

    @Query("SELECT d FROM ReceitaModel d WHERE year(d.created_at) = ?1 AND month(d.created_at) = ?2")
    List<ReceitaModel> findAllByPeriod(int year, int month);
}
