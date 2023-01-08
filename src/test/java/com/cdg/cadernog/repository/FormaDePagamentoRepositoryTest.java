package com.cdg.cadernog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.repositories.FormaDePagamentoRepository;

@DataJpaTest
public class FormaDePagamentoRepositoryTest {

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @AfterEach
    void tearDown() {
        formaDePagamentoRepository.deleteAll();
    }

    @Test
    void itShould_GetOneCategoriaDeDespesa_findByName() {
        // given
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        formaDePagamentoRepository.save(newPag);

        // when
        FormaDePagamentoModel pagFound = formaDePagamentoRepository
            .findByName(FormasDePagamento.valueOf(newPag.getName())).get();

        // then
        assertThat(newPag).isEqualTo(pagFound);
    }

}
