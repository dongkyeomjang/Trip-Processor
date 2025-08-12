package com.dongkyeom.trip.processor.route.presentation.dto.response;

import com.dongkyeom.trip.processor.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadRouteResponseDto extends SelfValidating<ReadRouteResponseDto> {

    @JsonProperty("routes")
    private final List<RouteDto> routes;

    public ReadRouteResponseDto(List<RouteDto> routes) {
        this.routes = routes;
        this.validateSelf();
    }

    @Getter
    public static class RouteDto extends SelfValidating<RouteDto> {
        private final String tripId;
        private final String agentId;
        private final String fullGeometry;
        private final List<Double> latitudes;
        private final List<Double> longitudes;

        public RouteDto(String tripId, String agentId, String fullGeometry,
                        List<Double> latitudes, List<Double> longitudes) {
            this.tripId = tripId;
            this.agentId = agentId;
            this.fullGeometry = fullGeometry;
            this.latitudes = latitudes;
            this.longitudes = longitudes;

            this.validateSelf();
        }
    }
}
