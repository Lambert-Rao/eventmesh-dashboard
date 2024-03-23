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

import org.apache.eventmesh.dashboard.console.dto.connection.ConnectionListResponse;
import org.apache.eventmesh.dashboard.console.service.connection.ConnectionDataService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController {

    @Autowired
    private ConnectionDataService connectionDataService;

    public List<ConnectionListResponse> getConnectionList(Long clusterId) {
        return connectionDataService.getConnectionToFrontByCluster(clusterId);
    }
    //TODO 问一下获取list的时候是只获取基本信息(id,然后通过id再次查询)，还是获取全部详细信息
    //TODO 需要做一下分页查询，所有列表都需要

    //TODO create connection
    public Response createConnection(Request) {
//        request 包含 runtime ip port
//            connector新建，runtime由ip指定
    }
}
