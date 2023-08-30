package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.mappers.AddressMapper;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;
    @Autowired
    public AddressServiceImpl(
            AddressRepository addressRepository,
            UserRepository userRepository,
            AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<AddressDto> getAllAddresses(){
        List<Address> addressEntities = addressRepository.findAll();
        List<AddressDto> addresses = addressEntities.stream()
                .map(addressMapper::mapAddressEntityToDomain)
                .collect(Collectors.toList());
        return addresses;
    }
}
