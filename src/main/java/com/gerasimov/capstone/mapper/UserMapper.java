package com.gerasimov.capstone.mapper;


import com.gerasimov.capstone.domain.UserDto;
import com.gerasimov.capstone.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

}
