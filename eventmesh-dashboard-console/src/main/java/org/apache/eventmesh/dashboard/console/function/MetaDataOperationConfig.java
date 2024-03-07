package org.apache.eventmesh.dashboard.console.function;

import lombok.Data;

@Data
public class MetaDataOperationConfig {

    private MetaDataServiceTypeEnums serviceTypeEnums;

    //url
    private String address;
}
