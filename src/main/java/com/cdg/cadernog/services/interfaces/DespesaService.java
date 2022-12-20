package com.cdg.cadernog.services.interfaces;

import java.util.List;

import com.cdg.cadernog.dtos.DespesaDto;

public interface DespesaService {
    List<DespesaDto> findAll();
    
    DespesaDto findById(long id);
    
    DespesaDto save(DespesaDto despesaDto);
    
    DespesaDto update(long id, DespesaDto despesaDto);

    void deleteById(long id);
}
