package com.cdg.cadernog.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.cdg.cadernog.dtos.RoleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesToRevokeForm {
    @NotEmpty
    private String userName;
    @NotEmpty
    private List<RoleDto> roleName;
    
}
