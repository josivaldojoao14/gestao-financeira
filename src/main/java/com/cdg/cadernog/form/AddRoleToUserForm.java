package com.cdg.cadernog.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleToUserForm {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String roleName;
}
