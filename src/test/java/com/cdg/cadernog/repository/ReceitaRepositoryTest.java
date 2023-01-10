package com.cdg.cadernog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.ReceitaModel;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.UserModel;
import com.cdg.cadernog.models.categorias.CategoriaReceitaModel;
import com.cdg.cadernog.repositories.ReceitaRepository;
import com.cdg.cadernog.repositories.FormaDePagamentoRepository;
import com.cdg.cadernog.repositories.UserRepository;
import com.cdg.cadernog.repositories.categorias.CategoriaReceitaRepository;

@DataJpaTest
public class ReceitaRepositoryTest {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoriaReceitaRepository categoriaReceitaRepository;

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @AfterEach
    void tearDown() {
        receitaRepository.deleteAll();
    }

    @Test
    void itShould_getThe_monthlyTotal() {
        // given
        CategoriaReceitaModel newCat = new CategoriaReceitaModel(null, CategoriasReceita.SALARIO);
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        UserModel newUser = new UserModel(null, "Usuario", "954235452", "usuario321", "senha321", new ArrayList<>());
        categoriaReceitaRepository.save(newCat);
        formaDePagamentoRepository.save(newPag);
        userRepository.save(newUser);

        ReceitaModel newReceita = new ReceitaModel(
            null, "title",
            "description", Instant.now(),
            null, 50, newCat,
            newPag, newUser);
        receitaRepository.save(newReceita);

        // when
        float monthlyTotal = receitaRepository.monthlyTotal(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        // then
        assertThat(newReceita.getValue()).isEqualTo(monthlyTotal);
    }

    @Test
    void itShould_getThe_annualTotal() {
        // given
        CategoriaReceitaModel newCat = new CategoriaReceitaModel(null, CategoriasReceita.ALUGUEL);
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        UserModel newUser = new UserModel(null, "Usuario", "954235452", "usuario321", "senha321", new ArrayList<>());
        categoriaReceitaRepository.save(newCat);
        formaDePagamentoRepository.save(newPag);
        userRepository.save(newUser);

        ReceitaModel newReceita = new ReceitaModel(
            null, "title",
            "description", Instant.now(),
            null, 50, newCat,
            newPag, newUser);
        receitaRepository.save(newReceita);

        // when
        float annualTotal = receitaRepository.annualTotal(LocalDate.now().getYear());

        // then
        assertThat(newReceita.getValue()).isEqualTo(annualTotal);
    }

    @Test
    void itShould_findAllByPeriod() {
        // given
        CategoriaReceitaModel newCat = new CategoriaReceitaModel(null, CategoriasReceita.VENDA);
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        UserModel newUser = new UserModel(null, "Usuario", "954235452", "usuario321", "senha321", new ArrayList<>());
        categoriaReceitaRepository.save(newCat);
        formaDePagamentoRepository.save(newPag);
        userRepository.save(newUser);

        ReceitaModel newReceita = new ReceitaModel(
            null, "title",
            "description", Instant.now(),
            null, 50, newCat,
            newPag, newUser);
        receitaRepository.save(newReceita);

        // when
        List<ReceitaModel> receitas = receitaRepository.findAllByPeriod(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        // then
        assertThat(receitas).hasSizeGreaterThanOrEqualTo(1);
    }

}
