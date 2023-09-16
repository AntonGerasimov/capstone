package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Address;
import com.gerasimov.capstone.exception.RestaurantException;
import com.gerasimov.capstone.mapper.AddressMapper;
import com.gerasimov.capstone.mapper.UserMapper;
import com.gerasimov.capstone.repository.AddressRepository;
import com.gerasimov.capstone.service.AddressService;
import com.gerasimov.capstone.service.OrderService;
import com.gerasimov.capstone.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;
    private UserMapper userMapper;
    private UserService userService;
    private OrderService orderService;


    @Override
    public List<AddressDto> findAll(){
        List<Address> addressesEntities = addressRepository.findAll();
        return addressesEntities.stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    public AddressDto findById(Long id){
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()){
            return addressMapper.toDto(addressOptional.get());
        }else{
            throw new RestaurantException(String.format("Can't find address with id %s", id));
        }
    }

    @Override
    public AddressDto save(AddressDto addressDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        Optional<UserDto> user = userService.findByUsername(authenticatedUsername);
        if (user.isPresent()) {
            addressDto.setUser(user.get());
            addressDto.setActive(true);
            addressRepository.save(addressMapper.toEntity(addressDto));
            return addressDto;
        }else{
            throw new RestaurantException("Can't find user in database");
        }
    }

    @Override
    @Transactional
    public AddressDto update(Long addressId, AddressDto addressDto, Long userId){
        log.info(String.format("Updating address with id %d", addressId));
        log.info(addressDto.toString());
        addressDto.setId(addressId);
        UserDto userDto = userService.findById(userId).orElse(null);
        addressDto.setUser(userDto);
        addressDto.setActive(true);
        addressRepository.save(addressMapper.toEntity(addressDto));
        return null;
    }

    @Override
    @Transactional
    public void delete(Long addressId){
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isPresent()){
            Address address = addressOptional.get();
            address.setActive(false);
            log.info(String.format("Delete address with id %s", address.getId()));
            log.info("address setActive is " + address.isActive());
        }else{
            throw new RestaurantException(String.format("Can't find address with id %d", addressId));
        }
    }

    @Override
    public List<AddressDto> findAllByUser(UserDto userDto){
        List<Address> addressesEntities = addressRepository.findByUser(userMapper.toEntity(userDto));
        return addressesEntities.stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    public List<AddressDto> findAllForAuthenticatedUser(Authentication authentication){
        Optional<UserDto> authenticatedUser = userService.findByUsername(authentication.getName());
        if (authenticatedUser.isPresent()){
            return findAllByUser(authenticatedUser.get());
        }else{
            throw new RestaurantException("Can't find authenticated user");
        }
    }
}
