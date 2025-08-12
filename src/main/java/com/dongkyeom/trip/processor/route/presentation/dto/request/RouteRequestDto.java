package com.dongkyeom.trip.processor.route.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RouteRequestDto(
        @JsonProperty("from")
        GpsDto from,
        @JsonProperty("to")
        GpsDto to
) {
    public record GpsDto(
            @JsonProperty("trip_id")
            String tripId,
            @JsonProperty("agent_id")
            String agentId,
            @JsonProperty("latitude")
            Double latitude,
            @JsonProperty("longitude")
            Double longitude
    ) {
    }
}
