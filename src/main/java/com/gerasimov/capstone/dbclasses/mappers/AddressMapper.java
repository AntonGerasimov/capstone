package com.gerasimov.capstone.dbclasses.mappers;

import com.gerasimov.capstone.dbclasses.domain.Address;
import com.gerasimov.capstone.dbclasses.domain.FullAddress;
import com.gerasimov.capstone.dbclasses.domain.User;
import com.gerasimov.capstone.dbclasses.entity.AddressEntity;
import com.gerasimov.capstone.dbclasses.entity.UserEntity;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.RoleRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.dbclasses.services.UserService;
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

    public Address mapAddressEntityToDomain(AddressEntity addressEntity) {
        Long id = addressEntity.getAddressId();
        Long userId = addressEntity.getUserId();
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        User user = userMapper.mapUserEntityToDomain(userEntity);
        String street = addressEntity.getStreet();
        String house = addressEntity.getHouse();
        String apartment = addressEntity.getApartment();
        return new Address(id, user, new FullAddress(street, house, apartment));
    }

}

