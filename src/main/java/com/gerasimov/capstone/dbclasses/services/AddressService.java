package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.domain.UserDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> findAll();
    List<AddressDto> findAllByUser(UserDto userDto);
    AddressDto save(AddressDto addressDto);
    void delete(Long addressId);

}
