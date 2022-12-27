package com.cdg.cadernog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdg.cadernog.dtos.UserDto;
import com.cdg.cadernog.models.UserModel;
import com.cdg.cadernog.repositories.UserRepository;
import com.cdg.cadernog.services.exceptions.ObjectNotFoundException;
import com.cdg.cadernog.services.exceptions.UsernameAlreadyTakenException;
import com.cdg.cadernog.services.interfaces.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário nao encontrado"));
        
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public List<UserDto> findAll() {
        List<UserModel> userModels = userRepository.findAll();
        List<UserDto> usersDto = userModels.stream().map(x -> new UserDto(x)).collect(Collectors.toList());
        return usersDto;
    }

    @Override
    public UserDto findById(long id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário nao encontrado"));
        return new UserDto(user);
    }

    @Override
    public UserDto save(UserDto userDto) {
        if(existsByUsername(userDto.getUsername()) == true){
            throw new UsernameAlreadyTakenException("Esse usuário já existe");
        } else {
            UserModel user = new UserModel();
            BeanUtils.copyProperties(userDto, user);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            user = userRepository.save(user);
            return new UserDto(user);
        }
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        UserDto userRetrivied = findById(id);
        UserModel userModel = new UserModel(userRetrivied);

        BeanUtils.copyProperties(userDto, userModel);
        userModel.setId(userRetrivied.getId());
        userModel.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userModel = userRepository.save(userModel);

        return new UserDto(userModel);
    }

    @Override
    public void deleteById(long id) {
        UserDto user = findById(id);
        userRepository.delete(new UserModel(user));
    }

    @Override
    public UserDto findByUsername(String username) {
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário nao encontrado"));
        return new UserDto(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        boolean result = userRepository.existsByUsername(username);
        return result;
    }   
}
