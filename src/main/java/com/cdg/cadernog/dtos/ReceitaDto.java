package com.cdg.cadernog.dtos;

import java.time.Instant;

import com.cdg.cadernog.models.Receita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaDto {
    
    private long id;
    private String title;
    private String description;
    private float value;
    private String categoriaDeReceita;
    private String formaDePagamento;
    private String created_at;
    private Instant updated_at;

    // model to dto
    public ReceitaDto(Receita obj) {
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at = obj.getCreated_at().toString();
        updated_at = obj.getUpdated_at();
        categoriaDeReceita = obj.getCategoria();
        formaDePagamento = obj.getFormaDePagamento();
    }
}
