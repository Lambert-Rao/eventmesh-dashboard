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

package org.apache.eventmesh.dashboard.console.service.topic;

import org.apache.eventmesh.dashboard.console.dto.topic.CreateTopicRequest;
import org.apache.eventmesh.dashboard.console.dto.topic.GetTopicListResponse;
import org.apache.eventmesh.dashboard.console.entity.config.ConfigEntity;
import org.apache.eventmesh.dashboard.console.entity.groupmember.GroupMemberEntity;
import org.apache.eventmesh.dashboard.console.entity.health.HealthCheckResultEntity;
import org.apache.eventmesh.dashboard.console.entity.storage.StoreEntity;
import org.apache.eventmesh.dashboard.console.entity.topic.TopicEntity;
import org.apache.eventmesh.dashboard.console.mapper.config.ConfigMapper;
import org.apache.eventmesh.dashboard.console.mapper.groupmember.OprGroupMemberMapper;
import org.apache.eventmesh.dashboard.console.mapper.health.HealthCheckResultMapper;
import org.apache.eventmesh.dashboard.console.mapper.runtime.RuntimeMapper;
import org.apache.eventmesh.dashboard.console.mapper.storage.StoreMapper;
import org.apache.eventmesh.dashboard.console.mapper.topic.TopicMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    OprGroupMemberMapper oprGroupMemberMapper;

    @Autowired
    HealthCheckResultMapper healthCheckResultMapper;

    @Autowired
    ConfigMapper configMapper;

    @Autowired
    RuntimeMapper runtimeMapper;

    @Autowired
    StoreMapper storeMapper;


    @Override
    public void createTopic(Long clusterId, CreateTopicRequest topicRequest) {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setType(0);
        topicEntity.setClusterId(clusterId);
        topicEntity.setTopicName(topicRequest.getName());
        topicEntity.setDescription(topicRequest.getDescription());
        topicEntity.setRetentionMs(topicRequest.getSaveTime().getTime());
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setClusterId(topicEntity.getClusterId());
        topicEntity.setStorageId(storeMapper.selectStoreByCluster(storeEntity).getId());
        topicMapper.addTopic(topicEntity);

        StoreEntity storeEntity1 = storeMapper.selectStoreByCluster(storeEntity);
        storeEntity1.setTopicList(storeEntity1.getTopicList() + topicEntity.getTopicName() + ",");
        storeMapper.updateTopicListByCluster(storeEntity1);

        ConfigEntity config = new ConfigEntity();
        config.setClusterId(clusterId);
        config.setInstanceType(3);

        Arrays.stream(CreateTopicRequest.class.getDeclaredFields()).forEach(n -> {
            n.setAccessible(true);
            config.setConfigName(n.getName());
            try {
                config.setConfigValue(String.valueOf(n.get(topicRequest)));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            configMapper.addConfig(config);
        });
    }

    @Override
    public void batchInsert(List<TopicEntity> topicEntities) {
        topicMapper.batchInsert(topicEntities);
    }

    @Override
    public List<TopicEntity> selectAll() {
        return topicMapper.selectAll();
    }

    @Override
    public Integer selectTopicNumByCluster(Long clusterId) {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setClusterId(clusterId);
        return topicMapper.selectTopicNumByCluster(topicEntity);
    }

    @Override
    public List<TopicEntity> getTopicList(TopicEntity topicEntity) {
        return topicMapper.getTopicList(topicEntity);
    }

    @Override
    public void addTopic(TopicEntity topicEntity) {
        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        groupMemberEntity.setTopicName(topicEntity.getTopicName());
        groupMemberEntity.setState("active");
        oprGroupMemberMapper.updateMemberByTopic(groupMemberEntity);
        topicMapper.addTopic(topicEntity);
    }

    @Override
    public void updateTopic(TopicEntity topicEntity) {
        topicMapper.updateTopic(topicEntity);
    }

    @Override
    public void deleteTopicById(TopicEntity topicEntity) {
        topicMapper.deleteTopic(topicEntity);
    }

    @Override
    public TopicEntity selectTopicById(TopicEntity topicEntity) {
        return topicMapper.selectTopicById(topicEntity);
    }

    @Override
    public TopicEntity selectTopicByUnique(TopicEntity topicEntity) {
        return topicMapper.selectTopicByUnique(topicEntity);
    }

    @Override
    public void deleteTopic(Long topicId, String topicName) {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setId(topicId);
        topicEntity.setTopicName(topicName);
        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        groupMemberEntity.setTopicName(topicEntity.getTopicName());
        groupMemberEntity.setState("empty");
        oprGroupMemberMapper.updateMemberByTopic(groupMemberEntity);
        topicMapper.deleteTopic(topicEntity);
    }

    @Override
    public List<TopicEntity> selectTopiByCluster(Long clusterId) {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setClusterId(clusterId);
        return topicMapper.selectTopicByCluster(topicEntity);
    }

    @Override
    public Integer getAbnormalTopicNum(Long clusterId) {
        HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
        healthCheckResultEntity.setClusterId(clusterId);
        healthCheckResultEntity.setType(3);
        return healthCheckResultMapper.getAbnormalNumByClusterIdAndType(healthCheckResultEntity);
    }

    @Override
    public List<GetTopicListResponse> getTopicFrontList(Long clusterId) {
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setClusterId(clusterId);
        List<TopicEntity> topicEntityList = topicMapper.selectAllByClusterId(topicEntity);
        List<GetTopicListResponse> topicListResponses = new ArrayList<>();
        HealthCheckResultEntity healthCheckResultEntity = new HealthCheckResultEntity();
        healthCheckResultEntity.setType(3);
        topicEntityList.forEach(n -> {
            healthCheckResultEntity.setTypeId(n.getId());
            GetTopicListResponse getTopicListResponse = new GetTopicListResponse(n.getId(), n.getTopicName(),
                healthCheckResultMapper.getLatestByTypeAndId(healthCheckResultEntity).getState(), n.getDescription());
            topicListResponses.add(getTopicListResponse);
        });
        return topicListResponses;
    }


}
