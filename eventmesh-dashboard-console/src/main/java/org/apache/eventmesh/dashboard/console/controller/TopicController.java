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


import org.apache.eventmesh.dashboard.console.dto.topic.CreateTopicRequest;
import org.apache.eventmesh.dashboard.console.dto.topic.GetInstanceAndAbnormalNumResponse;
import org.apache.eventmesh.dashboard.console.dto.topic.GetTopicListResponse;
import org.apache.eventmesh.dashboard.console.service.topic.TopicService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("")
    public GetInstanceAndAbnormalNumResponse getTopicAndAbnormalNum(Long clusterId) {
        Integer topicCount = topicService.selectTopicNumByCluster(clusterId);
        Integer abnormalTopicNum = topicService.getAbnormalTopicNum(clusterId);
        GetInstanceAndAbnormalNumResponse getInstanceAndAbnormalNumResponse = new GetInstanceAndAbnormalNumResponse(topicCount, abnormalTopicNum);
        return getInstanceAndAbnormalNumResponse;
    }

    public List<GetTopicListResponse> getTopicList(Long clusterId) {
        return topicService.getTopicFrontList(clusterId);
    }

    public boolean deleteTopic(Long topicId, String topicName) {
        try {
            topicService.deleteTopic(topicId, topicName);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean createTopic(Long clusterId, CreateTopicRequest createTopicRequest) {
        topicService.createTopic(clusterId, createTopicRequest);
        return false;
    }


}
