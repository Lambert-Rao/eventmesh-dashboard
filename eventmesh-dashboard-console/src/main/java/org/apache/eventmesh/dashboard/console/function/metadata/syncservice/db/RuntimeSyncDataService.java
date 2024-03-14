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

package org.apache.eventmesh.dashboard.console.function.metadata.syncservice.db;

import org.apache.eventmesh.dashboard.console.entity.runtime.RuntimeEntity;
import org.apache.eventmesh.dashboard.console.function.metadata.SyncDataService;
import org.apache.eventmesh.dashboard.console.function.metadata.data.RuntimeMetadata;
import org.apache.eventmesh.dashboard.console.function.metadata.util.Converter;
import org.apache.eventmesh.dashboard.console.function.metadata.util.ConverterFactory;
import org.apache.eventmesh.dashboard.console.service.database.runtime.RuntimeService;
import org.apache.eventmesh.dashboard.console.service.database.cluster.ClusterService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimeSyncDataService<T> implements SyncDataService<T> {

    @Autowired
    private RuntimeService runtimeDataService;

    @Autowired
    private ClusterService clusterService;

    @Override
    public List<T> getData() {
        return null;
    }

    @Override
    public List<Long> insertData(List<T> toInsertList) {
        //if (database contains)
        // status =1
        //else
        // if cluster info does not exist, create cluster then create runtime info

        if (writable()) {
            Converter converter = ConverterFactory.createConverter(RuntimeMetadata.class);
            List<RuntimeEntity> runtimeEntities = new ArrayList<>();
            Set<String> clusterNames = new HashSet<>();
            for (T t : toInsertList) {
                RuntimeMetadata runtimeMetadata = (RuntimeMetadata) t;
                RuntimeEntity runtimeEntity = (RuntimeEntity) converter.toEntity(runtimeMetadata);
                runtimeEntities.add(runtimeEntity);
                clusterNames.add(runtimeMetadata.getClusterName());
            }

            //TODO check if cluster exists
            clusterService.batchInsert(clusterNames);
            return runtimeDataService.batchInsert(runtimeEntities);
        }
    }

    @Override
    public String getUnique(T t) {
        return null;
    }
}
