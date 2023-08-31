package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.mappers.AddressMapper;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    AddressRepository addressRepository;
    AddressMapper addressMapper;


    @Override
    public List<AddressDto> getAllAddresses(){
        List<Address> addressEntities = addressRepository.findAll();
        List<AddressDto> addresses = null;
//        List<AddressDto> addresses = addressEntities.stream()
//                .map(addressMapper::mapAddressEntityToDomain)
//                .collect(Collectors.toList());
        return addresses;
    }
}
