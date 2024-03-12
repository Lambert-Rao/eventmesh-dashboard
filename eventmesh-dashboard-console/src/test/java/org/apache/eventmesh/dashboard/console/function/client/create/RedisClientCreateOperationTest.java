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

package org.apache.eventmesh.dashboard.console.function.client.create;


import lombok.extern.slf4j.Slf4j;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateRedisConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class RedisClientCreateOperationTest {

    private RedisClientCreateOperation redisClientCreateOperation = new RedisClientCreateOperation();

    @Test
    void createClient() {
        CreateRedisConfig config = new CreateRedisConfig();
        config.setUrl("redis://localhost:6379");
        try {
            String result = redisClientCreateOperation.createClient(config).sync().ping();
            Assertions.assertEquals("PONG", result);
            log.info("RedisClientCreate success");
        } catch (Exception e) {
            log.info("RedisClientCreate failed", e);
        }

    }
}