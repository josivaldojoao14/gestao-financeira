package com.cdg.cadernog.services.interfaces;

import java.util.List;

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;

public interface ReceitaService {
    List<ReceitaDto> findAll();

    ReceitaDto findById(long id);

    ReceitaDto save(ReceitaDto despesaDto);

    ReceitaDto update(long id, ReceitaDto despesaDto);

    void delete(ReceitaDto receitaDto);

    SituacaoMensalDto getMonthlyExpense(int year, int month);

    SituacaoMensalDto getAnnualExpense(int year);

    List<SituacaoMensalDto> getSummaryOfPeriod(int year, int month);
}
