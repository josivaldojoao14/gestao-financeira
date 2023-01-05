package com.cdg.cadernog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> save(@RequestBody DespesaDto despesa, Authentication authentication) {
        despesa.setUserName(extractUser(authentication));
        despesaServiceImpl.save(despesa);
        return ResponseEntity.ok().body("Despesa criada com sucesso!");
    }

    @PutMapping(value = "/despesa/{id}")
    public ResponseEntity<?> update(@RequestBody DespesaDto despesa, @PathVariable Long id,
            Authentication authentication) {
        despesa.setUserName(extractUser(authentication));
        despesaServiceImpl.update(id, despesa);
        return ResponseEntity.ok().body("Despesa atualizada com sucesso!");
    }

    @DeleteMapping(value = "/despesa/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication authentication) {
        DespesaDto despesa = despesaServiceImpl.findById(id);
        despesa.setUserName(extractUser(authentication));
        despesaServiceImpl.delete(despesa);
        return ResponseEntity.ok().body("Despesa removida com sucesso!");
    }

    @GetMapping(value = "/despesa/monthlyExpense/{year}/{month}")
    public ResponseEntity<?> getMonthlyExpense(@PathVariable int year, @PathVariable int month) {
        SituacaoMensalDto total = despesaServiceImpl.getMonthlyExpense(year, month);
        return ResponseEntity.ok().body(total);
    }

    @GetMapping(value = "/despesa/annualExpense/{year}")
    public ResponseEntity<?> getAnnualExpense(@PathVariable int year) {
        SituacaoMensalDto total = despesaServiceImpl.getAnnualExpense(year);
        return ResponseEntity.ok().body(total);
    }

    @GetMapping(value = "/despesa/summary/{year}/{month}")
    public ResponseEntity<?> getSummaryOfPeriod(@PathVariable int year, @PathVariable int month) {
        List<SituacaoMensalDto> teste = despesaServiceImpl.getSummaryOfPeriod(year, month);
        return ResponseEntity.ok().body(teste);
    }

    private String extractUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
