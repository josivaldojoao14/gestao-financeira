package com.cdg.cadernog.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cdg.cadernog.models.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String fullName;
    private String phone;
    private String username;
    private String password;
    private List<RoleDto> roles = new ArrayList<>();

    public UserDto(UserModel obj) {
        id = obj.getId();
        fullName = obj.getFullName();
        phone = obj.getPhone();
        username = obj.getUsername();
        password = obj.getPassword();
        roles = obj.getRoles().stream().map(x -> new RoleDto(x)).collect(Collectors.toList());
    }
}
