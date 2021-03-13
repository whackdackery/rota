package com.whackdackery.rota.app.common.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant created;
    private Instant updated;
}
