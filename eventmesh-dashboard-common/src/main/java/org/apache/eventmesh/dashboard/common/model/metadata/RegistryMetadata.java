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

package org.apache.eventmesh.dashboard.common.model.metadata;

import org.apache.eventmesh.dashboard.common.enums.DataStatus;
import org.apache.eventmesh.dashboard.common.enums.metadata.MetadataServiceTypeEnum;

import lombok.Data;

@Data
public class RegistryMetadata extends MetadataConfig {

    {
        this.setServiceTypeEnums(MetadataServiceTypeEnum.META_NACOS);
    }

    private String clusterName;

    private String name;

    private String type;

    private String version;

    private String host;

    private Integer port;

    private String role;

    private String username;

    private String params;

    /**
     * 0: not active, 1: active
     *
     * @see DataStatus
     */
    private Integer status;
}
