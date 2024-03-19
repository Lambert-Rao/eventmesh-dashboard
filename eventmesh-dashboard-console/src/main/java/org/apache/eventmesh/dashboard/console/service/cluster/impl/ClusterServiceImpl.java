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

package org.apache.eventmesh.dashboard.console.service.cluster.impl;

import org.apache.eventmesh.dashboard.console.dto.cluster.GetClusterBaseMessageResponse;
import org.apache.eventmesh.dashboard.console.dto.cluster.GetClusterListResponse;
import org.apache.eventmesh.dashboard.console.dto.cluster.GetResourceNumResponse;
import org.apache.eventmesh.dashboard.console.entity.cluster.ClusterEntity;
import org.apache.eventmesh.dashboard.console.entity.connection.ConnectionEntity;
import org.apache.eventmesh.dashboard.console.entity.group.GroupEntity;
import org.apache.eventmesh.dashboard.console.entity.storage.StoreEntity;
import org.apache.eventmesh.dashboard.console.entity.topic.TopicEntity;
import org.apache.eventmesh.dashboard.console.mapper.cluster.ClusterMapper;
import org.apache.eventmesh.dashboard.console.mapper.connection.ConnectionMapper;
import org.apache.eventmesh.dashboard.console.mapper.group.OprGroupMapper;
import org.apache.eventmesh.dashboard.console.mapper.storage.StoreMapper;
import org.apache.eventmesh.dashboard.console.mapper.topic.TopicMapper;
import org.apache.eventmesh.dashboard.console.service.cluster.ClusterService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ConnectionMapper connectionMapper;

    @Autowired
    private ClusterMapper clusterMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private OprGroupMapper oprGroupMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public GetClusterBaseMessageResponse getClusterBaseMessage(Long clusterId) {
        TopicEntity topicEntity = new TopicEntity();
        StoreEntity storeEntity = new StoreEntity();
        GroupEntity groupEntity = new GroupEntity();
        topicEntity.setClusterId(clusterId);
        storeEntity.setClusterId(clusterId);
        groupEntity.setClusterId(clusterId);
        GetClusterBaseMessageResponse getClusterBaseMessageResponse =
            new GetClusterBaseMessageResponse(topicMapper.selectTopicNumByCluster(topicEntity),
                oprGroupMapper.getConsumerNumByCluster(groupEntity));
        return getClusterBaseMessageResponse;
    }

    @Override
    public GetResourceNumResponse getResourceNumByCluster(Long clusterId) {
        ConnectionEntity connectionEntity = new ConnectionEntity();
        connectionEntity.setClusterId(clusterId);
        Integer connectionNumByCluster = connectionMapper.selectConnectionNumByCluster(connectionEntity);
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setClusterId(clusterId);
        Integer topicNumByCluster = topicMapper.selectTopicNumByCluster(topicEntity);
        GetResourceNumResponse getResourceNumResponse = new GetResourceNumResponse(topicNumByCluster, connectionNumByCluster);
        return getResourceNumResponse;
    }

    @Override
    public List<GetClusterListResponse> getClusterList() {
        List<ClusterEntity> clusterEntities = this.selectAll();
        ArrayList<GetClusterListResponse> getClusterListResponses = new ArrayList<>();
        clusterEntities.forEach(n -> {
            GetClusterListResponse getClusterListResponse = new GetClusterListResponse();
            getClusterListResponse.setClusterId(n.getId());
            getClusterListResponse.setEventmeshVersion(n.getEventmeshVersion());
            getClusterListResponse.setName(n.getName());
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.setClusterId(n.getId());
            getClusterListResponses.add(getClusterListResponse);
        });
        return getClusterListResponses;
    }

    @Override
    public void batchInsert(List<ClusterEntity> clusterEntities) {
        clusterMapper.batchInsert(clusterEntities);
    }

    @Override
    public List<ClusterEntity> selectAll() {
        return clusterMapper.selectAllCluster();
    }

    @Override
    public void addCluster(ClusterEntity cluster) {
        clusterMapper.addCluster(cluster);
    }

    @Override
    public List<ClusterEntity> selectAllCluster() {
        return clusterMapper.selectAllCluster();
    }

    @Override
    public ClusterEntity selectClusterById(Long clusterId) {
        ClusterEntity clusterEntity = new ClusterEntity();
        clusterEntity.setId(clusterId);
        return clusterMapper.selectClusterById(clusterEntity);
    }

    @Override
    public void updateClusterById(ClusterEntity cluster) {
        clusterMapper.updateClusterById(cluster);
    }

    @Override
    public void deleteClusterById(ClusterEntity cluster) {
        clusterMapper.deActive(cluster);
    }

}
