package com.cdg.cadernog.models;

import java.time.Instant;

import com.cdg.cadernog.dtos.DespesaDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "titulo")
    private String title;

    @Column(name = "descricao")
    private String description;

    @Column(name = "criado_em")
    private Instant created_at;

    @Column(name = "atualizado_em")
    private Instant updated_at;

    @Column(name = "valor")
    private float value;

    // dto to model
    public Despesa(DespesaDto obj){
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at = obj.getCreated_at();
        updated_at = obj.getUpdated_at();
    }
}
