package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.AddressDtoLight;
import com.gerasimov.capstone.domain.UserDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AddressService {
    List<AddressDto> findAll();
    AddressDto findById(Long id);
    AddressDtoLight findLightById(Long addressId);
    List<AddressDto> findAvailableForUser(UserDto userDto);
    List<AddressDto> findAvailableForAuthenticatedUser(Authentication authentication);
    AddressDto save(AddressDto addressDto);
    void update(AddressDtoLight addressDtoLight);
    void delete(Long addressId);

}
