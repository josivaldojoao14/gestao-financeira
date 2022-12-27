package com.cdg.cadernog.form;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleToUserForm {
    @JsonProperty(required = true)
    private String userName;

    @JsonProperty(required = true)
    private String roleName;
}
