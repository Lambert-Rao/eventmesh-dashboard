package org.apache.eventmesh.dashboard.console.function.metadata.data;

import org.apache.eventmesh.dashboard.console.function.MetaDataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.MetaDataServiceTypeEnums;

import lombok.Data;

@Data
public class CenterMetaData extends MetaDataOperationConfig {

    private String address;

    {
        this.setServiceTypeEnums(MetaDataServiceTypeEnums.CENTER_NACOS);
    }
}
