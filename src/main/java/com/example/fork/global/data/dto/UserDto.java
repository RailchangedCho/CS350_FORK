package com.example.fork.global.data.dto;

import com.example.fork.global.auth.Permission;
import com.example.fork.global.auth.Type;
import com.example.fork.global.data.entity.User;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class UserDto {

    private String id;
    private String name;
    private String password;
    private String email;
    private String deviceId;
    private Boolean status;
    private String defaultLanguage;
    private LocalDateTime registerDate;
    private Type type;
    private Boolean isAuthenticated;
    private Permission permission;
    private Integer attributes;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .password(password)
                .email(email)
                .deviceId(deviceId)
                .status(status)
                .defaultLanguage(defaultLanguage)
                .registerDate(registerDate)
                .type(type)
                .isAuthenticated(isAuthenticated)
                .permission(permission)
                .attributes(attributes)
                .build();
    }
}
