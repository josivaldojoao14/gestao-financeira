package com.cdg.cadernog.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cdg.cadernog.dtos.UserDto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements UserDetails {

    private long id;
    private String fullName;
    private String phone;
    private String username;
    private String password;

    private List<RoleModel> roles = new ArrayList<>();

    public UserModel(UserDto obj) {
        id = obj.getId();
        fullName = obj.getFullName();
        phone = obj.getPhone();
        username = obj.getUsername();
        password = obj.getPassword();
        roles = obj.getRoles().stream().map(x -> new RoleModel(x)).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
