package com.cdg.cadernog.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cdg.cadernog.dtos.RoleDto;
import com.cdg.cadernog.enums.Cargos;
import com.cdg.cadernog.models.RoleModel;
import com.cdg.cadernog.repositories.RoleRepository;
import com.cdg.cadernog.services.impl.RoleServiceImpl;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void canFindAllRoles() {
        // when
        roleService.findAll();
        // then
        verify(roleRepository).findAll();
    }

    @Test
    void canfindRoleById() {
        //given
        RoleDto newRole = new RoleDto(1L, Cargos.ROLE_USER.name());
        given(roleRepository.findById(newRole.getId())).willReturn(Optional.of(new RoleModel(newRole)));

        // when
        roleService.findById(newRole.getId());

        // then
        verify(roleRepository, times(1)).findById(newRole.getId());
    }

    @Test
    void canSaveRole() {
        // given
        RoleDto newRole = new RoleDto(1L, Cargos.ROLE_USER.name());

        // when
        when(roleRepository.save(Mockito.any(RoleModel.class))).thenReturn(new RoleModel(newRole));
        roleService.save(newRole);

        // then
        ArgumentCaptor<RoleModel> roleCaptor = ArgumentCaptor.forClass(RoleModel.class);
        verify(roleRepository, times(1)).save(roleCaptor.capture());
        RoleModel capturedRole = roleCaptor.getValue();

        assertThat(newRole.getName().equals(capturedRole.getName()));
    }

    @Test
    void canUpdateRole() {
        // given
        RoleDto newRole = new RoleDto(1L, Cargos.ROLE_USER.name());
        RoleDto roleUpdated = new RoleDto(null, Cargos.ROLE_SUPER_ADMIN.name());
        given(roleRepository.findById(newRole.getId())).willReturn(Optional.of(new RoleModel(newRole)));

        // when
        when(roleRepository.save(Mockito.any(RoleModel.class))).thenReturn(new RoleModel(roleUpdated));
        roleService.update(newRole.getId(), roleUpdated);

        // then
        ArgumentCaptor<RoleModel> roleCaptor = ArgumentCaptor.forClass(RoleModel.class);
        verify(roleRepository, times(1)).save(roleCaptor.capture());
        RoleModel capturedRole = roleCaptor.getValue();

        verify(roleRepository, times(1)).save(Mockito.any(RoleModel.class));
        assertThat(newRole.getName()).isNotEqualTo(capturedRole.getName());
    }

    @Test
    void canDeleteRoleById() {
        //given
        RoleDto newRole = new RoleDto(1L, Cargos.ROLE_USER.name());

        given(roleRepository.findById(newRole.getId())).willReturn(Optional.of(new RoleModel(newRole)));

        // when
        roleService.deleteById(newRole.getId());

        // then
        verify(roleRepository, times(1)).delete(new RoleModel(newRole));
    }
}