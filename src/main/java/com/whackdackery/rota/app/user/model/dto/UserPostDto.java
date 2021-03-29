package com.whackdackery.rota.app.user.model.dto;

import com.whackdackery.rota.app.common.model.PostDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Value
@Builder
public class UserPostDto extends PostDto {

    @NotBlank(message = "Username cannot be blank")
    String username;
    @NotBlank(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    String email;
    @NotBlank(message = "Password should not be blank")
    String password;

}
