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

package org.apache.eventmesh.dashboard.console.function.metadata;

import org.apache.eventmesh.dashboard.console.function.metadata.MetadataServiceWrapper.SingleMetadataServiceWrapper;
import org.apache.eventmesh.dashboard.console.function.metadata.util.Converter;
import org.apache.eventmesh.dashboard.console.function.metadata.util.ConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetadataManager {


    private final ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

    /**
     *
     */
    private final Map<Class<?>, MetadataServiceWrapper> metaDataServiceWrapperMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, List<Object>> cacheData = new ConcurrentHashMap<>();

    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(this::run, 1000, 1000 * 10, TimeUnit.MINUTES);
    }

    //
    //注册中心 同步 dashboard
    // dashboard 同步  node
    // 单项同步，还是双向同步
    //1. topic 2. acl 3. user
    //service --> dashboard 有不做识别的
    //1. group 2. client 3. connection 4. topic
    //5. acl 6. user
    //dashboard --> service 1. topic 2. acl 3. user 4.
    //
    //@param clazz
    //@param metaDataServiceWrapper
    //

    /**
     * entrance of a sync scheduled task
     *
     * @param clazz
     * @param metaDataServiceWrapper
     */
    public void addMetadataService(Class<?> clazz, MetadataServiceWrapper metaDataServiceWrapper) {
        metaDataServiceWrapperMap.put(clazz, metaDataServiceWrapper);

    }

    public void run() {
        metaDataServiceWrapperMap.forEach(this::handlers);
    }

    public void handlers(Class<?> clazz, MetadataServiceWrapper metaDataServiceWrapper) {
        this.handler(clazz, metaDataServiceWrapper.getDbToService() );
        this.handler(clazz, metaDataServiceWrapper.getServiceToDb() );
    }

    public void handler(Class<?> clazz, SingleMetadataServiceWrapper singleMetadataServiceWrapper) {
        try {
            //
            List<Object> newObjectList = singleMetadataServiceWrapper.getSyncService().getData();
            if (newObjectList.isEmpty()) {
                return;
            }

            if(!singleMetadataServiceWrapper.getCache()){
                singleMetadataServiceWrapper.getMetaService().addMetadata(singleMetadataServiceWrapper.getMetadataConverter().convert(newObjectList));
                return;
            }
            Map<String, Object> newObjectMap = singleMetadataServiceWrapper.getMetadataConverter().getUniqueKeyMap(newObjectList);
            // 得到之前的数据
            List<Object> cacheDataList = cacheData.get(clazz);
            if (Objects.isNull(cacheDataList)) {
                cacheDataList = new ArrayList<>();
                cacheData.put(clazz, cacheDataList);
            }
            Map<String, Object> oldObjectMap = getUniqueKeyMap(cacheDataList, singleMetadataServiceWrapper.getSyncService()::getUnique);
            //update old cache
            cacheData.replace(clazz, newObjectList);

            List<Object> toInsert = new ArrayList<>();
            List<Object> toUpdate = new ArrayList<>();
            List<Object> toDelete = new ArrayList<>();

            for (Entry<String, Object> entry : dbDataMap.entrySet()) {

                Object serviceObject = serviceDataMap.remove(entry.getKey());
                //if service Data don't have a key in dbMap, service removed a piece of data.
                //we remove this data in db for now, but if db is only readable,
                //this should be replaced by send a notification to administrator
                // that a peace of data is removed in cluster service but not operated by dashboard.
                if (serviceObject == null) {
                    toDelete.add(entry.getValue());
                } else {
                    //see comment above
                    //primary id, creat time and update time should not be compared
                    if (!serviceObject.equals(entry.getValue())) {
                        toUpdate.add(entry.getValue());
                    }
                }

            }
            //see comment above
            toInsert.addAll(serviceDataMap.values());

            singleMetadataServiceWrapper.getSyncService().insertData(toInsert);
            singleMetadataServiceWrapper.getSyncService().updateData(toUpdate);
            singleMetadataServiceWrapper.getSyncService().deleteData(toDelete);
            //TODO add service to DB after create new service in db2service
            //如果以DB为主, 那么service2db的时候无法向db写入数据

            //TODO: registry (service to db)
            //不同的metadata service 有不同的处理方式
            //注册nacos之后哪些数据能够反方向获取了？
            //这里还是不对的，应该调用一个添加的接口
            if (metaDataServiceWrapper.getServiceToDb() == null) {
                metaDataServiceWrapper.setDbToService(new SingleMetadataServiceWrapper(
                    singleMetadataServiceWrapper.getSyncService(), singleMetadataServiceWrapper.getMetaService()));
            }
        } catch (Throwable e) {
            log.error("metadata manager handler error", e);
        }
    }




}
