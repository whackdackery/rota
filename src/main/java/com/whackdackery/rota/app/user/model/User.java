package com.whackdackery.rota.app.user.model;

import com.whackdackery.rota.app.common.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

}
