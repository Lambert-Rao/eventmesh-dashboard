package org.apache.eventmesh.dashboard.console.dto.topic;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetTopicListResponse {

    private Long topicId;

    private String topicName;

    @Schema(description = "state of a health check, 0: failed, 1: passed, 2: doing check, 3: out of time")
    private Integer healthStatus;

    private String desc;
}
