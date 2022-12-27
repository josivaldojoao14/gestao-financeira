package com.cdg.cadernog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.cdg.cadernog.form.AddRoleToUserForm;
import com.cdg.cadernog.services.impl.UserServiceImpl;
import com.cdg.cadernog.util.URL;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        List<UserDto> users = userServiceImpl.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        UserDto user = userServiceImpl.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserDto user) {
        UserDto newUser = userServiceImpl.save(user);
        return ResponseEntity.ok().body(newUser);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto user, @PathVariable Long id) {
        UserDto newUser = userServiceImpl.update(id, user);
        return ResponseEntity.ok().body(newUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(value = "/findByUsername")
    public ResponseEntity<?> findByUsername(@RequestParam(value = "username") String username) {
        username = URL.decodeParam(username);
        UserDto user = userServiceImpl.findByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserForm form) {
        userServiceImpl.addRoleToUser(form.getRoleName(), form.getUserName());
        return ResponseEntity.noContent().build();
    }


}
