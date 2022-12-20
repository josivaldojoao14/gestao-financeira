package com.cdg.cadernog.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdg.cadernog.dtos.DespesaDto;
import com.cdg.cadernog.models.Despesa;
import com.cdg.cadernog.repositories.DespesaRepository;
import com.cdg.cadernog.services.interfaces.DespesaService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class DespesaServiceImpl implements DespesaService {

    @Autowired
    private final DespesaRepository despesaRepository;

    @Override
    public List<DespesaDto> findAll() {
        List<Despesa> despesas = despesaRepository.findAll();
        List<DespesaDto> despesasDto = despesas.stream().map(x -> new DespesaDto(x)).collect(Collectors.toList());
        return despesasDto;
    }

    @Override
    public DespesaDto findById(long id) {
        Despesa despesa = despesaRepository.findById(id).get();
        return new DespesaDto(despesa);
    }

    @Override
    public DespesaDto save(DespesaDto despesaDto) {
        Despesa newDespesa = new Despesa();
        BeanUtils.copyProperties(despesaDto, newDespesa);
        newDespesa.setCreated_at(Instant.now());

        newDespesa = despesaRepository.save(newDespesa);
        return new DespesaDto(newDespesa);
    }

    @Override
    public DespesaDto update(long id, DespesaDto despesaDto) {
        Despesa despesa = despesaRepository.findById(id).get();
        Instant created_at = despesa.getCreated_at();

        BeanUtils.copyProperties(despesaDto, despesa);

        despesa.setId(id);
        despesa.setCreated_at(created_at);
        despesa.setUpdated_at(Instant.now());

        despesa = despesaRepository.save(despesa);
        return new DespesaDto(despesa);
    }

    @Override
    public void deleteById(long id) {
        Despesa despesa = despesaRepository.findById(id).get();
        despesaRepository.delete(despesa);
    }

}
