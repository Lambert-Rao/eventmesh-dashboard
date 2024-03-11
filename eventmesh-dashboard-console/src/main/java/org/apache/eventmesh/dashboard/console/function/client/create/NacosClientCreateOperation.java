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

package org.apache.eventmesh.dashboard.console.function.client.create;

import org.apache.eventmesh.dashboard.console.function.client.ClientOperation;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateClientConfig;
import org.apache.eventmesh.dashboard.console.function.client.wrapper.NacosClientWrapper;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingService;
import javafx.util.Pair;

public class NacosClientCreateOperation implements ClientOperation {

    private final NacosConfigClientCreateOperation nacosConfigClientCreateOperation = new NacosConfigClientCreateOperation();
    private final NacosNamingClientCreateOperation nacosNamingClientCreateOperation = new NacosNamingClientCreateOperation();

    @Override
    public Pair createClient(CreateClientConfig clientConfig) {
        Pair configPair = nacosConfigClientCreateOperation.createClient(clientConfig);
        Pair namingPair = nacosNamingClientCreateOperation.createClient(clientConfig);
        if (configPair.getKey() != namingPair.getKey()) {
            throw new RuntimeException("Nacos config and naming server address not match");
        }
        NacosClientWrapper nacosClient = new NacosClientWrapper(
            (ConfigService) configPair.getValue(), (NamingService) namingPair.getValue()
        );
        return new Pair<>(configPair.getKey(), nacosClient);
    }

    @Override
    public void close(Object client) {
        try {
            ((NacosClientWrapper) client).shutdown();
        } catch (Exception e) {
            throw new RuntimeException("Nacos client close failed", e);
        }
    }
}
