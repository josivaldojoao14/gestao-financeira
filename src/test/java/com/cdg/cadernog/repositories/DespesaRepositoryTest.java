package com.cdg.cadernog.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.DespesaModel;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.categorias.CategoriaDespesaModel;
import com.cdg.cadernog.repositories.categorias.CategoriaDespesaRepository;

@DataJpaTest
public class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @AfterEach
    void tearDown() {
        despesaRepository.deleteAll();
    }

    @Test
    void itShould_getThe_monthlyTotal() {
        // given
        CategoriaDespesaModel newCat = new CategoriaDespesaModel(null, CategoriasDespesa.FATURA);
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        categoriaDespesaRepository.save(newCat);
        formaDePagamentoRepository.save(newPag);

        DespesaModel newDespesa = new DespesaModel(
            null, "title",
            "description", Instant.now(),
            null, 50, newCat,
            newPag);
        despesaRepository.save(newDespesa);

        // when
        float monthlyTotal = despesaRepository.monthlyTotal(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        // then
        assertThat(newDespesa.getValue()).isEqualTo(monthlyTotal);
    }

    @Test
    void itShould_getThe_annualTotal() {
        // given
        CategoriaDespesaModel newCat = new CategoriaDespesaModel(null, CategoriasDespesa.FATURA);
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        categoriaDespesaRepository.save(newCat);
        formaDePagamentoRepository.save(newPag);

        DespesaModel newDespesa = new DespesaModel(
            null, "title",
            "description", Instant.now(),
            null, 50, newCat,
            newPag);
        despesaRepository.save(newDespesa);

        // when
        float annualTotal = despesaRepository.annualTotal(LocalDate.now().getYear());

        // then
        assertThat(newDespesa.getValue()).isEqualTo(annualTotal);
    }

    @Test
    void itShould_findAllByPeriod() {
        // given
        CategoriaDespesaModel newCat = new CategoriaDespesaModel(null, CategoriasDespesa.FATURA);
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        categoriaDespesaRepository.save(newCat);
        formaDePagamentoRepository.save(newPag);

        DespesaModel newDespesa = new DespesaModel(
            null, "title",
            "description", Instant.now(),
            null, 50, newCat,
            newPag);
        despesaRepository.save(newDespesa);

        // when
        List<DespesaModel> despesas = despesaRepository.findAllByPeriod(LocalDate.now().getYear(),
                LocalDate.now().getMonthValue());

        // then
        assertThat(despesas).hasSizeGreaterThanOrEqualTo(1);
    }

}
