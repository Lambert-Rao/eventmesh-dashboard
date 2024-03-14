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

package org.apache.eventmesh.dashboard.console.function.metadata.util;

import org.apache.eventmesh.dashboard.console.entity.meta.MetaEntity;
import org.apache.eventmesh.dashboard.console.enums.StatusEnum;
import org.apache.eventmesh.dashboard.console.function.cluster.ClusterCache;
import org.apache.eventmesh.dashboard.console.function.metadata.data.CenterMetadata;

public class CenterDataConverter implements Converter<CenterMetadata, MetaEntity> {

    @Override
    public MetaEntity toEntity(CenterMetadata source) {
        MetaEntity metaEntity = new MetaEntity();
        metaEntity.setHost(source.getHost());
        metaEntity.setPort(source.getPort());
        metaEntity.setClusterId(ClusterCache.getCluster(source.getRegistryAddress()).getId());
        metaEntity.setName(source.getName());
        metaEntity.setVersion(source.getVersion());
        metaEntity.setParams(source.getParams());
        metaEntity.setRole(source.getRole());
        metaEntity.setStatusEnum(StatusEnum.ACTIVE);
        metaEntity.setType(source.getType());
        metaEntity.setUsername(source.getUsername());

        return metaEntity;
    }

    @Override
    public CenterMetadata toMetadata(MetaEntity source) {
        return null;
    }
}
