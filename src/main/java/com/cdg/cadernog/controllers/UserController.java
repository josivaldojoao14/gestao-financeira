package com.cdg.cadernog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping(value = "/users")
    public ResponseEntity<List<?>> getAll() {
        List<UserDto> users = userServiceImpl.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        UserDto user = userServiceImpl.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto user, @PathVariable Long id) {
        userServiceImpl.update(id, user);
        return ResponseEntity.ok().body("Cadastro atualizado com sucesso!");
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userServiceImpl.deleteById(id);
        return ResponseEntity.ok().body("Usu??rio removido com sucesso!");
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping(value = "/user/findByUsername")
    public ResponseEntity<?> findByUsername(@RequestParam(value = "username") String username) {
        username = URL.decodeParam(username);
        UserDto user = userServiceImpl.findByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping(value = "/user/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserForm form) {
        userServiceImpl.addRoleToUser(form.getRoleName(), form.getUserName());
        return ResponseEntity.ok().body("A permiss??o foi dada com sucesso!");
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping(value = "/user/revokeRoleFromUser")
    public ResponseEntity<?> revokeRoleFromUser(@RequestBody RolesToRevokeForm form) {
        userServiceImpl.revokeRoleFromUser(form.getRoleName(), form.getUserName());
        return ResponseEntity.ok().body("A permiss??o foi removida com sucesso!");
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

    @PostMapping(value = "/auth/user/register")
    public ResponseEntity<?> save(@RequestBody UserDto user) {
        userServiceImpl.save(user);
        return ResponseEntity.ok().body("O usu??rio foi cadastrado com sucesso!");
    }
}
