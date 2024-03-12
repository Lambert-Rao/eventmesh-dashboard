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

package org.apache.eventmesh.dashboard.console.function.metadata.data;

import org.apache.eventmesh.dashboard.console.enums.StatusEnum;
import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.MetadataServiceTypeEnums;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClientMetadata extends MetadataOperationConfig {


    {
        this.setServiceTypeEnums(MetadataServiceTypeEnums.CLIENT);
    }

    private String name;

    private String platform;

    /**
     * programing language of client
     */
    @Schema(name = "language", example = "java")
    private String language;

    /**
     * process id
     */
    @Schema(name = "pid", description = "process id")
    private Long pid;

    private String host;

    private Integer port;

    /**
     * protocol used to connect to runtime.
     */
    @Schema(name = "protocol", example = "http", allowableValues = {"http", "grpc", "tcp"})
    private String protocol;

}
