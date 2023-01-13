package com.cdg.cadernog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.services.impl.RoleServiceImpl;

@WebMvcTest(controllers = RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleServiceImpl roleService;

    @Test
    void canGetAllRoles() throws Exception {
        ResultActions response = mockMvc.perform(get("/caderno/roles")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetRoleById() throws Exception {
        RoleDto role = RoleDto.builder()
                .id(1L).name(Cargos.ROLE_SUPER_ADMIN.name())
                .build();

        ResultActions response = mockMvc.perform(get("/caderno/role/" + role.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
