package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Role;
import com.gerasimov.capstone.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Test
    void testToDtoWhenGivenValidUserEntityThenMapToUserDto() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setRole(new Role(1L, "ROLE_common"));
        user.setActive(true);

        UserDto userDto = userMapper.toDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getRole(), userDto.getRole());
        assertEquals(user.isActive(), userDto.isActive());
    }

    @Test
    void testToDtoWhenGivenNullUserEntityThenReturnNull() {
        UserDto userDto = userMapper.toDto(null);
        assertNull(userDto);
    }

    @Test
    void testToEntityWhenGivenValidUserDtoThenMapToUserEntity() {
        UserDto userDto = new UserDto();

        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setUsername("johndoe");
        userDto.setPassword("password");
        userDto.setRole(new Role(1L, "ROLE_common"));
        userDto.setActive(true);

        User user = userMapper.toEntity(userDto);

        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getRole(), user.getRole());
        assertEquals(userDto.isActive(), user.isActive());
    }

    @Test
    void testToEntityWhenGivenNullUserDtoThenReturnNull() {
        User user = userMapper.toEntity(null);
        assertNull(user);
    }
}