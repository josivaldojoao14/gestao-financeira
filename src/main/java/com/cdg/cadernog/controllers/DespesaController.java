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

import com.cdg.cadernog.dtos.DespesaDto;
import com.cdg.cadernog.dtos.SituacaoMensalDto;
import com.cdg.cadernog.services.impl.DespesaServiceImpl;

@RestController
@RequestMapping(value = "/caderno")
public class DespesaController {

    @Autowired
    private DespesaServiceImpl despesaServiceImpl;

    @GetMapping(value = "/despesas")
    public ResponseEntity<List<?>> getAll() {
        List<DespesaDto> despesas = despesaServiceImpl.findAll();
        return ResponseEntity.ok().body(despesas);
    }

    @GetMapping(value = "/despesa/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        DespesaDto despesa = despesaServiceImpl.findById(id);
        return ResponseEntity.ok().body(despesa);
    }
    
    @PostMapping(value = "/despesa")
    public ResponseEntity<?> save(@RequestBody DespesaDto despesa) {
        DespesaDto newDespesa = despesaServiceImpl.save(despesa);
        return ResponseEntity.ok().body(newDespesa);
    }

    @PutMapping(value = "/despesa/{id}")
    public ResponseEntity<?> update(@RequestBody DespesaDto despesa, @PathVariable Long id) {
        DespesaDto newDespesa = despesaServiceImpl.update(id, despesa);
        return ResponseEntity.ok().body(newDespesa);
    }

    @DeleteMapping(value = "/despesa/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        despesaServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/despesa/{year}/{month}")
    public ResponseEntity<?> sumByPeriod(@PathVariable int year, @PathVariable int month) {
        SituacaoMensalDto total = despesaServiceImpl.sumByPeriod(year, month);
        return ResponseEntity.ok().body(total);
    }
}
