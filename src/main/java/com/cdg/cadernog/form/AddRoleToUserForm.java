package com.cdg.cadernog.form;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRoleToUserForm {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String roleName;
}
