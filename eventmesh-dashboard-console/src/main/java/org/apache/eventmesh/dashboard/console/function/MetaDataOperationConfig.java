package org.apache.eventmesh.dashboard.console.function;

import lombok.Data;

/**
 * This class is used to represent a piece of metadata, which can be used in create, update or delete operations to metadata service(eventmesh meta
 * center, eventmesh runtime cluster)<p> follow method should be called in init block which is used to indicate the type of metadata:
 * {@code this.setServiceTypeEnums(MetaDataServiceTypeEnums.RUNTIME);}
 */
@Data
public class MetaDataOperationConfig {

    private MetaDataServiceTypeEnums serviceTypeEnums;

    //url
    private String address;
}
