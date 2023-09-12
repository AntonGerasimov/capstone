package com.gerasimov.capstone.service.impl;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.Address;
import com.gerasimov.capstone.entity.Order;
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
    public AddressDto save(AddressDto addressDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUsername = authentication.getName();
        Optional<UserDto> user = userService.findByUsername(authenticatedUsername);
        if (user.isPresent()) {
            addressDto.setUser(user.get());
            addressRepository.save(addressMapper.toEntity(addressDto));
            return addressDto;
        }else{
            return null;
        }
    }

    @Override
    public void delete(Long addressId){
        Address address = addressRepository.findById(addressId).orElse(null);
        if (address != null){
            log.info("Delete address with id " + address.getId());
            address.setUser(null);
            List<Order> orders = orderService.findByDeliveryAddressId(addressId);
            orders.forEach(order -> orderService.delete(order.getId()));
            addressRepository.deleteById(addressId);
        }
    }

    @Override
    public List<AddressDto> findAllByUser(UserDto userDto){
        List<Address> addressesEntities = addressRepository.findByUser(userMapper.toEntity(userDto));
        return addressesEntities.stream()
                .map(addressMapper::toDto)
                .toList();
    }
}
