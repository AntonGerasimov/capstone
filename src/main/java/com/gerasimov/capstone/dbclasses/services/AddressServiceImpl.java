package com.gerasimov.capstone.dbclasses.services;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.mappers.AddressMapper;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    AddressRepository addressRepository;
    AddressMapper addressMapper;

    @Override
    public List<AddressDto> getAllAddresses(){
        List<Address> addressEntities = addressRepository.findAll();
        List<AddressDto> addresses = addressEntities.stream()
                .map(addressMapper::addressToAddressDto)
                .collect(Collectors.toList());
        return addresses;
    }
}
