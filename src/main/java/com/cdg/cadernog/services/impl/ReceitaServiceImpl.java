package com.cdg.cadernog.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;
import com.cdg.cadernog.models.Receita;
import com.cdg.cadernog.repositories.ReceitaRepository;
import com.cdg.cadernog.services.exceptions.ObjectNotFoundException;
import com.cdg.cadernog.services.interfaces.ReceitaService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ReceitaServiceImpl implements ReceitaService {

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
        Receita receita = receitaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma receita encontrada"));
        return new ReceitaDto(receita);
    }

    @Override
    public ReceitaDto save(ReceitaDto receitaDto) {
        String dep = receitaDto.getCategoriaDeReceita();
        String pag = receitaDto.getFormaDePagamento();

        if (dep.isEmpty() || pag.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma categoria/forma de pagamento foi encontrada");
        } else {
            Receita newReceita = new Receita();
            BeanUtils.copyProperties(receitaDto, newReceita);
            newReceita.setCategoria(dep);
            newReceita.setFormaDePagamento(pag);
            newReceita.setCreated_at(Instant.parse(receitaDto.getCreated_at()));

            newReceita = receitaRepository.save(newReceita);
            return new ReceitaDto(newReceita);
        }
    }

    @Override
    public ReceitaDto update(long id, ReceitaDto receitaDto) {
        String dep = receitaDto.getCategoriaDeReceita();
        String pag = receitaDto.getFormaDePagamento();

        if (dep.isEmpty() || pag.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma categoria/forma de pagamento foi encontrada");
        } else {
            ReceitaDto receita = findById(id);
            Receita receitaToUpdate = new Receita(receita);
            BeanUtils.copyProperties(receitaDto, receitaToUpdate);

            receitaToUpdate.setId(id);
            receitaToUpdate.setCategoria(dep);
            receitaToUpdate.setFormaDePagamento(pag);
            receitaToUpdate.setUpdated_at(Instant.now());

            receitaToUpdate = receitaRepository.save(receitaToUpdate);
            return new ReceitaDto(receitaToUpdate);
        }
    }

    @Override
    public void deleteById(long id) {
        ReceitaDto receita = findById(id);
        receitaRepository.delete(new Receita(receita));
    }

    @Override
    public SituacaoMensalDto sumByPeriod(int year, int month) {
        float total = receitaRepository.sumByPeriod(year, month);
        SituacaoMensalDto situacao = new SituacaoMensalDto(month, year, "Receita", total);
        return situacao;
    }
}
