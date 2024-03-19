package org.apache.eventmesh.dashboard.console.dto.store;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetBrokerListResponse {

    private Integer storeId;

    private Integer storeType;

    private String host;

    private Integer port;

    private Integer jmxPort;

    private Timestamp startTimestamp;
}
