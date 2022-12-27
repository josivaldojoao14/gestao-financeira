package com.cdg.cadernog.dtos;

import com.cdg.cadernog.models.RoleModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private long id;
    private String name;

    public RoleDto(RoleModel obj) {
        id = obj.getId();
        name = obj.getName();
    }
}
