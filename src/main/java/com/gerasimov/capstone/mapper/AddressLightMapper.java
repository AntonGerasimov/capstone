package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.domain.AddressDtoLight;
import com.gerasimov.capstone.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressLightMapper {
    AddressLightMapper INSTANCE = Mappers.getMapper(AddressLightMapper.class);

    @Mapping(target = "userId", source = "user.id")
    AddressDtoLight toLight(AddressDto addressDto);

    @Mapping(target = "user.id", source = "userId")
    AddressDto toDto(AddressDtoLight addressDtoLight);
}
