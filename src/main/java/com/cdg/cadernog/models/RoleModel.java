package com.cdg.cadernog.models;

import org.springframework.security.core.GrantedAuthority;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.enums.Cargos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Cargos name;

    public RoleModel(RoleDto obj) {
        id = obj.getId();
        name = Cargos.valueOf(obj.getName());
    }
    
    @Override
    public String getAuthority() {
        return this.getName();
    }

    public String getName() {
        return this.name.toString();
    }

    public void setName(String cargo) {
        this.name = Cargos.valueOf(cargo);
    }

}
