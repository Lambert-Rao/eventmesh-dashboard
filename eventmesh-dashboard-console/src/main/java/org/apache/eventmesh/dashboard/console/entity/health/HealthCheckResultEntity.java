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

package org.apache.eventmesh.dashboard.console.entity.health;

import org.apache.eventmesh.dashboard.console.entity.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
@Schema(name = "HealthCheckResultEntity", description = "Health check result entity")
public class HealthCheckResultEntity extends BaseEntity {

    private static final long serialVersionUID = -7350585209577598040L;
    @Schema(name = "id", description = "primary key")
    private Long id;

    @Schema(description = "Dimension of Health Check;0:Unknown, 1:Cluster, 2:Runtime, 3:Topic, 4:Group", defaultValue = "0", allowableValues = {"0",
        "1", "2", "3", "4"})
    private Integer dimension;

    private String configName;

    private String resName;

    private Integer passed;

    public HealthCheckResultEntity() {
    }

}
