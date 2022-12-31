package com.cdg.cadernog.dtos;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import com.cdg.cadernog.models.DespesaModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDto {

    private long id;
    private String title;
    private String description;
    private float value;
    @NotEmpty
    private String categoriaDeDespesa;
    @NotEmpty
    private String formaDePagamento;
    private String created_at;
    private Instant updated_at;

    // model to dto
    public DespesaDto(DespesaModel obj) {
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at = obj.getCreated_at().toString();
        updated_at = obj.getUpdated_at();
        categoriaDeDespesa = obj.getCategoria().getName();
        formaDePagamento = obj.getFormaDePagamento().getName();
    }
}
