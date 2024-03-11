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

package org.apache.eventmesh.dashboard.console.function.metadata.service.sync;

import org.apache.eventmesh.dashboard.console.entity.MetaEntity;
import org.apache.eventmesh.dashboard.console.function.metadata.SyncDataService;
import org.apache.eventmesh.dashboard.console.function.metadata.data.CenterMetaData;
import org.apache.eventmesh.dashboard.console.service.database.CenterDataService;

import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CenterSyncDataService<T> implements SyncDataService<T> {

    public CenterSyncDataService(CenterDataService centerDataService) {
        this.centerDataService = centerDataService;
    }

    @Setter
    private CenterDataService centerDataService;

    @Override
    public List<T> getData() {
        return (List<T>) centerDataService.getAll();
    }

    @Override
    public List<Long> insertData(List<T> toInsertList) {
        if (writable()) {
            return centerDataService.batchInsert((List<CenterMetaData>) toInsertList);
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
        if (t instanceof CenterMetaData) {
            CenterMetaData centerMeta = (CenterMetaData) t;
            return centerMeta.getAddress();
        } else {
            throw new IllegalArgumentException("unsupported type");
        }
    }


}
