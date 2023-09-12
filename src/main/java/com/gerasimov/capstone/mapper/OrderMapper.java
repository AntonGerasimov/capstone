package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.OrderDto;
import com.gerasimov.capstone.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);
}
