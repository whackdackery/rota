package com.whackdackery.rota.app.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.Instant;

@NoArgsConstructor
@Data
public class UserGetDto {
    Long id;
    String username;
    String email;
    Instant created;
    Instant updated;
}
