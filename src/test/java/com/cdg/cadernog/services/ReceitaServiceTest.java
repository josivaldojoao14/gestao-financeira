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

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;
import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.ReceitaModel;
import com.cdg.cadernog.models.categorias.CategoriaReceitaModel;
import com.cdg.cadernog.repositories.FormaDePagamentoRepository;
import com.cdg.cadernog.repositories.ReceitaRepository;
import com.cdg.cadernog.repositories.categorias.CategoriaReceitaRepository;
import com.cdg.cadernog.services.impl.ReceitaServiceImpl;

@ExtendWith(MockitoExtension.class)
class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private CategoriaReceitaRepository categoriaReceitaRepository;

    @Mock
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @InjectMocks
    private ReceitaServiceImpl receitaService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        receitaService = new ReceitaServiceImpl(receitaRepository, categoriaReceitaRepository, formaDePagamentoRepository);
    }

    @Test
    void canfindAllReceitas() {
        // when
        receitaService.findAll();
        // then
        verify(receitaRepository).findAll();
    }

    @Test
    void canFindReceitaById() {
        // given
        ReceitaDto receitaDto = ReceitaDto.builder()
                .id(1L)
                .title("Receita")
                .description("Receita qualquer")
                .value(100)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(CategoriasReceita.ALUGUEL.name())
                .formaDePagamento(FormasDePagamento.DINHEIRO.name())
                .build();

        given(receitaRepository.findById(receitaDto.getId())).willReturn(Optional.of(new ReceitaModel(receitaDto)));

        // when
        receitaService.findById(receitaDto.getId());

        // then
        verify(receitaRepository, times(1)).findById(receitaDto.getId());
    }

    @Test
    void canSaveReceita() {
        // given
        FormaDePagamentoModel pagamentoModel = new FormaDePagamentoModel(1L, FormasDePagamento.DINHEIRO);
        CategoriaReceitaModel categoriaModel = new CategoriaReceitaModel(1L, CategoriasReceita.SALARIO);
        ReceitaDto receitaDto = ReceitaDto.builder()
                .title("Receita")
                .description("Receita qualquer")
                .value(100)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(categoriaModel.getName())
                .formaDePagamento(pagamentoModel.getName())
                .build();

        given(formaDePagamentoRepository.findByName(FormasDePagamento.valueOf(receitaDto.getFormaDePagamento())))
                .willReturn(Optional.of(pagamentoModel));
        given(categoriaReceitaRepository.findByName(CategoriasReceita.valueOf(receitaDto.getCategoriaDeReceita())))
                .willReturn(Optional.of(categoriaModel));

        // when
        when(receitaRepository.save(Mockito.any(ReceitaModel.class))).thenReturn(new ReceitaModel(receitaDto));
        receitaService.save(receitaDto);

        // then
        ArgumentCaptor<ReceitaModel> despCaptor = ArgumentCaptor.forClass(ReceitaModel.class);
        verify(receitaRepository, times(1)).save(despCaptor.capture());
        ReceitaModel capturedDesp = despCaptor.getValue();

        assertThat(receitaDto.getTitle().equals(capturedDesp.getTitle()));
    }

    @Test
    void canUpdateReceita() {
        // given
        FormaDePagamentoModel pagamentoModel = new FormaDePagamentoModel(1L, FormasDePagamento.DEBITO);
        CategoriaReceitaModel categoriaModel = new CategoriaReceitaModel(1L, CategoriasReceita.SALARIO);
        ReceitaDto receitaDto = ReceitaDto.builder()
                .id(1L)
                .title("Receita")
                .description("Receita qualquer")
                .value(100)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(categoriaModel.getName())
                .formaDePagamento(pagamentoModel.getName())
                .build();

        ReceitaDto receitaUpdated = ReceitaDto.builder()
                .title("Receita atualizada")
                .description("Receita atualizada")
                .value(400)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(CategoriasReceita.VENDA.name())
                .formaDePagamento(FormasDePagamento.PIX.name())
                .build();

        given(formaDePagamentoRepository.findByName(FormasDePagamento.valueOf(receitaUpdated.getFormaDePagamento())))
                .willReturn(Optional.of(pagamentoModel));
        given(categoriaReceitaRepository.findByName(CategoriasReceita.valueOf(receitaUpdated.getCategoriaDeReceita())))
                .willReturn(Optional.of(categoriaModel));
        given(receitaRepository.findById(receitaDto.getId())).willReturn(Optional.of(new ReceitaModel(receitaDto)));

        // when
        when(receitaRepository.save(Mockito.any(ReceitaModel.class))).thenReturn(new ReceitaModel(receitaUpdated));
        receitaService.update(receitaDto.getId(), receitaUpdated);

        // then
        ArgumentCaptor<ReceitaModel> despCaptor = ArgumentCaptor.forClass(ReceitaModel.class);
        verify(receitaRepository, times(1)).save(despCaptor.capture());
        ReceitaModel capturedDesp = despCaptor.getValue();

        verify(receitaRepository, times(1)).save(Mockito.any(ReceitaModel.class));
        assertThat(receitaDto.getTitle()).isNotEqualTo(capturedDesp.getTitle());
    }

    @Test
    void canDeleteReceita() {
        //given
        ReceitaDto receitaDto = ReceitaDto.builder()
                .id(1L)
                .title("Receita")
                .description("Receita qualquer")
                .value(100)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(CategoriasReceita.PRESTACAO_DE_SERVICO.name())
                .formaDePagamento(FormasDePagamento.DINHEIRO.name())
                .build();

        given(receitaRepository.findById(receitaDto.getId())).willReturn(Optional.of(new ReceitaModel(receitaDto)));

        // when
        receitaService.delete(receitaDto);

        // then
        verify(receitaRepository, times(1)).delete(new ReceitaModel(receitaDto));
    }

    @Test
    void canGetReceitaMonthlyExpense() {
            // given
            ReceitaDto receitaDto = ReceitaDto.builder()
                .title("Receita")
                .description("Receita qualquer")
                .value(100)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(CategoriasReceita.PRESTACAO_DE_SERVICO.name())
                .formaDePagamento(FormasDePagamento.DINHEIRO.name())
                .build();

            // when
            when(receitaRepository.monthlyTotal(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()))
                .thenReturn(receitaDto.getValue());

            SituacaoMensalDto situacao = receitaService.getMonthlyExpense(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue());

            // then
            verify(receitaRepository, times(1))
                .monthlyTotal(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());
            assertThat(receitaDto.getValue()).isEqualTo(situacao.getTotalSpent());
    }

    @Test
    void canGetReceitaAnnualExpense() {
            // given
            ReceitaDto receitaDto = ReceitaDto.builder()
                .title("Receita")
                .description("Receita qualquer")
                .value(100)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(CategoriasReceita.SALARIO.name())
                .formaDePagamento(FormasDePagamento.DINHEIRO.name())
                .build();

            // when
            when(receitaRepository.annualTotal(LocalDateTime.now().getYear())).thenReturn(receitaDto.getValue());
            SituacaoMensalDto situacao = receitaService.getAnnualExpense(LocalDateTime.now().getYear());

            // then
            verify(receitaRepository, times(1)).annualTotal(LocalDateTime.now().getYear());
            assertThat(receitaDto.getValue()).isEqualTo(situacao.getTotalSpent());
    }

    @Test
    void canGetReceitaSummaryOfPeriod() {
            // given
            ReceitaDto receitaDto = ReceitaDto.builder()
                .title("Receita")
                .description("Receita qualquer")
                .value(100)
                .created_at(Instant.now().toString())
                .categoriaDeReceita(CategoriasReceita.RENDIMENTO.name())
                .formaDePagamento(FormasDePagamento.DINHEIRO.name())
                .build();

            List<ReceitaModel> receitas = new ArrayList<>();
            receitas.add(new ReceitaModel(receitaDto));

            // when
            when(receitaRepository.findAllByPeriod(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()))
                .thenReturn(receitas);
            List<SituacaoMensalDto> situacao = receitaService
                .getSummaryOfPeriod(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());

            // then
            verify(receitaRepository, times(1))
                .findAllByPeriod(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue());
            assertThat(situacao.size()).isEqualTo(1);
    }
}