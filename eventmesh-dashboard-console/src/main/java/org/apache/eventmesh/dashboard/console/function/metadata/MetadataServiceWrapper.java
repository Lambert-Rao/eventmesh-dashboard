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

package org.apache.eventmesh.dashboard.console.function.metadata;

import org.apache.eventmesh.dashboard.console.function.metadata.util.Converter;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 不同的Metadata处理方式不一样，center需要在dbToService注册了center信息之后，才能从ServiceToDb获取数据，而topic则可以在注册的时候就获取serviceToDB数据
 */
@Data
public class MetadataServiceWrapper {

    private SingleMetadataServiceWrapper dbToService;

    private SingleMetadataServiceWrapper serviceToDb;




    @Data
    @AllArgsConstructor
    public static class SingleMetadataServiceWrapper {

        private Boolean cache;

        private Class<?> clazz;

        private Converter<Object, Object> metadataConverter;

        private SyncDataService<Object> syncService;

        private MetadataHandler<Object> metaService;

    }
}
