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

package org.apache.eventmesh.dashboard.console.log.service.Impl;

import org.apache.eventmesh.dashboard.console.entity.LogEntity;
import org.apache.eventmesh.dashboard.console.log.service.LogService;
import org.apache.eventmesh.dashboard.console.mapper.OprLogDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    OprLogDao oprLogDao;

    @Override
    public List<LogEntity> getLogListByCluster() {

        return oprLogDao.getLogList();
    }

    @Override
    public Long addLog(LogEntity logEntity) {

        return oprLogDao.addLog(logEntity);
    }

    @Override
    public Integer updateLog(LogEntity logEntity) {

        return oprLogDao.updateLog(logEntity);
    }
}
