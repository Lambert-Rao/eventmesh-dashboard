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

package org.apache.eventmesh.dashboard.console.function.optration;

import org.apache.eventmesh.dashboard.console.enums.StoreType;
import org.apache.eventmesh.dashboard.console.function.metadata.MetaDataHandler;
import org.apache.eventmesh.dashboard.console.function.metadata.data.TopicMetaData;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TopicMetaDataHandlerImpl implements MetaDataHandler<TopicMetaData> {

    @Override
    public boolean writable() {
        return true;
    }

    //TODO: use topic utils
    String storeAddress;
    StoreType storeType;

    @Override
    public List<TopicMetaData> getAllMetaData() {
        if (storeType == StoreType.ROCKETMQ) {
            //TODO
        } else if (storeType == StoreType.KAFKA) {
            //TODO
        } else if (storeType == StoreType.RABBITMQ) {
            //TODO
        } else {
            log.warn("Unsupported store type called get all meta data");
        }
        return null;
    }

    @Override
    public void addMetaData(TopicMetaData meta) {
        if (writable()) {
            if (meta.getStoreType() == StoreType.ROCKETMQ) {
                //TODO
            } else if (meta.getStoreType() == StoreType.KAFKA) {
                //TODO
            } else if (meta.getStoreType() == StoreType.RABBITMQ) {
                //TODO
            } else {
                log.warn("Unsupported store type called add meta data");
            }
        } else {
            log.warn("topic storage service is not writable");
        }
    }

    @Override
    public void deleteMetaData(TopicMetaData meta) {
        //TODO
    }
}
