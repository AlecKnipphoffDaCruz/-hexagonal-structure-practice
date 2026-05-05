package com.example.hexagonalstruct.user;

import com.example.hexagonalstruct.user.zdto.CreateUserDto;
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
        return persistencePort.getById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public User updateUser(CreateUserDto dto, Long id){
       User oldDataUser = persistencePort.getById(id)
               .orElseThrow(() -> new RuntimeException("User not found: " + id));
       User newUser = new User(oldDataUser.id(), dto.name(), dto.age(), dto.description());
       return persistencePort.save(newUser);
    }
}
