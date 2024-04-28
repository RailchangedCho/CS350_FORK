package com.example.fork.global.data.entity;

import com.example.fork.global.auth.Role;
import com.example.fork.global.data.dto.UserDto;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Column(name = "user_name")
    String name;

    @Getter
    @Column(name = "user_role")
    Role role;

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .name(name)
                .role(role)
                .build();
    }


}
