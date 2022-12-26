package com.cdg.cadernog.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.cdg.cadernog.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserDto {
    private long id;
    private String fullName;
    private String phone;
    private String username;
    private String password;
    private Collection<RoleDto> roles = new ArrayList<>();

    public UserDto(UserModel obj) {
        id = obj.getId();
        fullName = obj.getFullName();
        phone = obj.getPhone();
        username = obj.getUsername();
        password = obj.getPassword();
        roles = obj.getRoles().stream().map(x -> new RoleDto(x)).collect(Collectors.toList());
    }
}
