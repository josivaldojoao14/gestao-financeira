package com.cdg.cadernog.models;

import java.time.Instant;

import com.cdg.cadernog.dtos.DespesaDto;
import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.enums.FormasDePagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "despesas")
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

    @Column(name = "categoria")
    private CategoriasDespesa categoria;

    @Column(name = "forma_de_recebimento")
    private FormasDePagamento formaDePagamento;

    // dto to model
    public Despesa(DespesaDto obj) {
        id = obj.getId();
        title = obj.getTitle();
        description = obj.getDescription();
        value = obj.getValue();
        created_at =  Instant.parse(obj.getCreated_at());
        updated_at = obj.getUpdated_at();
        categoria = CategoriasDespesa.valueOf(obj.getCategoriaDeDespesa());
        formaDePagamento = FormasDePagamento.valueOf(obj.getFormaDePagamento());
    }

    public String getCategoria() {
        return this.categoria.toString();
    }

    public void setCategoria(String cat) {
        this.categoria = CategoriasDespesa.valueOf(cat);
    }

    public String getFormaDePagamento() {
        return this.formaDePagamento.toString();
    }

    public void setFormaDePagamento(String pag) {
        this.formaDePagamento = FormasDePagamento.valueOf(pag);
    }
}
