package com.cdg.cadernog.dtos;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import com.cdg.cadernog.models.ReceitaModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @NotEmpty
    private String categoriaDeReceita;
    @NotEmpty
    private String formaDePagamento;
    private String created_at;
    private Instant updated_at;

    @JsonIgnore
    private String userName;

    // model to dto
    public ReceitaDto(ReceitaModel obj) {
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at = obj.getCreated_at().toString();
        updated_at = obj.getUpdated_at();
        categoriaDeReceita = obj.getCategoria().getName();
        formaDePagamento = obj.getFormaDePagamento().getName();
    }
}
