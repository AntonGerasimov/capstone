package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAllAddresses();
}
