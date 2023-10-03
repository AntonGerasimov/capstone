package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Address;
import com.gerasimov.capstone.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private final AddressMapper addressMapper = AddressMapper.INSTANCE;

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

    @Test
    void testToEntityWhenValidAddressDtoProvidedThenAddressEntityReturned() {
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
        addressDto.setApartment("1A");
        addressDto.setActive(true);

        Address address = addressMapper.toEntity(addressDto);

        assertNotNull(address);
        assertEquals(addressDto.getId(), address.getId());
        assertEquals(addressDto.getStreet(), address.getStreet());
        assertEquals(addressDto.getHouse(), address.getHouse());
        assertEquals(addressDto.getApartment(), address.getApartment());
        assertEquals(addressDto.isActive(), address.isActive());
    }

    @Test
    void testToEntityWhenNullAddressDtoProvidedThenNullReturned() {
        Address address = addressMapper.toEntity(null);

        assertNull(address);
    }

    @Test
    void testToEntityWhenAddressDtoWithoutUserProvidedThenAddressEntityWithNullUserReturned() {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setStreet("Main Street");
        addressDto.setHouse("123");
        addressDto.setApartment("1A");
        addressDto.setActive(true);

        Address address = addressMapper.toEntity(addressDto);

        assertNotNull(address);
        assertNull(address.getUser());
    }

}