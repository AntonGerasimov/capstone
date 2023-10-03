package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.AddressDtoLight;
import com.gerasimov.capstone.domain.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressLightMapperTest {

    private AddressLightMapper addressLightMapper;

    @BeforeEach
    public void setUp() {
        addressLightMapper = AddressLightMapper.INSTANCE;
    }

    @Test
    void testToLightWhenAddressDtoNotNullThenReturnAddressDtoLight() {
        // Arrange
        UserDto userDto = new UserDto(1L, "John", "Doe", "john.doe@example.com", "johndoe", "password", null, true);
        AddressDto addressDto = new AddressDto(1L, userDto, "Main Street", "123", "456", true);

        AddressDtoLight addressDtoLight = addressLightMapper.toLight(addressDto);

        assertNotNull(addressDtoLight);
        assertEquals(addressDtoLight.getId(), addressDto.getId());
        assertEquals(addressDtoLight.getUserId(), userDto.getId());
        assertEquals(addressDtoLight.getStreet(), addressDto.getStreet());
        assertEquals(addressDtoLight.getHouse(), addressDto.getHouse());
        assertEquals(addressDtoLight.getApartment(), addressDto.getApartment());
        assertEquals(addressDtoLight.isActive(), addressDto.isActive());
    }

    @Test
    void testToLightWhenAddressDtoNullThenReturnNull() {
        // Act
        AddressDtoLight addressDtoLight = addressLightMapper.toLight(null);

        // Assert
        assertNull(addressDtoLight);
    }
}