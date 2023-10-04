package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.service.impl.AddressServiceImpl;
import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Address;
import com.gerasimov.capstone.entity.User;
import com.gerasimov.capstone.mapper.AddressMapper;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.AddressRepository;
import com.gerasimov.capstone.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private AddressServiceImpl addressService;

    private UserDto userDto;
    private User user;
    private AddressDto addressDto;
    private Address address;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
        userDto.setId(1L);

        user = new User();
        user.setId(1L);

        addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setUser(userDto);
        addressDto.setActive(true);

        address = new Address();
        address.setId(1L);
        address.setUser(user);
        address.setActive(true);
    }

    @Test
    void testFindAvailableForUserWhenValidUserDtoThenReturnListOfAddressDto() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(addressRepository.findByUser(user)).thenReturn(List.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressDto);

        List<AddressDto> result = addressService.findAvailableForUser(userDto);

        assertEquals(1, result.size());
        assertEquals(addressDto, result.get(0));
    }

    @Test
    void testFindAvailableForUserWhenUserDtoWithNoAddressesThenReturnEmptyList() {
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(addressRepository.findByUser(user)).thenReturn(Collections.emptyList());

        List<AddressDto> result = addressService.findAvailableForUser(userDto);

        assertEquals(0, result.size());
    }

    @Test
    void testFindAvailableForUserWhenUserDtoWithInactiveAddressesThenReturnEmptyList() {
        address.setActive(false);
        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(addressRepository.findByUser(user)).thenReturn(List.of(address));

        List<AddressDto> result = addressService.findAvailableForUser(userDto);

        assertEquals(0, result.size());
    }

    @Test
    void testSaveWhenValidAddressDtoThenReturnSameAddressDto() {
        when(addressMapper.toEntity(addressDto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(userService.findAuthenticatedUser()).thenReturn(userDto);

        AddressDto result = addressService.save(addressDto);

        assertEquals(addressDto, result);
    }

    @Test
    void testSaveWhenAddressMapperThrowsExceptionThenThrowException() {
        when(addressMapper.toEntity(addressDto)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> addressService.save(addressDto));
    }
}