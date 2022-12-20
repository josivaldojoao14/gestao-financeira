package com.cdg.cadernog.services.interfaces;

import java.util.List;

import com.cdg.cadernog.dtos.ReceitaDto;

public interface ReceitaService {
    List<ReceitaDto> findAll();

    ReceitaDto findById(long id);

    ReceitaDto save(ReceitaDto despesaDto);

    ReceitaDto update(long id, ReceitaDto despesaDto);

    void deleteById(long id);
}
