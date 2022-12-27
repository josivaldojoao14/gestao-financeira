package com.cdg.cadernog.services.interfaces;

import java.util.List;

import com.cdg.cadernog.dtos.UserDto;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(long id);

    UserDto save(UserDto userDto);

    UserDto update(long id, UserDto userDto);

    void deleteById(long id);

    UserDto findByUsername(String username);

    boolean existsByUsername(String username);

}
