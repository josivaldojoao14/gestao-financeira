package com.cdg.cadernog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdg.cadernog.dtos.UserDto;
import com.cdg.cadernog.dtos.auth.AuthResponseDto;
import com.cdg.cadernog.dtos.auth.UserLoginDto;
import com.cdg.cadernog.form.AddRoleToUserForm;
import com.cdg.cadernog.form.RolesToRevokeForm;
import com.cdg.cadernog.security.JWTGenerator;
import com.cdg.cadernog.services.impl.UserServiceImpl;
import com.cdg.cadernog.util.URL;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/caderno")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;

    @GetMapping(value = "/users")
    public ResponseEntity<List<?>> getAll() {
        List<UserDto> users = userServiceImpl.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        UserDto user = userServiceImpl.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/auth/user/register")
    public ResponseEntity<?> save(@RequestBody UserDto user) {
        UserDto newUser = userServiceImpl.save(user);
        return ResponseEntity.ok().body(newUser);
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto user, @PathVariable Long id) {
        UserDto newUser = userServiceImpl.update(id, user);
        return ResponseEntity.ok().body(newUser);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/user/findByUsername")
    public ResponseEntity<?> findByUsername(@RequestParam(value = "username") String username) {
        username = URL.decodeParam(username);
        UserDto user = userServiceImpl.findByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/user/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserForm form) {
        userServiceImpl.addRoleToUser(form.getRoleName(), form.getUserName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/user/revokeRoleFromUser")
    public ResponseEntity<?> revokeRoleFromUser(@RequestBody RolesToRevokeForm form) {
        userServiceImpl.revokeRoleFromUser(form.getRoleName(), form.getUserName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/auth/user/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userLoginDto.getUsername(),
                userLoginDto.getPassword()
            ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.ok().body(new AuthResponseDto(token));
    }
}
