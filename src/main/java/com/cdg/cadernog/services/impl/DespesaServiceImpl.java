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
import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.DespesaModel;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.categorias.CategoriaDespesaModel;
import com.cdg.cadernog.repositories.CategoriaDespesaRepository;
import com.cdg.cadernog.repositories.DespesaRepository;
import com.cdg.cadernog.repositories.FormaDePagamentoRepository;
import com.cdg.cadernog.services.exceptions.ObjectNotFoundException;
import com.cdg.cadernog.services.interfaces.DespesaService;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class DespesaServiceImpl implements DespesaService {

    @Autowired
    private final DespesaRepository despesaRepository;

    @Autowired
    private final CategoriaDespesaRepository categoriaDespesaRepository;

    @Autowired
    private final FormaDePagamentoRepository formaDePagamentoRepository;

    @Override
    public List<DespesaDto> findAll() {
        List<DespesaModel> despesas = despesaRepository.findAll();
        List<DespesaDto> despesasDto = despesas.stream().map(x -> new DespesaDto(x)).collect(Collectors.toList());
        return despesasDto;
    }

    @Override
    public DespesaDto findById(long id) {
        DespesaModel despesa = despesaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma despesa encontrada"));
        return new DespesaDto(despesa);
    }

    @Override
    public DespesaDto save(DespesaDto despesaDto) {
        FormaDePagamentoModel fpag = findPagamento(despesaDto);
        CategoriaDespesaModel cat = findCategoria(despesaDto);

        DespesaModel newDespesa = new DespesaModel();
        BeanUtils.copyProperties(despesaDto, newDespesa);

        newDespesa.setCategoria(cat);
        newDespesa.setFormaDePagamento(fpag);
        newDespesa.setCreated_at(Instant.parse(despesaDto.getCreated_at()));

        newDespesa = despesaRepository.save(newDespesa);
        return new DespesaDto(newDespesa);
    }

    @Override
    public DespesaDto update(long id, DespesaDto despesaDto) {
        FormaDePagamentoModel fpag = findPagamento(despesaDto);
        CategoriaDespesaModel cat = findCategoria(despesaDto);

        DespesaDto despesa = findById(id);
        DespesaModel despesaToUpdate = new DespesaModel(despesa);
        BeanUtils.copyProperties(despesaDto, despesaToUpdate);

        despesaToUpdate.setId(id);
        despesaToUpdate.setCategoria(cat);
        despesaToUpdate.setFormaDePagamento(fpag);
        despesaToUpdate.setUpdated_at(Instant.now());

        despesaToUpdate = despesaRepository.save(despesaToUpdate);
        return new DespesaDto(despesaToUpdate);
    }

    @Override
    public void deleteById(long id) {
        DespesaDto despesa = findById(id);
        despesaRepository.delete(new DespesaModel(despesa));
    }

    // @Override
    // public SituacaoMensalDto sumByPeriod(int year, int month) {
    //     float total = despesaRepository.sumByPeriod(year, month);
    //     SituacaoMensalDto situacao = new SituacaoMensalDto(month, year, "Despesa", total);
    //     return situacao;
    // }

    private FormaDePagamentoModel findPagamento(DespesaDto despesaDto){
        FormaDePagamentoModel fpag = formaDePagamentoRepository
            .findByName(FormasDePagamento.valueOf(despesaDto.getFormaDePagamento()))
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma forma de pagamento encontrada"));
        return fpag;
    }

    private CategoriaDespesaModel findCategoria(DespesaDto despesaDto) {
        CategoriaDespesaModel cat = categoriaDespesaRepository
            .findByName(CategoriasDespesa.valueOf(despesaDto.getCategoriaDeDespesa()))
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma categoria de despesa encontrada"));
        return cat;
    }
}
