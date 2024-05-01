package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Token;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class TokenDto {

    String id;
    Boolean isValid;

    public Token toEntity() {
        return Token.builder()
                .id(id)
                .isValid(isValid)
                .build();
    }
}
