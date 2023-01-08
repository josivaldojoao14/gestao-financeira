package com.cdg.cadernog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.DespesaModel;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.UserModel;
import com.cdg.cadernog.models.categorias.CategoriaDespesaModel;
import com.cdg.cadernog.repositories.DespesaRepository;

@DataJpaTest
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository despesaRepository;

    @AfterEach
    void tearDown() {
        despesaRepository.deleteAll();
    }

    @Test
    void itShould_findAll() {
        // given
        CategoriaDespesaModel newCat = new CategoriaDespesaModel(null, CategoriasDespesa.FATURA);
        FormaDePagamentoModel newPag = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        UserModel newUser = new UserModel(null, "Usuario", "954235452", "usuario321", "senha321", new ArrayList<>());

        DespesaModel newDespesa = new DespesaModel(
            null, "title",
            "description", Instant.now(),
            null, 50, newCat,
            newPag, newUser);
        despesaRepository.save(newDespesa);

        // when
        List<DespesaModel> listDespesas = despesaRepository.findAll();

        // then
        assertThat(listDespesas).isNotEmpty();
    } 
}
