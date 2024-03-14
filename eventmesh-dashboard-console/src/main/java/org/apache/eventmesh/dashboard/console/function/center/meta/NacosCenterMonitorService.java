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

package org.apache.eventmesh.dashboard.console.function.center.meta;

import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.center.CenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.client.ClientManager;
import org.apache.eventmesh.dashboard.console.function.client.ClientTypeEnum;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateNacosConfig;
import org.apache.eventmesh.dashboard.console.function.client.wrapper.NacosClientWrapper;
import org.apache.eventmesh.dashboard.console.function.metadata.syncservice.meta.RuntimeMetaService;

import javafx.util.Pair;

public class NacosCenterMonitorService implements CenterMonitorService {

    private String clientKey;

    @Override
    public Object getCenterInfo() {
//        ClientManager.getInstance().getClient(ClientTypeEnum.CENTER_NACOS, clientKey);
//        return null;
    }

    @Override
    public void init(RuntimeMetaService runtimeMetaService, MetadataOperationConfig serviceConfig) {
        CreateNacosConfig createNacosConfig = new CreateNacosConfig();
        createNacosConfig.setServerAddress(serviceConfig.getRegistryAddress());
        Pair<String, NacosClientWrapper> result = ClientManager.getInstance().createClient(ClientTypeEnum.CENTER_NACOS, createNacosConfig);
        clientKey = result.getKey();
    }

    @Override
    public void close() {
        ClientManager.getInstance().deleteClient(ClientTypeEnum.CENTER_NACOS, clientKey);
    }
}
