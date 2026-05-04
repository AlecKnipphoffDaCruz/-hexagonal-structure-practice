package com.example.hexagonalstruct.user.zin;

import com.example.hexagonalstruct.user.User;
import com.example.hexagonalstruct.user.zout.UserMapper;
import com.example.hexagonalstruct.user.UserService;
import com.example.hexagonalstruct.user.zdto.CreateUserDto;
import com.example.hexagonalstruct.user.zdto.ResponseUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseUserDto> create(@RequestBody CreateUserDto dto) {
        User user = userMapper.dtoToDomain(dto);
        userService.createUser(user);
        return ResponseEntity.ok(userMapper.domainToDto(user));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseUserDto> get(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(userMapper.domainToDto(user));
    }
}
