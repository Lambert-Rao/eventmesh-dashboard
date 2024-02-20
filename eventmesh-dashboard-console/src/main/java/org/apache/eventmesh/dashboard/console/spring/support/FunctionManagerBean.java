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

package org.apache.eventmesh.dashboard.console.spring.support;

import javax.annotation.PostConstruct;
import org.apache.eventmesh.dashboard.console.health.HealthService;
import org.apache.eventmesh.dashboard.console.service.health.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FunctionManagerBean {

    private FunctionManager functionManager;

    private FunctionManagerProperties properties;

    @Autowired
    private HealthDataService healthDataService;

    @Bean
    public HealthService getHealthService() {
        return functionManager.getHealthService();
    }

    @PostConstruct
    void initManager() {
        functionManager = new FunctionManager();
        functionManager.setProperties(properties);
        functionManager.setHealthDataService(healthDataService);
        functionManager.initFunctionManager();
    }
}