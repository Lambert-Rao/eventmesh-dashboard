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

import org.apache.eventmesh.dashboard.console.dto.topic.GetInstanceAndAbnormalNumResponse;
import org.apache.eventmesh.dashboard.console.entity.runtime.RuntimeEntity;
import org.apache.eventmesh.dashboard.console.service.runtime.RuntimeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuntimeController {

    @Autowired
    private RuntimeService runtimeService;

    public List<RuntimeEntity> getRuntimeList(Long clusterId) {
        return runtimeService.getRuntimeToFrontByClusterId(clusterId);
    }

    public GetInstanceAndAbnormalNumResponse getRuntimeAndAbnormalNum(Long clusterId) {
        return runtimeService.getRuntimeBaseMessage(clusterId);
    }

    //todo: 一个概念的提示，只有topic和config(以及user和acl 但是现在还没有涉及)，其他的数据是只读的，提供读的接口就可以
}
