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

package org.apache.eventmesh.dashboard.console.service.health.impl;

import org.apache.eventmesh.dashboard.console.dto.health.LastHealthCheckResponse;
import org.apache.eventmesh.dashboard.console.entity.cluster.ClusterEntity;
import org.apache.eventmesh.dashboard.console.entity.health.HealthCheckResultEntity;
import org.apache.eventmesh.dashboard.console.entity.runtime.RuntimeEntity;
import org.apache.eventmesh.dashboard.console.entity.storage.StoreEntity;
import org.apache.eventmesh.dashboard.console.entity.topic.TopicEntity;
import org.apache.eventmesh.dashboard.console.mapper.cluster.ClusterMapper;
import org.apache.eventmesh.dashboard.console.mapper.health.HealthCheckResultMapper;
import org.apache.eventmesh.dashboard.console.mapper.runtime.RuntimeMapper;
import org.apache.eventmesh.dashboard.console.mapper.storage.StoreMapper;
import org.apache.eventmesh.dashboard.console.mapper.topic.TopicMapper;
import org.apache.eventmesh.dashboard.console.service.health.HealthDataService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthDataServiceDatabaseImpl implements HealthDataService {

    @Autowired
    private HealthCheckResultMapper healthCheckResultMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private ClusterMapper clusterMapper;

    @Autowired
    private RuntimeMapper runtimeMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public List<LastHealthCheckResponse> getRuntimeLastHealthCheckList(Long clusterId) {
        RuntimeEntity runtimeEntity = new RuntimeEntity();
        runtimeEntity.setClusterId(clusterId);
        List<RuntimeEntity> runtimeEntities = runtimeMapper.selectRuntimeByCluster(runtimeEntity);
        HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
        ArrayList<LastHealthCheckResponse> lastHealthCheckResponds = new ArrayList<>();
        runtimeEntities.forEach(n -> {
            healthCheckResultEntity.setType(2);
            healthCheckResultEntity.setTypeId(n.getId());
            HealthCheckResultEntity latestByTypeAndId = healthCheckResultMapper.getLatestByTypeAndId(healthCheckResultEntity);
            LastHealthCheckResponse lastHealthCheckResponse = new LastHealthCheckResponse();
            lastHealthCheckResponse.setHealthState(latestByTypeAndId.getState());
            lastHealthCheckResponse.setInstanceName(n.getHost() + ":" + n.getPort());
            lastHealthCheckResponse.setResultDesc(latestByTypeAndId.getResultDesc());
            lastHealthCheckResponse.setUpdateTime(latestByTypeAndId.getCreateTime());
            lastHealthCheckResponds.add(lastHealthCheckResponse);
        });

        return lastHealthCheckResponds;
    }

    @Override
    public LastHealthCheckResponse getStoreLastHealthCheckList(Long clusterId) {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setClusterId(clusterId);
        StoreEntity storeEntities = storeMapper.selectStoreByCluster(storeEntity);
        HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
        healthCheckResultEntity.setType(4);
        healthCheckResultEntity.setTypeId(storeEntities.getId());
        HealthCheckResultEntity latestByTypeAndId = healthCheckResultMapper.getLatestByTypeAndId(healthCheckResultEntity);
        LastHealthCheckResponse lastHealthCheckResponse = new LastHealthCheckResponse();
        lastHealthCheckResponse.setHealthState(latestByTypeAndId.getState());
        lastHealthCheckResponse.setInstanceName(storeEntities.getHost() + ":" + storeEntities.getPort());
        lastHealthCheckResponse.setResultDesc(latestByTypeAndId.getResultDesc());
        lastHealthCheckResponse.setUpdateTime(latestByTypeAndId.getCreateTime());

        return lastHealthCheckResponse;
    }

    @Override
    public List<LastHealthCheckResponse> getClusterLastHealthCheckList() {

        List<ClusterEntity> clusterEntities = clusterMapper.selectAllCluster();
        HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
        ArrayList<LastHealthCheckResponse> lastHealthCheckResponses = new ArrayList<>();
        clusterEntities.forEach(n -> {
            healthCheckResultEntity.setType(1);
            healthCheckResultEntity.setTypeId(n.getId());
            HealthCheckResultEntity latestByTypeAndId = healthCheckResultMapper.getLatestByTypeAndId(healthCheckResultEntity);
            LastHealthCheckResponse lastHealthCheckResponse = new LastHealthCheckResponse();
            lastHealthCheckResponse.setHealthState(latestByTypeAndId.getState());
            lastHealthCheckResponse.setInstanceName(n.getName());
            lastHealthCheckResponse.setResultDesc(latestByTypeAndId.getResultDesc());
            lastHealthCheckResponse.setUpdateTime(latestByTypeAndId.getCreateTime());
            lastHealthCheckResponses.add(lastHealthCheckResponse);
        });

        return lastHealthCheckResponses;
    }

    @Override
    public List<LastHealthCheckResponse> getTopicLastHealthCheckList(Long clusterId) {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setClusterId(clusterId);
        List<TopicEntity> topicList = topicMapper.getTopicList(topicEntity);
        HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
        ArrayList<LastHealthCheckResponse> lastHealthCheckRespons = new ArrayList<>();
        topicList.forEach(n -> {
            healthCheckResultEntity.setType(3);
            healthCheckResultEntity.setTypeId(n.getId());
            HealthCheckResultEntity latestByTypeAndId = healthCheckResultMapper.getLatestByTypeAndId(healthCheckResultEntity);
            LastHealthCheckResponse lastHealthCheckResponse = new LastHealthCheckResponse();
            lastHealthCheckResponse.setHealthState(latestByTypeAndId.getState());
            lastHealthCheckResponse.setInstanceName(n.getTopicName());
            lastHealthCheckResponse.setResultDesc(latestByTypeAndId.getResultDesc());
            lastHealthCheckResponse.setUpdateTime(latestByTypeAndId.getCreateTime());
            lastHealthCheckRespons.add(lastHealthCheckResponse);
        });

        return lastHealthCheckRespons;
    }

    @Override
    public HealthCheckResultEntity insertHealthCheckResult(HealthCheckResultEntity healthCheckResultEntity) {
        healthCheckResultMapper.insert(healthCheckResultEntity);
        return healthCheckResultEntity;
    }

    @Override
    public void batchInsertHealthCheckResult(List<HealthCheckResultEntity> healthCheckResultEntityList) {
        healthCheckResultMapper.batchInsert(healthCheckResultEntityList);
    }

    @Override
    public List<HealthCheckResultEntity> queryHealthCheckResultByClusterIdAndTypeAndTypeId(HealthCheckResultEntity entity) {
        return healthCheckResultMapper.selectByClusterIdAndTypeAndTypeId(entity);
    }

    @Override
    public void batchUpdateCheckResult(List<HealthCheckResultEntity> healthCheckResultEntityList) {
        healthCheckResultMapper.batchUpdate(healthCheckResultEntityList);
    }

    @Override
    public void batchUpdateCheckResultByClusterIdAndTypeAndTypeId(List<HealthCheckResultEntity> healthCheckResultEntityList) {
        List<HealthCheckResultEntity> idsNeedToBeUpdate = healthCheckResultMapper.getIdsNeedToBeUpdateByClusterIdAndTypeAndTypeId(
            healthCheckResultEntityList);
        idsNeedToBeUpdate.forEach(entity -> {
            healthCheckResultEntityList.forEach(updateEntity -> {
                if (entity.getClusterId().equals(updateEntity.getClusterId()) && entity.getType().equals(updateEntity.getType())
                    && entity.getTypeId().equals(updateEntity.getTypeId())) {
                    updateEntity.setId(entity.getId());
                }
            });
        });
        healthCheckResultMapper.batchUpdate(healthCheckResultEntityList);
    }


    @Override
    public List<HealthCheckResultEntity> queryHealthCheckResultByClusterIdAndTimeRange(Long clusterId, Timestamp startTime, Timestamp endTime) {
        return healthCheckResultMapper.selectByClusterIdAndCreateTimeRange(clusterId, startTime, endTime);
    }
}
