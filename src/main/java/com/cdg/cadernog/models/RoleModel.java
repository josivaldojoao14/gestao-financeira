package com.cdg.cadernog.models;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.enums.Cargos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cargos")
public class RoleModel implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
