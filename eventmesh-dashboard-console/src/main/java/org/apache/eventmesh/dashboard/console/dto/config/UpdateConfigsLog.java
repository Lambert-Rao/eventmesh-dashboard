package org.apache.eventmesh.dashboard.console.dto.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateConfigsLog {

    private String name;

    private String configProperties;

    private Long clusterId;
}
