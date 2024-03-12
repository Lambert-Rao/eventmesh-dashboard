package org.apache.eventmesh.dashboard.console.function.metadata.data;

import org.apache.eventmesh.dashboard.console.function.MetadataServiceTypeEnums;
import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class RuntimeMetadata extends MetadataOperationConfig {


    {
        this.setServiceTypeEnums(MetadataServiceTypeEnums.RUNTIME);
    }

    /**
     * this is used to create cluster info in db if this row does not exist when runtime info is inserted.
     */
    private String clusterName;

    private String host;

    private Integer port;

    private Integer jmxPort;

    private String rack;

    private String endpointMap;
}
