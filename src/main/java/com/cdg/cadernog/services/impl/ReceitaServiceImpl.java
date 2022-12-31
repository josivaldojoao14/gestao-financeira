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
import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.ReceitaModel;
import com.cdg.cadernog.models.categorias.CategoriaReceitaModel;
import com.cdg.cadernog.repositories.CategoriaReceitaRepository;
import com.cdg.cadernog.repositories.FormaDePagamentoRepository;
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

    @Autowired
    private final CategoriaReceitaRepository categoriaReceitaRepository;

    @Autowired
    private final FormaDePagamentoRepository formaDePagamentoRepository;

    @Override
    public List<ReceitaDto> findAll() {
        List<ReceitaModel> receitas = receitaRepository.findAll();
        List<ReceitaDto> receitasDto = receitas.stream().map(x -> new ReceitaDto(x)).collect(Collectors.toList());
        return receitasDto;
    }

    @Override
    public ReceitaDto findById(long id) {
        ReceitaModel receita = receitaRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma receita encontrada"));
        return new ReceitaDto(receita);
    }

    @Override
    public ReceitaDto save(ReceitaDto receitaDto) {
        FormaDePagamentoModel fpag = findPagamento(receitaDto);
        CategoriaReceitaModel cat = findCategoria(receitaDto);

        ReceitaModel newReceita = new ReceitaModel();
        BeanUtils.copyProperties(receitaDto, newReceita);

        newReceita.setCategoria(cat);
        newReceita.setFormaDePagamento(fpag);
        newReceita.setCreated_at(Instant.parse(receitaDto.getCreated_at()));

        newReceita = receitaRepository.save(newReceita);
        return new ReceitaDto(newReceita);
    }

    @Override
    public ReceitaDto update(long id, ReceitaDto receitaDto) {
        FormaDePagamentoModel fpag = findPagamento(receitaDto);
        CategoriaReceitaModel cat = findCategoria(receitaDto);

        ReceitaDto receita = findById(id);
        ReceitaModel receitaToUpdate = new ReceitaModel(receita);
        BeanUtils.copyProperties(receitaDto, receitaToUpdate);

        receitaToUpdate.setId(id);
        receitaToUpdate.setCategoria(cat);
        receitaToUpdate.setFormaDePagamento(fpag);
        receitaToUpdate.setUpdated_at(Instant.now());

        receitaToUpdate = receitaRepository.save(receitaToUpdate);
        return new ReceitaDto(receitaToUpdate);
    }

    @Override
    public void deleteById(long id) {
        ReceitaDto receita = findById(id);
        receitaRepository.delete(new ReceitaModel(receita));
    }

    // @Override
    // public SituacaoMensalDto sumByPeriod(int year, int month) {
    //     float total = receitaRepository.sumByPeriod(year, month);
    //     SituacaoMensalDto situacao = new SituacaoMensalDto(month, year, "Receita", total);
    //     return situacao;
    // }

    private FormaDePagamentoModel findPagamento(ReceitaDto receitaDto) {
        FormaDePagamentoModel fpag = formaDePagamentoRepository
            .findByName(FormasDePagamento.valueOf(receitaDto.getFormaDePagamento()))
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma forma de pagamento encontrada"));
        return fpag;
    }

    private CategoriaReceitaModel findCategoria(ReceitaDto receitaDto) {
        CategoriaReceitaModel cat = categoriaReceitaRepository
            .findByName(CategoriasReceita.valueOf(receitaDto.getCategoriaDeReceita()))
            .orElseThrow(() -> new ObjectNotFoundException("Nenhuma categoria de receita encontrada"));
        return cat;
    }
}
