package com.example.fork.global.data.entity;

import com.example.fork.global.data.dto.TokenDto;
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
@Table(name = "tokens")
public class Token {

    @Id
    @Column(name = "token_id")
    String id;

    @Getter
    @Column(name = "token_is_valid")
    Boolean is_valid;

    public TokenDto toDto() {
        return TokenDto.builder()
                .id(id)
                .is_valid(is_valid)
                .build();
    }
}
