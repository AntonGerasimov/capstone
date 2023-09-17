package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AddressService {
    List<AddressDto> findAll();
    AddressDto findById(Long id);
    List<AddressDto> findAvailableForUser(UserDto userDto);
    List<AddressDto> findAvailableForAuthenticatedUser(Authentication authentication);
    AddressDto save(AddressDto addressDto, Authentication authentication);
    void update(Long addressId, AddressDto addressDto, Long userId);
    void delete(Long addressId);

}
