package com.example.fork.global.data.entity;

import com.example.fork.global.auth.Permission;
import com.example.fork.global.auth.Type;
import com.example.fork.global.data.dto.UserDto;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    String id;

    @Getter
    @Column(name = "user_password")
    String password;

    @Getter
    @Column(name = "user_email")
    String email;

    @Getter
    @Column(name = "user_type")
    Type type;

    @Getter
    @Column(name = "user_permission")
    Permission permission;

    @Getter
    @Column(name = "user_attributes")
    String attributes;

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .password(password)
                .email(email)
                .type(type)
                .permission(permission)
                .attributes(attributes)
                .build();
    }
}
