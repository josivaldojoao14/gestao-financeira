package com.cdg.cadernog.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdg.cadernog.dtos.DespesaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;
import com.cdg.cadernog.models.Despesa;
import com.cdg.cadernog.repositories.DespesaRepository;
import com.cdg.cadernog.services.exceptions.ObjectNotFoundException;
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
        Despesa despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma despesa encontrada"));
        return new DespesaDto(despesa);
    }

    @Override
    public DespesaDto save(DespesaDto despesaDto) {
        String dep = despesaDto.getCategoriaDeDespesa();
        String pag = despesaDto.getFormaDePagamento();

        if (dep.isEmpty() || pag.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma categoria/forma de pagamento foi encontrada");
        } else {
            Despesa newDespesa = new Despesa();
            BeanUtils.copyProperties(despesaDto, newDespesa);

            newDespesa.setCategoria(dep);
            newDespesa.setFormaDePagamento(pag);
            newDespesa.setCreated_at(Instant.parse(despesaDto.getCreated_at()));

            newDespesa = despesaRepository.save(newDespesa);
            return new DespesaDto(newDespesa);
        }
    }

    @Override
    public DespesaDto update(long id, DespesaDto despesaDto) {
        String dep = despesaDto.getCategoriaDeDespesa();
        String pag = despesaDto.getFormaDePagamento();

        if (dep.isEmpty() || pag.isEmpty()) {
            throw new ObjectNotFoundException("Nenhuma categoria/forma de pagamento foi encontrada");
        } else {
            DespesaDto despesa = findById(id);
            Despesa despesaToUpdate = new Despesa(despesa);
            BeanUtils.copyProperties(despesaDto, despesaToUpdate);

            despesaToUpdate.setId(id);
            despesaToUpdate.setCategoria(dep);
            despesaToUpdate.setFormaDePagamento(pag);
            despesaToUpdate.setUpdated_at(Instant.now());

            despesaToUpdate = despesaRepository.save(despesaToUpdate);
            return new DespesaDto(despesaToUpdate);
        }  
    }

    @Override
    public void deleteById(long id) {
        DespesaDto despesa = findById(id);
        despesaRepository.delete(new Despesa(despesa));
    }

    @Override
    public SituacaoMensalDto sumByPeriod(int year, int month) {
        float total = despesaRepository.sumByPeriod(year, month);
        SituacaoMensalDto situacao = new SituacaoMensalDto(month, year, "Despesa", total);
        return situacao;
    }
}
