package com.whackdackery.rota.app.user.model.dto;

import com.whackdackery.rota.app.common.model.PostDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPostDto extends PostDto {
    String username;
    String email;
    String password;
}
