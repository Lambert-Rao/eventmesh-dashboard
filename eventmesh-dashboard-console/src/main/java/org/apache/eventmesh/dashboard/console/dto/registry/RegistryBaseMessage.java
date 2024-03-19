package org.apache.eventmesh.dashboard.console.dto.registry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistryBaseMessage {

    private String name;

    private String type;

    private String host;

    private Integer followerNum;

    private Integer followerLiveNum;


}
