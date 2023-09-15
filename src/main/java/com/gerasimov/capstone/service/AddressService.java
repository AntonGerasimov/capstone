package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AddressService {
    List<AddressDto> findAll();
    List<AddressDto> findAllByUser(UserDto userDto);
    List<AddressDto> findAllForAuthenticatedUser(Authentication authentication);
    AddressDto save(AddressDto addressDto);
    void delete(Long addressId);

}
