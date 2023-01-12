package com.cdg.cadernog.repositories.categorias;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.models.categorias.CategoriaDespesaModel;

@DataJpaTest
public class CategoriaDespesaRepositoryTest {

    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @AfterEach
    void tearDown(){
        categoriaDespesaRepository.deleteAll();
    }

    @Test
    void itShould_GetOneCategoriaDeDespesa_findByName() {
        // given
        CategoriaDespesaModel newCat = new CategoriaDespesaModel(null, CategoriasDespesa.FATURA);
        categoriaDespesaRepository.save(newCat);

        // when
        CategoriaDespesaModel catFound = categoriaDespesaRepository
            .findByName(CategoriasDespesa.valueOf(newCat.getName())).get();

        // then
        assertThat(newCat).isEqualTo(catFound);
    }

}
