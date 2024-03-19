package org.apache.eventmesh.dashboard.console.dto.cluster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetResourceNumResponse {

    private Integer topics;

    private Integer connections;


}
