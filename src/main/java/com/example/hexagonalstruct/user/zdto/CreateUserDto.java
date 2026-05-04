package com.example.hexagonalstruct.user.zdto;

public record CreateUserDto(
        String name,
        Long age,
        String description
) {
}
