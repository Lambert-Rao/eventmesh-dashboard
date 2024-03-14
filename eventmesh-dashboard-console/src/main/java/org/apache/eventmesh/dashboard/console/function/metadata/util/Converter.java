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

package org.apache.eventmesh.dashboard.console.function.metadata.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converter is used to convert data between database entity and metadata
 *
 * @param <T> metadata type
 * @param <R> entity type
 */
public interface Converter<T, R> {

    R convert(T source);

    default List<R> convert(List<T> source) {
        List<R> results = new ArrayList<R>(source.size());
        source.forEach(t -> results.add(convert(t)));
        return results;
    }

    String getUnique(T t);

    default Map<String, Object>  getUniqueKeyMap(List<T> source){
        Map<String, Object> newObjectMap = new HashMap<>();
        source.forEach(t -> newObjectMap.put(getUnique(t), t));
        return newObjectMap;
    }

}
