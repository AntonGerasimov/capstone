package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Address;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.AddressMapper;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.AddressRepository;
import com.gerasimov.capstone.service.AddressService;
import com.gerasimov.capstone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;
    private UserMapper userMapper;
    private UserService userService;


    @Override
    public List<AddressDto> findAll(){
        List<Address> addressesEntities = addressRepository.findAll();
        return addressesEntities.stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    public AddressDto findById(Long id){
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RestaurantException(String.format("Can't find address with id %d", id)));
        return addressMapper.toDto(address);
    }

    @Override
    public AddressDto save(AddressDto addressDto, Authentication authentication){
        UserDto userDto = userService.findAuthenticatedUser(authentication);
        addressDto.setUser(userDto);
        addressDto.setActive(true);
        addressRepository.save(addressMapper.toEntity(addressDto));
        return addressDto;
    }

    @Override
    @Transactional
    public void update(Long addressId, AddressDto addressDto, Long userId){
        log.info(String.format("Updating address with id %d", addressId));
        log.info(addressDto.toString());
        addressDto.setId(addressId);
        UserDto userDto = userService.findById(userId);
        addressDto.setUser(userDto);
        addressRepository.save(addressMapper.toEntity(addressDto));
    }

    @Override
    @Transactional
    public void delete(Long addressId){
        AddressDto addressDto = findById(addressId);
        addressDto.setActive(false);
        addressRepository.save(addressMapper.toEntity(addressDto));
        log.info(String.format("Delete address with id %s", addressId));
    }

    @Override
    public List<AddressDto> findAllByUser(UserDto userDto){
        List<Address> addressesEntities = addressRepository.findByUser(userMapper.toEntity(userDto));
        return addressesEntities.stream()
                .filter(Address::isActive)
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    public List<AddressDto> findAllForAuthenticatedUser(Authentication authentication){
        UserDto authenticatedUser = userService.findByUsername(authentication.getName());
        return findAllByUser(authenticatedUser);
    }
}
