package com.example.hexagonalstruct.user.zout;


import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//anotations from database
public class UserEntity {

    //anotations from hibernate id etc...
    Long id;
    String name;
    Long age;
    String description;

}
