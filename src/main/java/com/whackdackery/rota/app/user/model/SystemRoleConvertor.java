package com.whackdackery.rota.app.user.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SystemRoleConvertor implements AttributeConverter<SystemRole.RoleType, String> {

    @Override
    public String convertToDatabaseColumn(SystemRole.RoleType roleType) {
        return roleType.getType();
    }

    @Override
    public SystemRole.RoleType convertToEntityAttribute(String type) {
        return Stream.of(SystemRole.RoleType.values())
                .filter(roleType -> roleType.getType().equals(type))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
