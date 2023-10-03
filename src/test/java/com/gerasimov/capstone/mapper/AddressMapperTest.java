package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.entity.Address;
import com.gerasimov.capstone.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    public void setUp() {
        addressMapper = Mappers.getMapper(AddressMapper.class);
    }

    @Test
    void testToDtoWhenAddressEntityProvidedThenAddressDtoReturned() {

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setActive(true);

        Address address = new Address();
        address.setId(1L);
        address.setUser(user);
        address.setStreet("Main Street");
        address.setHouse("123");
        address.setApartment("1A");
        address.setActive(true);

        AddressDto addressDto = addressMapper.toDto(address);

        assertNotNull(addressDto);
        assertEquals(addressDto.getId(), address.getId());
        assertEquals(addressDto.getStreet(), address.getStreet());
        assertEquals(addressDto.getHouse(), address.getHouse());
        assertEquals(addressDto.getApartment(), address.getApartment());
        assertEquals(addressDto.isActive(), address.isActive());
    }

    @Test
    void testToDtoWhenNullAddressEntityProvidedThenNullAddressDtoReturned() {

        AddressDto addressDto = addressMapper.toDto(null);

        assertNull(addressDto);
    }

    @Test
    void testToDtoWhenAddressEntityWithoutUserProvidedThenAddressDtoWithNullUserReturned() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Main Street");
        address.setHouse("123");
        address.setApartment("1A");
        address.setActive(true);

        AddressDto addressDto = addressMapper.toDto(address);

        assertNotNull(addressDto);
        assertNull(addressDto.getUser());
    }
}