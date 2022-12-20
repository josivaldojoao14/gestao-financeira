package com.cdg.cadernog.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.models.Receita;
import com.cdg.cadernog.repositories.ReceitaRepository;
import com.cdg.cadernog.services.interfaces.ReceitaService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ReceitaServiceImpl implements ReceitaService{

    @Autowired
    private final ReceitaRepository receitaRepository;

    @Override
    public List<ReceitaDto> findAll() {
        List<Receita> receitas = receitaRepository.findAll();
        List<ReceitaDto> receitasDto = receitas.stream().map(x -> new ReceitaDto(x)).collect(Collectors.toList());
        return receitasDto;
    }

    @Override
    public ReceitaDto findById(long id) {
        Receita receita = receitaRepository.findById(id).get();
        return new ReceitaDto(receita);
    }

    @Override
    public ReceitaDto save(ReceitaDto receitaDto) {
        Receita newReceita = new Receita();
        BeanUtils.copyProperties(receitaDto, newReceita);
        newReceita.setCategoria(receitaDto.getCategoriaDeReceita());
        newReceita.setFormaDePagamento(receitaDto.getFormaDePagamento());
        newReceita.setCreated_at(Instant.now());

        newReceita = receitaRepository.save(newReceita);
        return new ReceitaDto(newReceita);
    }

    @Override
    public ReceitaDto update(long id, ReceitaDto receitaDto) {
        Receita receita = receitaRepository.findById(id).get();
        Instant created_at = receita.getCreated_at();

        BeanUtils.copyProperties(receitaDto, receita);

        receita.setId(id);
        receita.setCategoria(receitaDto.getCategoriaDeReceita());
        receita.setFormaDePagamento(receitaDto.getFormaDePagamento());
        receita.setCreated_at(created_at);
        receita.setUpdated_at(Instant.now());

        receita = receitaRepository.save(receita);
        return new ReceitaDto(receita);
    }

    @Override
    public void deleteById(long id) {
        Receita receita = receitaRepository.findById(id).get();
        receitaRepository.delete(receita);
    }
}
