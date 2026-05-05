package com.example.hexagonalstruct.user.zout;

import com.example.hexagonalstruct.user.JpaPersistencePort;
import com.example.hexagonalstruct.user.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
    public User getById(Long id) {
        UserEntity entity = database.get(id);
        return new User(entity.getId(), entity.getName(), entity.getAge(), entity.getDescription());
    }

    @Override
    public void deleteById(Long id){
        //delete
        return;
    }
}
