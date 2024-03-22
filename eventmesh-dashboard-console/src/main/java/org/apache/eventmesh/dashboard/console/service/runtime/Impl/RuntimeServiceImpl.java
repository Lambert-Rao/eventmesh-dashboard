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

package org.apache.eventmesh.dashboard.console.service.runtime.Impl;

import org.apache.eventmesh.dashboard.console.dto.topic.GetInstanceAndAbnormalNumResponse;
import org.apache.eventmesh.dashboard.console.entity.health.HealthCheckResultEntity;
import org.apache.eventmesh.dashboard.console.entity.runtime.RuntimeEntity;
import org.apache.eventmesh.dashboard.console.mapper.health.HealthCheckResultMapper;
import org.apache.eventmesh.dashboard.console.mapper.runtime.RuntimeMapper;
import org.apache.eventmesh.dashboard.console.service.runtime.RuntimeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimeServiceImpl implements RuntimeService {

    @Autowired
    private RuntimeMapper runtimeMapper;

    @Autowired
    private HealthCheckResultMapper healthCheckResultMapper;

    @Override
    public List<RuntimeEntity> getRuntimeToFrontByClusterId(Long clusterId) {
        List<RuntimeEntity> runtimeByClusterId = this.getRuntimeByClusterId(clusterId);
        runtimeByClusterId.forEach(n -> {
            HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
            healthCheckResultEntity.setType(2);
            healthCheckResultEntity.setTypeId(n.getId());
            n.setStatus(healthCheckResultMapper.selectStateByTypeAndId(healthCheckResultEntity));
        });
        return runtimeByClusterId;
    }

    @Override
    public GetInstanceAndAbnormalNumResponse getRuntimeBaseMessage(Long clusterId) {
        List<RuntimeEntity> runtimeEntities = this.selectAll();
        HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
        int abnormalNumByClusterIdAndType = 0;
        for (RuntimeEntity n : runtimeEntities) {
            healthCheckResultEntity.setType(2);
            healthCheckResultEntity.setTypeId(n.getId());
            abnormalNumByClusterIdAndType += healthCheckResultMapper.getAbnormalNumByClusterIdAndType(healthCheckResultEntity);
        }
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setClusterId(clusterId);
        Integer runtimeNumByCluster = runtimeMapper.getRuntimeNumByCluster(runtimeEntity);
        return new GetInstanceAndAbnormalNumResponse(runtimeNumByCluster, abnormalNumByClusterIdAndType);
    }

    @Override
    public void batchInsert(List<RuntimeEntity> runtimeEntities) {
        runtimeMapper.batchInsert(runtimeEntities);
    }

    @Override
    public List<RuntimeEntity> selectAll() {
        return runtimeMapper.selectAll();
    }

    @Override
    public List<RuntimeEntity> getRuntimeByClusterId(Long clusterId) {
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setClusterId(clusterId);

        return runtimeMapper.selectRuntimeByCluster(runtimeEntity);
    }

    @Override
    public void addRuntime(RuntimeEntity runtimeEntity) {
        runtimeMapper.addRuntime(runtimeEntity);
    }

    @Override
    public void updateRuntimeByCluster(RuntimeEntity runtimeEntity) {
        runtimeMapper.updateRuntimeByCluster(runtimeEntity);
    }

    @Override
    public void deleteRuntimeByCluster(RuntimeEntity runtimeEntity) {
        runtimeMapper.deleteRuntimeByCluster(runtimeEntity);
    }
}
