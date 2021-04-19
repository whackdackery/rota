package com.whackdackery.rota.app.service.user.domain.dto;

import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.service.user.domain.SystemRole;

public class SystemRoleGetDto extends GetDto {
    private SystemRole.RoleType roleType;
    private String name;
}
