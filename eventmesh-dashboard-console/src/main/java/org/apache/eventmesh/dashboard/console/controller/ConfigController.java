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

package org.apache.eventmesh.dashboard.console.controller;

import org.apache.eventmesh.dashboard.console.entity.config.ConfigEntity;
import org.apache.eventmesh.dashboard.console.service.config.ConfigService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    public boolean updateConfigsByTypeAndId(String name, List<ConfigEntity> configEntityList) {
        configService.updateConfigsByInstanceId(name, configEntityList);
        return false;
    }

    public List<ConfigEntity> selectConfigsByTypeAndId(Long instanceId, Integer type) {
        return configService.selectByInstanceId(instanceId, type);
    }

    public List<String> getConnectorClasses(String type) {
        return configService.getConnectorClasses(type);
    }

    public List<ConfigEntity> getConnectorConfigsByClassAndVersion(String classType, String version) {
        return configService.getConnectorConfigsByClassAndVersion(classType, version);
    }
}
