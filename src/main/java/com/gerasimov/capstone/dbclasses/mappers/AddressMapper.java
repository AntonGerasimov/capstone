package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.domain.FullAddress;
import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public AddressMapper(AddressRepository addressRepository, UserRepository userRepository, UserMapper userMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public AddressDto mapAddressEntityToDomain(Address addressEntity) {
        Long id = addressEntity.getAddressId();
        Long userId = addressEntity.getUserId();
        User userEntity = userRepository.findById(userId).orElse(null);
        UserDto user = userMapper.mapUserEntityToDomain(userEntity);
        String street = addressEntity.getStreet();
        String house = addressEntity.getHouse();
        String apartment = addressEntity.getApartment();
        return new AddressDto(id, user, new FullAddress(street, house, apartment));
    }

}

