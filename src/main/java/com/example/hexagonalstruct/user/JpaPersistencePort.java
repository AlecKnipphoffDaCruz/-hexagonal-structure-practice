package com.example.hexagonalstruct.user;

public interface JpaPersistencePort {

    User save(User user);

    User getById(Long id);

    void deleteById(Long id);
}
