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

import org.apache.eventmesh.dashboard.console.function.health.CheckResultCache;
import org.apache.eventmesh.dashboard.console.function.health.HealthService;
import org.apache.eventmesh.dashboard.console.service2.database.HealthDataService;

import lombok.Getter;
import lombok.Setter;

/**
 * FunctionManager is in charge of tasks such as scheduled health checks
 */
public class FunctionManager {

    @Setter
    private FunctionManagerProperties properties;

    @Getter
    private HealthService healthService;

    @Setter
    private HealthDataService healthDataService;

    public void initFunctionManager() {
        // HealthService Initialization
        healthService = new HealthService();
        CheckResultCache checkResultCache = new CheckResultCache();
        healthService.createExecutor(healthDataService, checkResultCache);
        healthService.startScheduledExecution(5, 5);
    }
}
