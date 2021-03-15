package com.whackdackery.rota.app.common.model;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class GetDto {
    private Long id;
    private Instant created;
    private Instant updated;
}
