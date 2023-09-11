package com.gerasimov.capstone.dbclasses.services.impl;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.domain.UserDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.entity.Order;
import com.gerasimov.capstone.dbclasses.entity.User;
import com.gerasimov.capstone.dbclasses.mappers.AddressMapper;
import com.gerasimov.capstone.dbclasses.mappers.UserMapper;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import com.gerasimov.capstone.dbclasses.repositories.UserRepository;
import com.gerasimov.capstone.dbclasses.services.AddressService;
import com.gerasimov.capstone.dbclasses.services.OrderService;
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
    private OrderRepository orderRepository;
    private UserRepository userRepository;
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
        Optional<User> user = userRepository.findByUsername(authenticatedUsername);
        if (user.isPresent()) {
            addressDto.setUser(userMapper.toDto(user.get()));
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
            List<Order> orders = orderRepository.findByDeliveryAddressId(addressId);
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
