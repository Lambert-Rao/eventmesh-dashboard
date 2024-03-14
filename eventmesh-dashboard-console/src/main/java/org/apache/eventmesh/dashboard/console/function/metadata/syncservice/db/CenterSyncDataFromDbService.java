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


import org.apache.eventmesh.dashboard.console.entity.meta.MetaEntity;
import org.apache.eventmesh.dashboard.console.function.metadata.SyncDataService;
import org.apache.eventmesh.dashboard.console.function.metadata.data.CenterMetadata;
import org.apache.eventmesh.dashboard.console.function.metadata.util.Converter;
import org.apache.eventmesh.dashboard.console.function.metadata.util.ConverterFactory;
import org.apache.eventmesh.dashboard.console.service.database.center.CenterDataService;
import org.apache.eventmesh.dashboard.console.service.metadata.CenterService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CenterSyncDataFromDbService<T> implements SyncDataService<T> {

    public CenterSyncDataFromDbService(MetaDataService centerDataService) {
        this.centerDataService = centerDataService;
    }

    @Autowired
    private CenterService centerDataService;

    @Override
    public List<T> getData() {
        return (List<T>) centerDataService.selectAll();
    }

    @Override
    public List<Long> insertData(List<T> toInsertList) {
        if (writable()) {
            Converter converter = ConverterFactory.createConverter(CenterMetadata.class);
            List<MetaEntity> centerEntities = new ArrayList<>();
            for (T t : toInsertList) {
                CenterMetadata centerMeta = (CenterMetadata) t;
                MetaEntity centerEntity = (MetaEntity) converter.toEntity(centerMeta);
                centerEntities.add(centerEntity);
            }
            return centerDataService.batchInsert(centerEntities);
        } else {
            return null;
        }
    }

    @Override
    public String getUnique(T t) {
        // warning: there two method should return the same String
        if (t instanceof MetaEntity) {
            MetaEntity centerEntity = (MetaEntity) t;
            return centerEntity.getHost() + ":" + centerEntity.getPort();
        }
        if (t instanceof CenterMetadata) {
            CenterMetadata centerMeta = (CenterMetadata) t;
            return centerMeta.getRegistryAddress();
        } else {
            throw new IllegalArgumentException("unsupported type");
        }
    }


}
