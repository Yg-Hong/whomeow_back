package com.whomeow.whomeow.mapper;

import com.whomeow.whomeow.dto.UserDTO;
import com.whomeow.whomeow.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity userDtoToUserEntity(UserDTO userDto);
    UserDTO userEntityToUserDto(UserEntity userEntity);
}
