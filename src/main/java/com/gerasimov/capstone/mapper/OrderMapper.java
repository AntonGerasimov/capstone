package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer", target = "customer")
    OrderDto toDto(Order order);

    @Mapping(source = "customer", target = "customer")
    Order toEntity(OrderDto orderDto);

}
