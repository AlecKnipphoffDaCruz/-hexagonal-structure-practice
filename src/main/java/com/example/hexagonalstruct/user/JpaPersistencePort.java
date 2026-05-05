package com.example.hexagonalstruct.user;

import java.util.Optional;

public interface JpaPersistencePort {

    User save(User user);

    Optional<User> getById(Long id);

    void deleteById(Long id);
}
