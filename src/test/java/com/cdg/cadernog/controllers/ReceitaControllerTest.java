package com.cdg.cadernog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.Instant;
import java.time.LocalDateTime;

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

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.services.impl.ReceitaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ReceitaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReceitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReceitaServiceImpl receitaService;

    @Test
    void canGetAllReceitas() throws Exception {
        ResultActions response = mockMvc.perform(get("/caderno/receitas")
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetReceitaById() throws Exception {
        ReceitaDto receita = ReceitaDto.builder()
            .id(1L)
            .title("Receita")
            .description("Receita qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeReceita(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        ResultActions response = mockMvc.perform(get("/caderno/receita/" + receita.getId())
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canSaveReceita() throws Exception {
        ReceitaDto receita = ReceitaDto.builder()
            .title("Receita")
            .description("Receita qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeReceita(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        mockMvc.perform(post("/caderno/receita")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(receita)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canUpdateReceita() throws Exception {
        ReceitaDto receita = ReceitaDto.builder()
            .id(1L)
            .title("Receita")
            .description("Receita qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeReceita(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        ResultActions response = mockMvc.perform(put("/caderno/receita/" + receita.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(receita)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canDeleteReceita() throws Exception {
        ReceitaDto receita = ReceitaDto.builder()
            .id(1L)
            .title("Receita")
            .description("Receita qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeReceita(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        mockMvc.perform(delete("/caderno/receita/" + receita.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetReceitaMonthlyExpense() throws Exception {
        mockMvc.perform(get("/caderno/receita/monthlyExpense/" + LocalDateTime.now().getYear() + "/" + LocalDateTime.now().getMonthValue())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
}

    @Test
    void canGetReceitaAnnualExpense() throws Exception {
        mockMvc.perform(get("/caderno/receita/annualExpense/" + LocalDateTime.now().getYear())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetReceitaSummaryOfPeriod() throws Exception {
        ResultActions response = mockMvc.perform(get("/caderno/receita/summary/" + +LocalDateTime.now().getYear() + "/" + LocalDateTime.now().getMonthValue())
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
