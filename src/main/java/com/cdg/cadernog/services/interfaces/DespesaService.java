package com.cdg.cadernog.services.interfaces;

import java.util.List;

import com.cdg.cadernog.dtos.DespesaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;

public interface DespesaService {
    List<DespesaDto> findAll();
    
    DespesaDto findById(long id);
    
    DespesaDto save(DespesaDto despesaDto);
    
    DespesaDto update(long id, DespesaDto despesaDto);

    void deleteById(long id);

    SituacaoMensalDto getMonthlyExpense(int year, int month);

    SituacaoMensalDto getAnnualExpense(int year);

    List<SituacaoMensalDto> getSummaryOfPeriod(int year, int month);
}
