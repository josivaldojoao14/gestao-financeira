package com.cdg.cadernog.dtos;

import java.time.ZoneOffset;

import com.cdg.cadernog.models.Despesa;
import com.cdg.cadernog.models.Receita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SituacaoMensalDto {

    private int month;
    private int year;
    private String categoria;
    private float total;

    // model to dto - RECEITA
    public SituacaoMensalDto(Receita obj){
        month = obj.getCreated_at().atZone(ZoneOffset.UTC).toLocalDate().getMonthValue();
        year = obj.getCreated_at().atZone(ZoneOffset.UTC).toLocalDate().getYear();
        categoria = obj.getCategoria().toString();
        total = obj.getValue();
    }

    // model to dto - DESPESA
    public SituacaoMensalDto(Despesa obj) {
        month = obj.getCreated_at().atZone(ZoneOffset.UTC).toLocalDate().getMonthValue();
        year = obj.getCreated_at().atZone(ZoneOffset.UTC).toLocalDate().getYear();
        categoria = obj.getCategoria().toString();
        total = obj.getValue();
    }
}
