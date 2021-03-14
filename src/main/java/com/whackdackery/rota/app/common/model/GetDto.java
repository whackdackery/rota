package com.whackdackery.rota.app.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class GetDto {
    private Long id;
    private Instant created;
    private Instant updated;
}
