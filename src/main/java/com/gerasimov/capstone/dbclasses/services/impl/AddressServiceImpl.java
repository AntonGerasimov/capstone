package com.gerasimov.capstone.dbclasses.services.impl;

import com.gerasimov.capstone.dbclasses.domain.AddressDto;
import com.gerasimov.capstone.dbclasses.entity.Address;
import com.gerasimov.capstone.dbclasses.entity.Order;
import com.gerasimov.capstone.dbclasses.mappers.AddressMapper;
import com.gerasimov.capstone.dbclasses.repositories.AddressRepository;
import com.gerasimov.capstone.dbclasses.repositories.OrderRepository;
import com.gerasimov.capstone.dbclasses.services.AddressService;
import com.gerasimov.capstone.dbclasses.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;
    private OrderRepository orderRepository;
    private OrderService orderService;

    @Override
    public List<AddressDto> findAll(){
        List<Address> addressEntities = addressRepository.findAll();
        return addressEntities.stream()
                .map(addressMapper::toDto)
                .toList();
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
}
