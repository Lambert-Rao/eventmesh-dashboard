package org.apache.eventmesh.dashboard.console.function.metadata.data;

import org.apache.eventmesh.dashboard.console.enums.StatusEnum;
import org.apache.eventmesh.dashboard.console.function.MetadataOperationConfig;
import org.apache.eventmesh.dashboard.console.function.MetadataServiceTypeEnums;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
public class CenterMetadata extends MetadataOperationConfig {

    {
        this.setServiceTypeEnums(MetadataServiceTypeEnums.CENTER_NACOS);
    }

    private String name;

    private String type;

    private String version;

    private String host;

    private Integer port;

    private String role;

    private String username;

    private String params;

    /**
     * 0: not active, 1: active
     *
     * @see StatusEnum
     */
    @Schema(name = "status", defaultValue = "0", allowableValues = {"0", "1"}, description = "0:inactive, 1:active")
    private Integer status;
}
