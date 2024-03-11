package org.apache.eventmesh.dashboard.console.function.center;

import org.apache.eventmesh.dashboard.console.function.MetaDataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.metadata.service.meta.RuntimeMetaService;

public interface CenterMonitorService {
    public Object getCenterInfo();

    void init(RuntimeMetaService runtimeMetaService, MetaDataOperationConfig serviceConfig);

    void close();

}
