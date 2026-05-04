package com.example.hexagonalstruct.user.zdto;

public record ResponseUserDto(
        Long id,
        String name,
        Long age,
        String description
) {
}
