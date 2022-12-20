package com.cdg.cadernog.dtos;

import java.time.Instant;

import com.cdg.cadernog.models.Despesa;
import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'", timezone = "GMT-3")
    private Instant created_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'", timezone = "GMT-3")
    private Instant updated_at;

    // model to dto
    public DespesaDto(Despesa obj){
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at = obj.getCreated_at();
        updated_at = obj.getUpdated_at();
    }
}
