package com.example.fork.global.data.dto;

import com.example.fork.global.auth.Permission;
import com.example.fork.global.auth.Type;
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
    private String password;
    private String email;
    private Type type;
    private Boolean isAuthenticated;
    private Permission permission;
    private String attributes;

    public User toEntity() {
        return User.builder()
                .id(id)
                .password(password)
                .email(email)
                .type(type)
                .isAuthenticated(isAuthenticated)
                .permission(permission)
                .attributes(attributes)
                .build();
    }
}
