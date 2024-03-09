package org.apache.eventmesh.dashboard.console.function.center.meta;

import org.apache.eventmesh.dashboard.console.function.MetaDataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.center.CenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.client.ClientManager;
import org.apache.eventmesh.dashboard.console.function.client.ClientTypeEnum;
import org.apache.eventmesh.dashboard.console.function.client.config.CreateNacosConfig;
import org.apache.eventmesh.dashboard.console.function.client.wrapper.NacosClientWrapper;
import org.apache.eventmesh.dashboard.console.function.metadata.service.RuntimeMetaService;

import javafx.util.Pair;

public class NacosCenterMonitorService implements CenterMonitorService {
    private String clientKey;
    private NacosClientWrapper nacosClient;

    @Override
    public Object getCenterInfo() {
        return null;
    }

    @Override
    public void init(RuntimeMetaService runtimeMetaService, MetaDataOperationConfig serviceConfig) {
        CreateNacosConfig createNacosConfig = new CreateNacosConfig();
        createNacosConfig.setServerAddress(serviceConfig.getAddress());
        Pair<String, NacosClientWrapper> result = ClientManager.getInstance().createClient(ClientTypeEnum.CENTER_NACOS, createNacosConfig);
        clientKey = result.getKey();
        nacosClient = result.getValue();
    }

    @Override
    public void close() {

    }
}
