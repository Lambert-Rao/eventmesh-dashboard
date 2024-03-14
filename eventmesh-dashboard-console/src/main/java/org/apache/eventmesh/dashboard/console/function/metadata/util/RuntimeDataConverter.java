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
import org.apache.eventmesh.dashboard.console.entity.runtime.RuntimeEntity;
import org.apache.eventmesh.dashboard.console.enums.StatusEnum;
import org.apache.eventmesh.dashboard.console.function.cluster.ClusterCache;
import org.apache.eventmesh.dashboard.console.function.metadata.data.CenterMetadata;
import org.apache.eventmesh.dashboard.console.function.metadata.data.RuntimeMetadata;

public class RuntimeDataConverter implements Converter<RuntimeMetadata, RuntimeEntity> {

    @Override
    public RuntimeEntity toEntity(RuntimeMetadata source) {
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setHost(source.getHost());
        runtimeEntity.setPort(source.getPort());
        runtimeEntity.setClusterId(ClusterCache.getCluster(source.getRegistryAddress()).getId());
        runtimeEntity.setStatus(1);
        runtimeEntity.setEndpointMap(source.getEndpointMap());
        runtimeEntity.setJmxPort(source.getJmxPort());
        runtimeEntity.setRack(source.getRack());

        return runtimeEntity;
    }

    @Override
    public RuntimeMetadata toMetadata(RuntimeEntity source) {
        return null;
    }
}
