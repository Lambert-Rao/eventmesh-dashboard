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

import org.apache.eventmesh.dashboard.console.dto.health.LastHealthCheckResponse;
import org.apache.eventmesh.dashboard.console.service.health.HealthDataService;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Autowired
    HealthDataService healthDataService;

    public List<LastHealthCheckResponse> topicLastHealthCheckList(Long clusterId) {
        return healthDataService.getTopicLastHealthCheckList(clusterId);
    }

    public List<LastHealthCheckResponse> clusterLastHealthCheckList() {
        return healthDataService.getClusterLastHealthCheckList();
    }

    public LastHealthCheckResponse storeLastHealthCheckList(Long clusterId) {
        return healthDataService.getStoreLastHealthCheckList(clusterId);
    }

    public List<LastHealthCheckResponse> runtimeLastHealthCheckList(Long clusterId) {
        return healthDataService.getRuntimeLastHealthCheckList(clusterId);
    }

    //TODO
    public List<Object> runtimeHistoryLastHealthCheckList(Long clusterId, Timestamp startTime, Timestamp endTime) {
        return null;
    }
}
