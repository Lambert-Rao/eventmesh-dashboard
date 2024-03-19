package org.apache.eventmesh.dashboard.console.dto.cluster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateClusterRequest {

    private String name;

    private String registerNameList;

    private String bootstrapServers;

    private String eventmeshVersion;

    private String clientProperties;

    private String jmxProperties;

    private String regProperties;

    private String description;
}
