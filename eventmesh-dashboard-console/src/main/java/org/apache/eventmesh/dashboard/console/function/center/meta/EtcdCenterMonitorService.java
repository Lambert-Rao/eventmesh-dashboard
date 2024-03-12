package org.apache.eventmesh.dashboard.console.function.center.meta;

import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.center.CenterMonitorService;
import org.apache.eventmesh.dashboard.console.function.metadata.service.meta.RuntimeMetaService;

public class EtcdCenterMonitorService implements CenterMonitorService {

    private RuntimeMetaService runtimeMetaService;

    @Override
    public void init(RuntimeMetaService runtimeMetaService , MetadataOperationConfig serviceConfig) {
        this.runtimeMetaService = runtimeMetaService;

    }

    @Override
    public void close() {

    }
}
