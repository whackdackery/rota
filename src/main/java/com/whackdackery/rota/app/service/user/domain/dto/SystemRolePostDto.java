package com.whackdackery.rota.app.service.user.domain.dto;

import com.whackdackery.rota.app.common.model.PostDto;
import com.whackdackery.rota.app.service.user.domain.SystemRole;

import javax.validation.constraints.NotBlank;

public class SystemRolePostDto extends PostDto {

    @NotBlank
    private SystemRole.RoleType roleType;
    @NotBlank
    private String name;
}
