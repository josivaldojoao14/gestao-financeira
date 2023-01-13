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

import com.cdg.cadernog.dtos.DespesaDto;
import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.services.impl.DespesaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = DespesaController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DespesaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DespesaServiceImpl despesaService;

    @Test
    void canGetAllDespesas() throws Exception {
        ResultActions response = mockMvc.perform(get("/caderno/despesas")
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetDespesaById() throws Exception {
        DespesaDto despesa = DespesaDto.builder()
            .id(1L)
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        ResultActions response = mockMvc.perform(get("/caderno/despesa/" + despesa.getId())
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canSaveDespesa() throws Exception {
        DespesaDto despesa = DespesaDto.builder()
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        mockMvc.perform(post("/caderno/despesa")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(despesa)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canUpdateDespesa() throws Exception {
        DespesaDto despesa = DespesaDto.builder()
            .id(1L)
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        ResultActions response = mockMvc.perform(put("/caderno/despesa/" + despesa.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(despesa)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canDeleteDespesa() throws Exception {
        DespesaDto despesa = DespesaDto.builder()
            .id(1L)
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasReceita.RENDIMENTO.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        mockMvc.perform(delete("/caderno/despesa/" + despesa.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetDespesaMonthlyExpense() throws Exception {
        mockMvc.perform(get("/caderno/despesa/monthlyExpense/" + LocalDateTime.now().getYear() + "/" + LocalDateTime.now().getMonthValue())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetDespesaAnnualExpense() throws Exception {
        mockMvc.perform(get("/caderno/despesa/annualExpense/" + LocalDateTime.now().getYear())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void canGetDespesaSummaryOfPeriod() throws Exception {
        ResultActions response = mockMvc.perform(get("/caderno/despesa/summary/" + +LocalDateTime.now().getYear() + "/" + LocalDateTime.now().getMonthValue())
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
