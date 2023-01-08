package com.cdg.cadernog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.models.UserModel;
import com.cdg.cadernog.repositories.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void itShould_GetOneUser_findByUsername() {
        // given
        UserModel newUser = new UserModel(
            null, "Usuario", 
            "954235452", "usuario321", 
            "senha321", new ArrayList<>()
        );

        userRepository.save(newUser);

        // when
        UserModel userFound = userRepository.findByUsername(newUser.getUsername()).get();

        // then
        assertThat(newUser).isEqualTo(userFound);
    }
}
