package org.apache.eventmesh.dashboard.console.dto.health;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LastHealthCheckResponse {

    private String instanceName;

    @Schema(description = "state of a health check, 0: failed, 1: passed, 2: doing check, 3: out of time")
    private Integer healthState;

    private String resultDesc;

    private Timestamp updateTime;
}
