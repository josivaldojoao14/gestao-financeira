package com.cdg.cadernog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.services.impl.RoleServiceImpl;

@RestController
@RequestMapping("/caderno")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping(value = "/roles")
    public ResponseEntity<List<?>> getAll() {
        List<RoleDto> roles = roleServiceImpl.findAll();
        return ResponseEntity.ok().body(roles);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping(value = "/role/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        RoleDto role = roleServiceImpl.findById(id);
        return ResponseEntity.ok().body(role);
    }
}
