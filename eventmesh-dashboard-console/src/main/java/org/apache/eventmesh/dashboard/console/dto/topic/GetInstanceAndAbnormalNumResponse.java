package org.apache.eventmesh.dashboard.console.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetInstanceAndAbnormalNumResponse {

    private Integer instanceCount;

    private Integer malfunctionCount;
}
