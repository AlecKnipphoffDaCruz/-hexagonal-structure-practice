package com.example.hexagonalstruct.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JpaPersistencePort persistencePort;

    public UserService(JpaPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    public User createUser(User user) {
        return persistencePort.save(user);
    }

    public User getUser(Long id) {
        return persistencePort.getById(id);
    }
}
