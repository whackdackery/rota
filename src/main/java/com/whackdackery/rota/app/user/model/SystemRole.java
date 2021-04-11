package com.whackdackery.rota.app.user.model;

import com.whackdackery.rota.app.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_roles")
@Data
public class SystemRole extends BaseEntity {
    public enum RoleType {
        SUPER_ADMIN("SUPER_ADMIN"),
        ADMIN("ADMIN"),
        EDITOR("EDITOR"),
        SCHEDULER("SCHEDULER"),
        END_USER("END_USER");

        private String type;

        RoleType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    @Column(unique = true)
    private RoleType roleType;
    private String name;
}
