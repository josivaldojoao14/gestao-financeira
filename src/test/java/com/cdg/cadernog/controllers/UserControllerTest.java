package com.cdg.cadernog.controllers;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.dtos.UserDto;
import com.cdg.cadernog.dtos.auth.UserLoginDto;
import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.form.AddRoleToUserForm;
import com.cdg.cadernog.form.RolesToRevokeForm;
import com.cdg.cadernog.security.JWTGenerator;
import com.cdg.cadernog.services.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTGenerator jwtGenerator;

    @Test
    void canGetAllUsers() throws Exception{
        ResultActions response = mockMvc.perform(get("/caderno/users")
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetUserById() throws Exception{
        UserDto user = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        ResultActions response = mockMvc.perform(get("/caderno/user/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canUpdateUser() throws Exception{
        UserDto user = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        ResultActions response = mockMvc.perform(put("/caderno/user/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void canDeleteUser() throws Exception{
        UserDto user = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        mockMvc.perform(delete("/caderno/user/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void canFindUserByUsername() throws Exception {
        UserDto user = UserDto.builder()
            .id(1L)
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        mockMvc.perform(get("/caderno/user/findByUsername")
            .param("username", user.getUsername())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canAddRoleToUser() throws Exception {
        UserDto user = UserDto.builder()
            .id(1L).fullName("Usuario").phone("954235452").username("usuario321")
            .password("senha321").roles(new ArrayList<>())
            .build();
        RoleDto role = RoleDto.builder().id(1L).name(Cargos.ROLE_ADMIN.name()).build();

        AddRoleToUserForm form = AddRoleToUserForm.builder()
            .userName(user.getUsername()).roleName(role.getName()).build();

        doNothing().when(userService).addRoleToUser(form.getUserName(), form.getRoleName());

        ResultActions response = mockMvc.perform(post("/caderno/user/addRoleToUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(form)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canRevokeRoleFromUser() throws Exception {
        RoleDto role = RoleDto.builder().id(1L).name(Cargos.ROLE_ADMIN.name()).build();
        UserDto user = UserDto.builder()
            .id(1L).fullName("Usuario").phone("954235452").username("usuario321")
            .password("senha321").roles(Arrays.asList(role))
            .build();
        RolesToRevokeForm form = RolesToRevokeForm.builder()
            .userName(user.getUsername()).roleName(Arrays.asList(role))
            .build();

        doNothing().when(userService).revokeRoleFromUser(form.getRoleName(), form.getUserName());

        ResultActions response = mockMvc.perform(post("/caderno/user/revokeRoleFromUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(form)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canSaveUser() throws Exception{
        UserDto user = UserDto.builder()
            .fullName("Usuario")
            .phone("954235452")
            .username("usuario321")
            .password("senha321")
            .roles(new ArrayList<>())
            .build();

        mockMvc.perform(post("/caderno/auth/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canLogin() throws Exception{
        UserLoginDto userLogin = UserLoginDto.builder()
            .username("usuario321")
            .password("senha321")
            .build();

        mockMvc.perform(post("/caderno/auth/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userLogin)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
