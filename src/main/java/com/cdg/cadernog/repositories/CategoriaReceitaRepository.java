package com.cdg.cadernog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.models.categorias.CategoriaReceitaModel;

public interface CategoriaReceitaRepository extends JpaRepository<CategoriaReceitaModel, Long>{
    Optional<CategoriaReceitaModel> findByName(CategoriasReceita rec);
}
