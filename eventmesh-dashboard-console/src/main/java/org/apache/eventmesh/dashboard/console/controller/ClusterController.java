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

import org.apache.eventmesh.dashboard.console.dto.cluster.GetClusterBaseMessageResponse;
import org.apache.eventmesh.dashboard.console.dto.cluster.GetClusterListResponse;
import org.apache.eventmesh.dashboard.console.dto.cluster.GetResourceNumResponse;
import org.apache.eventmesh.dashboard.console.entity.cluster.ClusterEntity;
import org.apache.eventmesh.dashboard.console.service.cluster.ClusterService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClusterController {

    @Autowired
    ClusterService clusterService;


    @GetMapping("/cluster")
    public GetResourceNumResponse getResourceNumByClusterId(Long clusterId) {
        return clusterService.getResourceNumByCluster(clusterId);
    }


    public boolean createCluster(ClusterEntity clusterEntity) {
        clusterService.addCluster(clusterEntity);
        return false;
    }

    public List<GetClusterListResponse> getClusterList() {
        return clusterService.getClusterListToFornt();
    }

    public boolean updateClusterById(ClusterEntity cluster) {
        clusterService.updateClusterById(cluster);
        return false;
    }

    public ClusterEntity showClusterConfig(Long clusterId) {
        return clusterService.selectClusterById(clusterId);
    }

    public GetClusterBaseMessageResponse getClusterBaseMessage(Long clusterId) {
        return clusterService.getClusterBaseMessage(clusterId);
    }
}
