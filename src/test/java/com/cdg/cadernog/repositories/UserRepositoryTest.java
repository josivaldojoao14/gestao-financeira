package com.cdg.cadernog.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cdg.cadernog.models.UserModel;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

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

    @Test
    void itShould_CheckIfUser_existsByUsername() {
        // given
        UserModel newUser = new UserModel(
            null, "Usuario",
            "954235452", "usuario321",
            "senha321", new ArrayList<>());

        userRepository.save(newUser);

        // when
        boolean result = userRepository.existsByUsername(newUser.getUsername());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void itShould_CheckIfUserDoesNot_existsByUsername() {
        // given
        String username = "username";

        // when
        boolean result = userRepository.existsByUsername(username);

        // then
        assertThat(result).isFalse();
    }
}
