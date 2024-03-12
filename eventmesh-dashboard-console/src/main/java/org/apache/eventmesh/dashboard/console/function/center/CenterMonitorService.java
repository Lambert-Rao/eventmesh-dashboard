package org.apache.eventmesh.dashboard.console.function.center;

import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.metadata.service.meta.RuntimeMetaService;

public interface CenterMonitorService {
    public Object getCenterInfo();

    void init(RuntimeMetaService runtimeMetaService, MetadataOperationConfig serviceConfig);

    void close();

}
