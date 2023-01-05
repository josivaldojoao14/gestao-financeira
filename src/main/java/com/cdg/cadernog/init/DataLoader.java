package com.cdg.cadernog.init;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.enums.CategoriasDespesa;
import com.cdg.cadernog.enums.CategoriasReceita;
import com.cdg.cadernog.enums.FormasDePagamento;
import com.cdg.cadernog.models.FormaDePagamentoModel;
import com.cdg.cadernog.models.RoleModel;
import com.cdg.cadernog.models.UserModel;
import com.cdg.cadernog.models.categorias.CategoriaDespesaModel;
import com.cdg.cadernog.models.categorias.CategoriaReceitaModel;
import com.cdg.cadernog.repositories.FormaDePagamentoRepository;
import com.cdg.cadernog.repositories.RoleRepository;
import com.cdg.cadernog.repositories.UserRepository;
import com.cdg.cadernog.repositories.categorias.CategoriaDespesaRepository;
import com.cdg.cadernog.repositories.categorias.CategoriaReceitaRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @Autowired
    private CategoriaReceitaRepository categoriaReceitaRepository;

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        RoleModel role1 = new RoleModel(null, Cargos.ROLE_USER);
        RoleModel role2 = new RoleModel(null, Cargos.ROLE_ADMIN);
        RoleModel role3 = new RoleModel(null, Cargos.ROLE_SUPER_ADMIN);

        roleRepository.saveAll(Arrays.asList(role1, role2, role3));

        CategoriaDespesaModel catDep1 = new CategoriaDespesaModel(null, CategoriasDespesa.AGUA_LUZ_GAS);
        CategoriaDespesaModel catDep2 = new CategoriaDespesaModel(null, CategoriasDespesa.INTERNET);
        CategoriaDespesaModel catDep3 = new CategoriaDespesaModel(null, CategoriasDespesa.ALIMENTO);
        CategoriaDespesaModel catDep4 = new CategoriaDespesaModel(null, CategoriasDespesa.FATURA);
        CategoriaDespesaModel catDep5 = new CategoriaDespesaModel(null, CategoriasDespesa.BOLETO);
        CategoriaDespesaModel catDep6 = new CategoriaDespesaModel(null, CategoriasDespesa.MULTA);
        CategoriaDespesaModel catDep7 = new CategoriaDespesaModel(null, CategoriasDespesa.OUTRO);

        categoriaDespesaRepository.saveAll(Arrays.asList(catDep1, catDep2, catDep3, catDep4, catDep5, catDep6, catDep7));

        CategoriaReceitaModel catRec1 = new CategoriaReceitaModel(null, CategoriasReceita.VENDA);
        CategoriaReceitaModel catRec2 = new CategoriaReceitaModel(null, CategoriasReceita.PRESTACAO_DE_SERVICO);
        CategoriaReceitaModel catRec3 = new CategoriaReceitaModel(null, CategoriasReceita.SALARIO);
        CategoriaReceitaModel catRec4 = new CategoriaReceitaModel(null, CategoriasReceita.RENDIMENTO);
        CategoriaReceitaModel catRec5 = new CategoriaReceitaModel(null, CategoriasReceita.ALUGUEL);
        CategoriaReceitaModel catRec6 = new CategoriaReceitaModel(null, CategoriasReceita.OUTRO);

        categoriaReceitaRepository.saveAll(Arrays.asList(catRec1, catRec2, catRec3, catRec4, catRec5, catRec6));
        
        FormaDePagamentoModel fPag1 = new FormaDePagamentoModel(null, FormasDePagamento.DINHEIRO);
        FormaDePagamentoModel fPag2 = new FormaDePagamentoModel(null, FormasDePagamento.PIX);
        FormaDePagamentoModel fPag3 = new FormaDePagamentoModel(null, FormasDePagamento.DEBITO);
        FormaDePagamentoModel fPag4 = new FormaDePagamentoModel(null, FormasDePagamento.CREDITO);
        FormaDePagamentoModel fPag5 = new FormaDePagamentoModel(null, FormasDePagamento.BOLETO);

        formaDePagamentoRepository.saveAll(Arrays.asList(fPag1, fPag2, fPag3, fPag4, fPag5));

        UserModel userComum = new UserModel(null, "Maria", "986543243", "maria123", passwordEncoder.encode("123456"), new ArrayList<>());
        UserModel userAdmin = new UserModel(null, "Joana", "953562353", "joana321", passwordEncoder.encode("123456"), new ArrayList<>());
        UserModel userSuperAdmin = new UserModel(null, "Carol", "977554433", "carol567", passwordEncoder.encode("123456"), new ArrayList<>());
        
        userRepository.saveAll(Arrays.asList(userComum, userAdmin, userSuperAdmin));

        userComum.getRoles().add(role1);
        userAdmin.getRoles().add(role2);
        userSuperAdmin.getRoles().add(role3);
        userRepository.saveAll(Arrays.asList(userComum, userAdmin, userSuperAdmin));

    }
    
}
