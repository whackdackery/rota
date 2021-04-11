package com.whackdackery.rota.app.user.model.dto;

import com.whackdackery.rota.app.common.model.GetDto;
import com.whackdackery.rota.app.user.model.SystemRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserGetDto extends GetDto {
    String username;
    String email;
    Set<SystemRole> roles;
}
