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

import java.util.List;

public interface MetadataHandler<T> {


    default void handler(List<T> addData, List<T> updateData, List<T> deleteData) {
        if (addData != null) {
            addData.forEach(this::addMetadata);
        }
        if (updateData != null) {
            updateData.forEach(this::updateMetadata);
        }
        if (deleteData != null) {
            deleteData.forEach(this::deleteMetadata);
        }
    }

    //metaData: topic, center, etc. add meta is to create a topic.

    void addMetadata(T meta);

    void updateMetadata(T meta) {
        this.addMetadata(meta);
    }

    void deleteMetadata(T meta);

}
