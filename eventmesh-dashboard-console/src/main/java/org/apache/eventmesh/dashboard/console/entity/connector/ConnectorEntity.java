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

package org.apache.eventmesh.dashboard.console.entity.connector;

import org.apache.eventmesh.dashboard.console.entity.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;

public class ConnectorEntity extends BaseEntity {

    private static final long serialVersionUID = -8226303660232951326L;

    @Schema(name = "id", description = "primary key")
    private Long id;

    private String name;

    private String className;

    private String type;

    private String status;

    private Integer podState;

    /**
     * csv format config id list.<br>
     * Example value: 1,2,7<br>
     * This field is updated when the configuration is modified via the web API, but is not used during the configuration retrieval process.
     */
    private String configIds;
}
