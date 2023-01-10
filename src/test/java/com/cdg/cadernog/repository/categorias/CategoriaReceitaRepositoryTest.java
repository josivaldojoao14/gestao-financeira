package com.cdg.cadernog.repository.categorias;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.models.categorias.CategoriaReceitaModel;
import com.cdg.cadernog.repositories.categorias.CategoriaReceitaRepository;

@DataJpaTest
public class CategoriaReceitaRepositoryTest {

    @Autowired
    private CategoriaReceitaRepository categoriaReceitaRepository;

    @AfterEach
    void tearDown() {
        categoriaReceitaRepository.deleteAll();
    }

    @Test
    void itShould_GetOneCategoriaDeReceita_findByName() {
        // given
        CategoriaReceitaModel newCat = new CategoriaReceitaModel(null, CategoriasReceita.ALUGUEL);
        categoriaReceitaRepository.save(newCat);

        // when
        CategoriaReceitaModel catFound = categoriaReceitaRepository
            .findByName(CategoriasReceita.valueOf(newCat.getName())).get();

        // then
        assertThat(newCat).isEqualTo(catFound);
    }

}
