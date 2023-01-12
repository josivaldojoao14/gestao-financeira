package com.cdg.cadernog.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.dtos.UserDto;
import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.models.RoleModel;
import com.cdg.cadernog.models.UserModel;
import com.cdg.cadernog.repositories.RoleRepository;
import com.cdg.cadernog.repositories.UserRepository;
import com.cdg.cadernog.services.exceptions.ObjectNotFoundException;
import com.cdg.cadernog.services.exceptions.UsernameAlreadyTakenException;
import com.cdg.cadernog.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void canDeleteUserById() {
        //given
        UserDto user = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        given(userRepository.findById(user.getId())).willReturn(Optional.of(new UserModel(user)));

        // when
        userService.deleteById(user.getId());

        // then
        verify(userRepository, times(1)).delete(new UserModel(user));
    }

    @Test
    void verifyIfUserExistsByUsername() {
        //given
        UserModel user = UserModel.builder()
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        given(userRepository.existsByUsername(user.getUsername())).willReturn(true);

        // when
        userService.existsByUsername(user.getUsername());

        // then
        verify(userRepository, times(1)).existsByUsername(user.getUsername());
    }

    @Test
    void canFindAllUsers() {
        // when
        userService.findAll();
        // then
        verify(userRepository).findAll();
    }

    @Test
    void canFindUserById() {
        //given
        UserDto userDto = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();
        given(userRepository.findById(userDto.getId())).willReturn(Optional.of(new UserModel(userDto)));

        // when
        userService.findById(userDto.getId());

        // then
        verify(userRepository, times(1)).findById(userDto.getId());
    }

    @Test
    void willThrowWhenUserIdIsNotFound() {
        //given
        UserDto userDto = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();
        // when
        // then
        assertThatThrownBy(() ->  userService.findById(userDto.getId()))
            .isInstanceOf(ObjectNotFoundException.class)
            .hasMessageContaining("Usuário nao encontrado");
    }

    @Test
    void canRevokeRoleFromUser() {
        //given
        RoleDto newRole = new RoleDto(null, Cargos.ROLE_USER.name());
        UserDto userDto = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(Arrays.asList(newRole))
            .build();

        given(userRepository.findByUsername(userDto.getUsername())).willReturn(Optional.of(new UserModel(userDto)));
        given(roleRepository.findByName(Cargos.valueOf(newRole.getName()))).willReturn(Optional.of(new RoleModel(newRole)));

        // when
        userService.revokeRoleFromUser(userDto.getRoles(), userDto.getUsername());

        // then
        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        UserModel capturedUser = userCaptor.getValue();

        assertThat(capturedUser.getRoles()).isEmpty();
    }

    @Test
    void canSaveUser() {
        // given
        UserDto user = UserDto.builder()
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();
        RoleModel newRole = new RoleModel(null, Cargos.ROLE_USER);

        // when
        when(roleRepository.findByName(Cargos.valueOf(newRole.getName()))).thenReturn(Optional.of(newRole));
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(new UserModel(user)));
        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(new UserModel(user));
        userService.save(user);

        // then
        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepository, times(2)).save(userCaptor.capture());
        UserModel capturedUser = userCaptor.getValue();

        assertThat(user.getUsername().equals(capturedUser.getUsername()));
    }

    @Test
    void willThrowWhenUsernameIsTaken() {
        // given
        UserDto user = UserDto.builder()
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();
        given(userRepository.existsByUsername(user.getUsername())).willReturn(true);

        // when
        // then
       assertThatThrownBy(() ->  userService.save(user))
           .isInstanceOf(UsernameAlreadyTakenException.class)
           .hasMessageContaining("Esse usuário já existe");
    }

    @Test
    void canUpdateUser() {
        // given
        UserDto user = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        UserDto userUpdated = UserDto.builder()
            .fullName("Usuario2")
            .phone("955555555")
            .username("usuario555")
            .password("senha333")
            .roles(new ArrayList<>())
            .build();
        given(userRepository.findById(user.getId())).willReturn(Optional.of(new UserModel(user)));

        // when
        when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(new UserModel(userUpdated));
        userService.update(user.getId(), userUpdated);

        // then
        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        UserModel capturedUser = userCaptor.getValue();

        verify(userRepository, times(1)).save(Mockito.any(UserModel.class));
        assertThat(user.getUsername()).isNotEqualTo(capturedUser.getUsername());
    }

    @Test
    void loadUserByUsername(){
        //given
        UserDto userDto = UserDto.builder()
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();
        given(userRepository.findByUsername(userDto.getUsername())).willReturn(Optional.of(new UserModel(userDto)));

        // when
        UserDetails user = userService.loadUserByUsername(userDto.getUsername());

        // then
        assertThat(user).isNotNull();
        assertThat(user.getUsername().equals(userDto.getUsername()));
    }
}
