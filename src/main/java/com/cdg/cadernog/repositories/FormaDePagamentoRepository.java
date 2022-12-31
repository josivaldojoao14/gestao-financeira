package com.cdg.cadernog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.FormaDePagamentoModel;

public interface FormaDePagamentoRepository extends JpaRepository<FormaDePagamentoModel, Long>{
    Optional<FormaDePagamentoModel> findByName(FormasDePagamento pag);
}
