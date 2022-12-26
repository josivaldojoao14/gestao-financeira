package com.cdg.cadernog.models;

import org.springframework.security.core.GrantedAuthority;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.enums.Cargos;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel implements GrantedAuthority{

    private long id;
    private Cargos name;

    public RoleModel(RoleDto obj) {
        id = obj.getId();
        name = Cargos.valueOf(obj.getName());
    }
    
    @Override
    public String getAuthority() {
        return this.getCargo();
    }

    public String getCargo() {
        return this.name.toString();
    }

    public void setCargo(String cargo) {
        this.name = Cargos.valueOf(cargo);
    }

}
