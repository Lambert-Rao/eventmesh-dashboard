package org.apache.eventmesh.dashboard.console.function.center;

import org.apache.eventmesh.dashboard.console.function.MetaDataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.metadata.service.RuntimeMetaService;

public interface CenterMonitorService {


    void init(RuntimeMetaService runtimeMetaService, MetaDataOperationConfig serviceConfig);

    void close();

}
