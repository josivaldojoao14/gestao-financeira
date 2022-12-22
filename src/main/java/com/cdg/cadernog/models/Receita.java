package com.cdg.cadernog.models;

import java.time.Instant;

import com.cdg.cadernog.dtos.ReceitaDto;
import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;

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
public class Receita{

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

    @Column(name = "categoria")
    private CategoriasReceita categoria;

    @Column(name = "forma_de_pagamento")
    private FormasDePagamento formaDePagamento;

    // dto to model
    public Receita(ReceitaDto obj) {
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at = Instant.parse(obj.getCreated_at());
        updated_at = obj.getUpdated_at();
        categoria = CategoriasReceita.valueOf(obj.getCategoriaDeReceita());
        formaDePagamento = FormasDePagamento.valueOf(obj.getFormaDePagamento());
    }
    
    public String getCategoria() {
        return this.categoria.toString();
    }

    public void setCategoria(String cat) {
        this.categoria = CategoriasReceita.valueOf(cat);
    }

    public String getFormaDePagamento() {
        return this.formaDePagamento.toString();
    }

    public void setFormaDePagamento(String pag) {
        this.formaDePagamento = FormasDePagamento.valueOf(pag);
    }
}
