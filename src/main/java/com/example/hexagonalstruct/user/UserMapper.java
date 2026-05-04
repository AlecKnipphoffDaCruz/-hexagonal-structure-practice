package com.example.hexagonalstruct.user;

import com.example.hexagonalstruct.user.zdto.CreateUserDto;
import com.example.hexagonalstruct.user.zdto.ResponseUserDto;
import com.example.hexagonalstruct.user.zout.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User dtoToDomain(CreateUserDto dto) {
        return new User(null, dto.name(), dto.age(), dto.description());
    }

    public User entityToDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getAge(), entity.getDescription());
    }

    public UserEntity domainToEntity(User user) {
        return new UserEntity(user.id(), user.name(), user.age(), user.description());
    }

    public ResponseUserDto domainToDto(User user) {
        return new ResponseUserDto(user.id(), user.name(), user.age(), user.description());
    }
}
