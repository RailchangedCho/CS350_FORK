package com.example.fork.global.data.dto;

import com.example.fork.global.auth.Role;
import com.example.fork.global.data.entity.Token;
import com.example.fork.global.data.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class UserDto {

    private String id;
    private String name;
    private Role role;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .role(role)
                .build();
    }
}
