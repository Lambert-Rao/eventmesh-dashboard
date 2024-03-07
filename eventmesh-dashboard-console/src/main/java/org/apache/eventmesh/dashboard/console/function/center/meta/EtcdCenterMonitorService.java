package org.apache.eventmesh.dashboard.console.function.center.meta;

import org.apache.eventmesh.dashboard.console.function.MetaDataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.center.CenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.metadata.service.RuntimeMetaService;

public class EtcdCenterMonitorService implements CenterMonitorService {

    private RuntimeMetaService runtimeMetaService;

    @Override
    public void init(RuntimeMetaService runtimeMetaService , MetaDataOperationConfig serviceConfig) {
        this.runtimeMetaService = runtimeMetaService;

    }

    @Override
    public void close() {

    }
}
