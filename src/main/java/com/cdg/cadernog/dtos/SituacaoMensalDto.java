package com.cdg.cadernog.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.cdg.cadernog.models.DespesaModel;
import com.cdg.cadernog.models.ReceitaModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituacaoMensalDto {
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer month;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer year;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String category;
    
    private float totalSpent;

    public SituacaoMensalDto(DespesaModel obj){
        month = LocalDateTime.ofInstant(obj.getCreated_at(), ZoneOffset.UTC).getMonth().getValue();
        year = LocalDateTime.ofInstant(obj.getCreated_at(), ZoneOffset.UTC).getYear();
        category = obj.getCategoria().getName();
        totalSpent = obj.getValue();
    }

    public SituacaoMensalDto(ReceitaModel obj) {
        month = LocalDateTime.ofInstant(obj.getCreated_at(), ZoneOffset.UTC).getMonth().getValue();
        year = LocalDateTime.ofInstant(obj.getCreated_at(), ZoneOffset.UTC).getYear();
        category = obj.getCategoria().getName();
        totalSpent = obj.getValue();
    }
}

