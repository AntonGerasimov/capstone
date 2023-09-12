package com.gerasimov.capstone.mapper;

import com.gerasimov.capstone.domain.AddressDto;
import com.gerasimov.capstone.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "user", source = "address.user")
    AddressDto toDto(Address address);

    @Mapping(target = "user", source = "user")
    Address toEntity(AddressDto addressDto);


}

