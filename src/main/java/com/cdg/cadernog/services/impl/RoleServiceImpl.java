package com.cdg.cadernog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.models.RoleModel;
import com.cdg.cadernog.repositories.RoleRepository;
import com.cdg.cadernog.services.exceptions.ObjectNotFoundException;
import com.cdg.cadernog.services.interfaces.RoleService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {
        List<RoleModel> roles = roleRepository.findAll();
        List<RoleDto> rolesDto = roles.stream().map(x -> new RoleDto(x)).collect(Collectors.toList());
        return rolesDto;
    }

    @Override
    public RoleDto findById(long id) {
        RoleModel role = roleRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Nenhum cargo encontrado"));
        return new RoleDto(role);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        RoleModel newRole = new RoleModel();
        BeanUtils.copyProperties(roleDto, newRole);
        newRole = roleRepository.save(newRole);
        return new RoleDto(newRole);
    }

    @Override
    public RoleDto update(long id, RoleDto roleDto) {
        RoleDto role = findById(id);
        RoleModel roleToUpdate = new RoleModel(role);
        BeanUtils.copyProperties(roleDto, roleToUpdate);

        roleToUpdate.setId(id);
        roleToUpdate = roleRepository.save(roleToUpdate);
        return new RoleDto(roleToUpdate);  
    }

    @Override
    public void deleteById(long id) {
        RoleDto role = findById(id);
        roleRepository.delete(new RoleModel(role));
    }
}
