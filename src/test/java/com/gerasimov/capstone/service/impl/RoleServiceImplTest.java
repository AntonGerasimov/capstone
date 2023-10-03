package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role1;
    private Role role2;

    @BeforeEach
    public void setUp() {
        role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_common");

        role2 = new Role();
        role2.setId(2L);
        role2.setName("ROLE_manager");
    }

    @Test
    void testFindAllWhenRolesExistThenReturnListOfRoles() {

        List<Role> expectedRoles = Arrays.asList(role1, role2);
        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<Role> actualRoles = roleService.findAll();

        assertNotNull(actualRoles, "The returned list of roles should not be null");
        assertEquals(expectedRoles.size(), actualRoles.size(), "The returned list of roles should have the same size as the expected list");
    }

    @Test
    void testFindAllWhenNoRolesThenThrowException() {

        when(roleRepository.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RestaurantException.class, () -> roleService.findAll());
        assertEquals("No roles found in the database", exception.getMessage());
    }

    @Test
    void testFindByNameWhenRoleExistsThenReturnRole() {

        String roleName = "ROLE_common";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role1));

        Role actualRole = roleService.findByName(roleName);

        assertNotNull(actualRole, "The returned role should not be null");
        assertEquals(role1, actualRole, "The returned role should be the same as the expected role");
    }

    @Test
    void testFindByNameWhenRoleDoesNotExistThenThrowException() {

        String roleName = "ROLE_NON_EXISTENT";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RestaurantException.class, () -> roleService.findByName(roleName));
        assertEquals(String.format("Can't find role with name %s", roleName), exception.getMessage());
    }
}