package com.cdg.cadernog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;
import com.cdg.cadernog.services.impl.ReceitaServiceImpl;

@RestController
@RequestMapping(value = "/caderno")
public class ReceitaController {

    @Autowired
    private ReceitaServiceImpl receitaServiceImpl;

    @GetMapping(value = "/receitas")
    public ResponseEntity<List<?>> getAll() {
        List<ReceitaDto> receitas = receitaServiceImpl.findAll();
        return ResponseEntity.ok().body(receitas);
    }

    @GetMapping(value = "/receita/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ReceitaDto receita = receitaServiceImpl.findById(id);
        return ResponseEntity.ok().body(receita);
    }

    @PostMapping(value = "/receita")
    public ResponseEntity<?> save(@RequestBody ReceitaDto receita) {
        ReceitaDto newReceita = receitaServiceImpl.save(receita);
        return ResponseEntity.ok().body(newReceita);
    }

    @PutMapping(value = "/receita/{id}")
    public ResponseEntity<?> update(@RequestBody ReceitaDto receita, @PathVariable Long id) {
        ReceitaDto newReceita = receitaServiceImpl.update(id, receita);
        return ResponseEntity.ok().body(newReceita);
    }

    @DeleteMapping(value = "/receita/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        receitaServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/receita/{year}/{month}")
    public ResponseEntity<?> sumByPeriod(@PathVariable int year, @PathVariable int month) {
        SituacaoMensalDto total = receitaServiceImpl.sumByPeriod(year, month);
        return ResponseEntity.ok().body(total);
    }
}
