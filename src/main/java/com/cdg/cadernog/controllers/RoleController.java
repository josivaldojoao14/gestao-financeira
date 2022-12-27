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
import org.springframework.web.bind.annotation.RestController;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.services.impl.RoleServiceImpl;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @GetMapping
    public ResponseEntity<List<?>> getAll() {
        List<RoleDto> roles = roleServiceImpl.findAll();
        return ResponseEntity.ok().body(roles);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        RoleDto role = roleServiceImpl.findById(id);
        return ResponseEntity.ok().body(role);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RoleDto role) {
        RoleDto newRole = roleServiceImpl.save(role);
        return ResponseEntity.ok().body(newRole);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody RoleDto role, @PathVariable Long id) {
        RoleDto newRole = roleServiceImpl.update(id, role);
        return ResponseEntity.ok().body(newRole);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
