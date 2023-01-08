package com.cdg.cadernog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.models.RoleModel;
import com.cdg.cadernog.repositories.RoleRepository;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void itShould_GetOneCategoriaDeReceita_findByName() {
        // given
        RoleModel newRole = new RoleModel(null, Cargos.ROLE_ADMIN);
        roleRepository.save(newRole);

        // when
        RoleModel roleFound = roleRepository
            .findByName(Cargos.valueOf(newRole.getName())).get();

        // then
        assertThat(newRole).isEqualTo(roleFound);
    }

}
