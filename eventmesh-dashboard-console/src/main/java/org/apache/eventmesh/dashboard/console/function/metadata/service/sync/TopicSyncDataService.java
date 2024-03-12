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

import org.apache.eventmesh.dashboard.console.entity.TopicEntity;
import org.apache.eventmesh.dashboard.console.function.metadata.SyncDataService;
import org.apache.eventmesh.dashboard.console.function.metadata.data.TopicMetadata;
import org.apache.eventmesh.dashboard.console.service2.database.TopicDataService;

import java.util.List;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopicSyncDataService<T> implements SyncDataService<T> {

    public TopicSyncDataService(TopicDataService topicDataService) {
        this.topicDataService = topicDataService;
    }

    @Setter
    private TopicDataService topicDataService;

    @Override
    public List<T> getData() {
        return (List<T>) topicDataService.selectAll();
    }

    @Override
    public List<Long> insertData(List<T> toInsertList) {
        if (writable()) {
            return topicDataService.batchInsert((List<TopicEntity>) toInsertList);
        } else {
            log.warn("topic data source is not writable");
            return null;
        }
    }

    @Override
    public String getUnique(T t) {
        if (t instanceof TopicEntity) {
            TopicEntity topicEntity = (TopicEntity) t;
            return topicEntity.getTopicName();
        } else if (t instanceof TopicMetadata) {
            TopicMetadata topicMeta = (TopicMetadata) t;
            return topicMeta.getTopic();
        } else {
            throw new IllegalArgumentException("unsupported type");
        }
    }
}
