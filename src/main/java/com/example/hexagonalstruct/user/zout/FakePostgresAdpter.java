package com.example.hexagonalstruct.user.zout;

import com.example.hexagonalstruct.user.JpaPersistencePort;
import com.example.hexagonalstruct.user.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class FakePostgresAdpter implements JpaPersistencePort {

    private final Map<Long, UserEntity> database = new HashMap<>();

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user.id(), user.name(), user.age(), user.description());
        database.put(entity.getId(), entity);
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(database.get(id))
                .map(e -> new User(e.getId(), e.getName(), e.getAge(), e.getDescription()));
    }

    @Override
    public void deleteById(Long id){
        //delete
        return;
    }
}
