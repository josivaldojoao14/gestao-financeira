package com.cdg.cadernog.services.interfaces;

import java.util.List;

import com.cdg.cadernog.dtos.RoleDto;

public interface RoleService {
    List<RoleDto> findAll();

    RoleDto findById(long id);

    RoleDto save(RoleDto roleDto);

    RoleDto update(long id, RoleDto roleDto);

    void deleteById(long id);
}
