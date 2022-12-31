package com.cdg.cadernog.init;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.models.RoleModel;
import com.cdg.cadernog.repositories.RoleRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        RoleModel role1 = new RoleModel(null, Cargos.USER);
        RoleModel role2 = new RoleModel(null, Cargos.ADMIN);
        RoleModel role3 = new RoleModel(null, Cargos.SUPER_ADMIN);

        roleRepository.saveAll(Arrays.asList(role1, role2, role3));
    }
    
}
