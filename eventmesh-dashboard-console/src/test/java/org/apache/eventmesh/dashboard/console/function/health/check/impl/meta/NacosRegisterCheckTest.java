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

package org.apache.eventmesh.dashboard.console.function.health.check.impl.meta;

import org.apache.eventmesh.dashboard.console.function.health.callback.HealthCheckCallback;
import org.apache.eventmesh.dashboard.console.function.health.check.config.HealthCheckObjectConfig;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class NacosRegistryCheckTest {

    private NacosNamingServiceCheck nacosRegistryCheck;

    @BeforeEach
    public void init() {
        HealthCheckObjectConfig config = new HealthCheckObjectConfig();
        config.setInstanceId(1L);
        config.setHealthCheckResourceType("meta");
        config.setHealthCheckResourceSubType("nacos");
        config.setClusterId(1L);
        config.setConnectUrl("127.0.0.1:8848");
        nacosRegistryCheck = new NacosNamingServiceCheck(config);
    }

    @Test
    public void testDoCheck() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        nacosRegistryCheck.doCheck(new HealthCheckCallback() {
            @Override
            public void onSuccess() {
                latch.countDown();
                log.info("{} success", this.getClass().getSimpleName());
            }

            @Override
            public void onFail(Exception e) {
                latch.countDown();
                log.error("{}, failed for reason {}", this.getClass().getSimpleName(), e);
            }
        });
        latch.await();
    }

    @AfterEach
    public void destroy() {
        nacosRegistryCheck.destroy();
    }
}