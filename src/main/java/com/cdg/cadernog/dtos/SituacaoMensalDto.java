package com.cdg.cadernog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SituacaoMensalDto {

    private String type;
    private int month;
    private int year;
    private float total;
}
