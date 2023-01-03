package com.cdg.cadernog.models;

import java.time.Instant;

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.categorias.CategoriaReceitaModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receitas")
public class ReceitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    //@Column(name = "categoria")
    @ManyToOne
    private CategoriaReceitaModel categoria;

    //@Column(name = "forma_de_pagamento")
    @ManyToOne
    private FormaDePagamentoModel formaDePagamento;

    // dto to model
    public ReceitaModel(ReceitaDto obj) {
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at = Instant.parse(obj.getCreated_at());
        updated_at = obj.getUpdated_at();
        categoria = new CategoriaReceitaModel(null, CategoriasReceita.valueOf(obj.getCategoriaDeReceita()));
        formaDePagamento = new FormaDePagamentoModel(null, FormasDePagamento.valueOf(obj.getFormaDePagamento()));
    } 
}
