package com.cdg.cadernog.repositories.categorias;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.models.categorias.CategoriaDespesaModel;

public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesaModel, Long>{
    Optional<CategoriaDespesaModel> findByName(CategoriasDespesa cat);
}
