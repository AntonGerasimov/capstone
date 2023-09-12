package com.gerasimov.capstone.service;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> findAll();
    List<AddressDto> findAllByUser(UserDto userDto);
    AddressDto save(AddressDto addressDto);
    void delete(Long addressId);

}
