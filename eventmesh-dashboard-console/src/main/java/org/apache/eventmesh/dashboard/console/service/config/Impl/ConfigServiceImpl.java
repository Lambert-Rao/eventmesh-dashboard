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

package org.apache.eventmesh.dashboard.console.service.config.Impl;

import org.apache.eventmesh.dashboard.console.dto.config.UpdateConfigsLog;
import org.apache.eventmesh.dashboard.console.entity.config.ConfigEntity;
import org.apache.eventmesh.dashboard.console.mapper.config.ConfigMapper;
import org.apache.eventmesh.dashboard.console.service.config.ConfigService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class ConfigServiceImpl implements ConfigService {


    @Autowired
    ConfigMapper configMapper;


    @Override
    public List<ConfigEntity> getConnectorConfigsByClassAndVersion(String classType, String version) {
        ConfigEntity config = new ConfigEntity();
        config.setBusinessType(classType);
        List<ConfigEntity> configEntityList = configMapper.selectConnectorConfigsByBusinessType(config);
        configEntityList.forEach(n -> {
            if (!n.matchVersion(version)) {
                configEntityList.remove(n);
            }
        });
        return configEntityList;
    }

    @Override
    public List<String> getConnectorClasses(String type) {
        ConfigEntity config = new ConfigEntity();
        config.setBusinessType(type);
        return configMapper.selectConnectorBusinessType(config);
    }

    @Override
    public void logUpdateRuntimeConfigs(UpdateConfigsLog updateConfigsLog) {

    }

    @Override
    public void logUpdateStoreConfigs(UpdateConfigsLog updateConfigsLog) {

    }

    @Override
    public void logUpdateConnectorConfigs(UpdateConfigsLog updateConfigsLog) {

    }

    @Override
    public void logUpdateTopicConfigs(UpdateConfigsLog updateConfigsLog) {

    }

    @Override
    public void updateConfigsByInstanceId(String name, List<ConfigEntity> configEntityList) {
        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<>();
        configEntityList.forEach(n -> {
            stringStringConcurrentHashMap.put(n.getConfigName(), n.getConfigValue());
            this.updateConfig(n);
        });
        UpdateConfigsLog updateConfigsLog =
            new UpdateConfigsLog(name, this.mapToProperties(stringStringConcurrentHashMap), configEntityList.get(0).getClusterId());
        switch (configEntityList.get(0).getInstanceType()) {
            case 0:
                this.logUpdateRuntimeConfigs(updateConfigsLog);
                break;
            case 1:
                this.logUpdateRuntimeConfigs(updateConfigsLog);
                break;
            case 2:
                this.logUpdateRuntimeConfigs(updateConfigsLog);
                break;
            case 3:
                this.logUpdateRuntimeConfigs(updateConfigsLog);
                break;
            default:
                break;
        }
    }


    @Override
    public List<ConfigEntity> selectAll() {
        return configMapper.selectAll();
    }

    @Override
    public void batchInsert(List<ConfigEntity> configEntityList) {
        configMapper.batchInsert(configEntityList);
    }

    @Override
    public String mapToYaml(Map<String, String> stringMap) {
        Yaml yaml = new Yaml();
        return yaml.dumpAsMap(stringMap);
    }

    @Override
    public String mapToProperties(Map<String, String> stringMap) {
        Properties properties = new Properties();
        stringMap.forEach((key, value) -> {
            properties.setProperty(key, value);
        });
        return properties.toString().replace(",", ",\n");
    }

    @Override
    public Map<String, String> propertiesToMap(String configProperties) {
        ConcurrentHashMap<String, String> stringStringConcurrentHashMap = new ConcurrentHashMap<>();
        String replace = configProperties.replace("{", "");
        String replace1 = replace.replace("}", "");
        String[] split = replace1.split(",");
        Arrays.stream(split).forEach(n -> {
            String[] split1 = n.split("=");
            stringStringConcurrentHashMap.put(split1[0].replace("\n ", ""), split1[1]);
        });
        return stringStringConcurrentHashMap;
    }

    @Override
    public Integer addConfig(ConfigEntity configEntity) {
        return configMapper.addConfig(configEntity);
    }

    @Override
    public Integer deleteConfig(ConfigEntity configEntity) {
        return configMapper.deleteConfig(configEntity);
    }

    @Override
    public List<ConfigEntity> selectByInstanceId(Long instanceId, Integer type) {
        ConfigEntity config = new ConfigEntity();
        config.setInstanceId(instanceId);
        config.setInstanceType(type);
        return configMapper.selectByInstanceId(config);
    }

    @Override
    public List<ConfigEntity> selectDefaultConfig(ConfigEntity configEntity) {
        return configMapper.selectDefaultConfig(configEntity);
    }

    @Override
    public void updateConfig(ConfigEntity configEntity) {
        configMapper.updateConfig(configEntity);
    }


}
