package com.cdg.cadernog.dtos;

import com.cdg.cadernog.models.RoleModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    private String name;

    public RoleDto(RoleModel obj) {
        id = obj.getId();
        name = obj.getName();
    }
}
