/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.eventmesh.dashboard.console.dto.health;

import org.apache.eventmesh.dashboard.console.entity.health.HealthCheckResultEntity;
import org.apache.eventmesh.dashboard.console.enums.health.HealthCheckType;

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

    //这里能不能直接包含一个health entity
    HealthCheckResultEntity result;

    private HealthCheckType type;
}
