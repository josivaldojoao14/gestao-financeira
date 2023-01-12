package com.cdg.cadernog.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

import com.cdg.cadernog.dtos.DespesaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;
import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.DespesaModel;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.categorias.CategoriaDespesaModel;
import com.cdg.cadernog.repositories.DespesaRepository;
import com.cdg.cadernog.repositories.FormaDePagamentoRepository;
import com.cdg.cadernog.repositories.categorias.CategoriaDespesaRepository;
import com.cdg.cadernog.services.impl.DespesaServiceImpl;

@ExtendWith(MockitoExtension.class)
class DespesaServiceTest {
    
    @Mock
    private DespesaRepository despesaRepository;

    @Mock
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @Mock
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @InjectMocks
    private DespesaServiceImpl despesaService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        despesaService = new DespesaServiceImpl(despesaRepository, categoriaDespesaRepository, formaDePagamentoRepository);
    }

    @Test
    void canfindAllDespesas() {
        // when
        despesaService.findAll();
        // then
        verify(despesaRepository).findAll();
    }

    @Test
    void canFindDespesaById() {
        // given
        DespesaDto despesaDto = DespesaDto.builder()
            .id(1L)
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasDespesa.FATURA.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        given(despesaRepository.findById(despesaDto.getId())).willReturn(Optional.of(new DespesaModel(despesaDto)));

        // when
        despesaService.findById(despesaDto.getId());

        // then
        verify(despesaRepository, times(1)).findById(despesaDto.getId());
    }

    @Test
    void canSaveDespesa() {
        // given
        FormaDePagamentoModel pagamentoModel = new FormaDePagamentoModel(1L, FormasDePagamento.DINHEIRO);
        CategoriaDespesaModel categoriaModel = new CategoriaDespesaModel(1L, CategoriasDespesa.FATURA);
        DespesaDto despesaDto = DespesaDto.builder()
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(categoriaModel.getName())
            .formaDePagamento(pagamentoModel.getName())
            .build();

        given(formaDePagamentoRepository.findByName(FormasDePagamento.valueOf(despesaDto.getFormaDePagamento())))
            .willReturn(Optional.of(pagamentoModel));
        given(categoriaDespesaRepository.findByName(CategoriasDespesa.valueOf(despesaDto.getCategoriaDeDespesa())))
            .willReturn(Optional.of(categoriaModel));

        // when
        when(despesaRepository.save(Mockito.any(DespesaModel.class))).thenReturn(new DespesaModel(despesaDto));
        despesaService.save(despesaDto);

        // then
        ArgumentCaptor<DespesaModel> despCaptor = ArgumentCaptor.forClass(DespesaModel.class);
        verify(despesaRepository, times(1)).save(despCaptor.capture());
        DespesaModel capturedDesp = despCaptor.getValue();

        assertThat(despesaDto.getTitle().equals(capturedDesp.getTitle()));
    }

    @Test
    void canUpdateDespesa() {
        // given
        FormaDePagamentoModel pagamentoModel = new FormaDePagamentoModel(1L, FormasDePagamento.DEBITO);
        CategoriaDespesaModel categoriaModel = new CategoriaDespesaModel(1L, CategoriasDespesa.ALIMENTO);
        DespesaDto despesaDto = DespesaDto.builder()
            .id(1L)
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(categoriaModel.getName())
            .formaDePagamento(pagamentoModel.getName())
            .build();

        DespesaDto despesaUpdated = DespesaDto.builder()
            .title("Despesa atualizada")
            .description("Despesa atualizada")
            .value(400)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasDespesa.AGUA_LUZ_GAS.name())
            .formaDePagamento(FormasDePagamento.PIX.name())
            .build();

        given(formaDePagamentoRepository.findByName(FormasDePagamento.valueOf(despesaUpdated.getFormaDePagamento())))
            .willReturn(Optional.of(pagamentoModel));
        given(categoriaDespesaRepository.findByName(CategoriasDespesa.valueOf(despesaUpdated.getCategoriaDeDespesa())))
            .willReturn(Optional.of(categoriaModel));
        given(despesaRepository.findById(despesaDto.getId())).willReturn(Optional.of(new DespesaModel(despesaDto)));

        // when
        when(despesaRepository.save(Mockito.any(DespesaModel.class))).thenReturn(new DespesaModel(despesaUpdated));
        despesaService.update(despesaDto.getId(), despesaUpdated);

        // then
        ArgumentCaptor<DespesaModel> despCaptor = ArgumentCaptor.forClass(DespesaModel.class);
        verify(despesaRepository, times(1)).save(despCaptor.capture());
        DespesaModel capturedDesp = despCaptor.getValue();

        verify(despesaRepository, times(1)).save(Mockito.any(DespesaModel.class));
        assertThat(despesaDto.getTitle()).isNotEqualTo(capturedDesp.getTitle());
    }

    @Test
    void canDeleteDespesa() {
        //given
        DespesaDto despesaDto = DespesaDto.builder()
            .id(1L)
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasDespesa.FATURA.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        given(despesaRepository.findById(despesaDto.getId())).willReturn(Optional.of(new DespesaModel(despesaDto)));

        // when
        despesaService.delete(despesaDto);

        // then
        verify(despesaRepository, times(1)).delete(new DespesaModel(despesaDto));
    }

    @Test
    void canGetDespesaMonthlyExpense() {
        //given
        DespesaDto despesaDto = DespesaDto.builder()
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasDespesa.FATURA.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        // when
        when(despesaRepository.monthlyTotal(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()))
            .thenReturn(despesaDto.getValue());

        SituacaoMensalDto situacao = despesaService.getMonthlyExpense(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());

        // then
        verify(despesaRepository, times(1))
            .monthlyTotal(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());
        assertThat(despesaDto.getValue()).isEqualTo(situacao.getTotalSpent());
    }

    @Test
    void canGetDespesaAnnualExpense() {
        //given
        DespesaDto despesaDto = DespesaDto.builder()
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasDespesa.FATURA.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        // when
        when(despesaRepository.annualTotal(LocalDateTime.now().getYear())).thenReturn(despesaDto.getValue());
        SituacaoMensalDto situacao = despesaService.getAnnualExpense(LocalDateTime.now().getYear());

        // then
        verify(despesaRepository, times(1)).annualTotal(LocalDateTime.now().getYear());
        assertThat(despesaDto.getValue()).isEqualTo(situacao.getTotalSpent());
    }

    @Test
    void canGetDespesaSummaryOfPeriod() {
        //given
        DespesaDto despesaDto = DespesaDto.builder()
            .title("Despesa")
            .description("Despesa qualquer")
            .value(100)
            .created_at(Instant.now().toString())
            .categoriaDeDespesa(CategoriasDespesa.FATURA.name())
            .formaDePagamento(FormasDePagamento.DINHEIRO.name())
            .build();

        List<DespesaModel> despesas = new ArrayList<>();
        despesas.add(new DespesaModel(despesaDto));

        // when
        when(despesaRepository.findAllByPeriod(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()))
            .thenReturn(despesas);
        List<SituacaoMensalDto> situacao = despesaService
            .getSummaryOfPeriod(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());

        // then
        verify(despesaRepository, times(1))
            .findAllByPeriod(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());
        assertThat(situacao.size()).isEqualTo(1);
    }
}