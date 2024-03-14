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

package org.apache.eventmesh.dashboard.console.function.center;

import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.MetadataServiceTypeEnum;
import org.apache.eventmesh.dashboard.console.function.center.meta.EtcdCenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.center.meta.NacosCenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.metadata.MetadataHandler;
import org.apache.eventmesh.dashboard.console.function.metadata.data.CenterMetadata;
import org.apache.eventmesh.dashboard.console.function.metadata.data.RuntimeMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;

/**
 * Center Manager注册了monitor之后，才能从center获取数据
 */
public class CenterManager {

    private static final Map<MetadataServiceTypeEnum, Class<?>> SERIVICE_TYPE_ENUMS_CLASS_MAP = new ConcurrentHashMap<>();

    static {
        SERIVICE_TYPE_ENUMS_CLASS_MAP.put(MetadataServiceTypeEnum.CENTER_ETCD, EtcdCenterMonitorService.class);
        SERIVICE_TYPE_ENUMS_CLASS_MAP.put(MetadataServiceTypeEnum.CENTER_NACOS, NacosCenterMonitorService.class);
    }

    /**
     * key: center address
     */
    @Getter
    private final Map<String, CenterMonitorService> centerMap = new ConcurrentHashMap<>();

    private final CenterMetaService centerMetaService = new CenterMetaService();

    @Setter
    private MetadataHandler<RuntimeMetadata> runtimeMetaService;

    public MetadataHandler<CenterMetadata> getCenterMetaService() {
        return centerMetaService;
    }


    public void startMonitor(MetadataOperationConfig serviceConfig) {
        if (centerMap.containsKey(serviceConfig.getRegistryAddress())) {
            return;
        }
        Class<?> clazz = SERIVICE_TYPE_ENUMS_CLASS_MAP.get(serviceConfig.getServiceTypeEnums());
        try {
            CenterMonitorService centerMonitorService = (CenterMonitorService) clazz.newInstance();
            centerMonitorService.init(runtimeMetaService, serviceConfig);
            centerMap.put(serviceConfig.getRegistryAddress(), centerMonitorService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void stopMonitor(MetadataOperationConfig serviceConfig) {
        CenterMonitorService centerMonitorService = centerMap.get(serviceConfig.getRegistryAddress());
        if (centerMonitorService != null) {
            centerMonitorService.close();
            centerMap.remove(serviceConfig.getRegistryAddress());
        }
    }


    public class CenterMetaService implements MetadataHandler<CenterMetadata> {

        @Setter
        private String address;

        @Override
        public List<CenterMetadata> getAllMetadata() {
            //
            return Arrays.asList((CenterMetadata) CenterManager.this.getCenterMap().get(address).getCenterInfo());
            //
        }

    }


}
