package com.dongkyeom.trajectory.processor.gps.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record GpsRequestDto(
        @JsonProperty("trip_id")
        @NotBlank(message = "tripId는 필수입니다.")
        String tripId,

        @JsonProperty("agent_id")
        @NotBlank(message = "agentId는 필수입니다.")
        String agentId,

        @JsonProperty("latitude")
        @NotBlank(message = "latitude 는 필수입니다.")
        String latitude,

        @JsonProperty("longitude")
        @NotBlank(message = "longitude 는 필수입니다.")
        String longitude,

        @JsonProperty("timestamp")
        @NotBlank(message = "timestamp는 필수입니다.")
        String timestamp
) {
}
