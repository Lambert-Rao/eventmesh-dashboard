package org.apache.eventmesh.dashboard.console.dto.cluster;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class GetClusterListResponse {

    private Long clusterId;

    private String name;

    private String eventmeshVersion;

}
