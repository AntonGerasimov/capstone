package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.AddressDtoLight;
import com.gerasimov.capstone.domain.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressLightMapperTest {

    private final AddressLightMapper addressLightMapper = AddressLightMapper.INSTANCE;

    @Test
    void testToLightWhenAddressDtoNotNullThenReturnAddressDtoLight() {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setUsername("johndoe");
        userDto.setPassword("password");
        userDto.setActive(true);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setUser(userDto);
        addressDto.setStreet("Main Street");
        addressDto.setHouse("123");
        addressDto.setApartment("456");
        addressDto.setActive(true);


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

        AddressDtoLight addressDtoLight = addressLightMapper.toLight(null);

        assertNull(addressDtoLight);
    }

    @Test
    void testToDtoWhenAddressDtoLightNotNullThenReturnAddressDto() {

        AddressDtoLight addressDtoLight = new AddressDtoLight();
        addressDtoLight.setId(1L);
        addressDtoLight.setUserId(1L);
        addressDtoLight.setStreet("Main Street");
        addressDtoLight.setHouse("123");
        addressDtoLight.setApartment("456");
        addressDtoLight.setActive(true);


        AddressDto addressDto = addressLightMapper.toDto(addressDtoLight);

        assertNotNull(addressDto);
        assertEquals(addressDto.getId(), addressDtoLight.getId());
        assertEquals(addressDto.getUser().getId(), addressDtoLight.getUserId());
        assertEquals(addressDto.getStreet(), addressDtoLight.getStreet());
        assertEquals(addressDto.getHouse(), addressDtoLight.getHouse());
        assertEquals(addressDto.getApartment(), addressDtoLight.getApartment());
        assertEquals(addressDto.isActive(), addressDtoLight.isActive());
    }

    @Test
    void testToDtoWhenAddressDtoLightNullThenReturnNull() {

        AddressDto addressDto = addressLightMapper.toDto(null);

        assertNull(addressDto);
    }
}