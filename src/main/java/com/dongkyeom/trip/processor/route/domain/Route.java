package com.dongkyeom.trip.processor.route.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Route {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    private final String tripId;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    private final String agentId;

    private final String fullGeometry;

    private final List<Double> latitudes;

    private final List<Double> longitudes;

    @Builder
    public Route(String tripId, String agentId, String fullGeometry,
                 List<Double> latitudes, List<Double> longitudes) {
        this.tripId = tripId;
        this.agentId = agentId;
        this.fullGeometry = fullGeometry;
        this.latitudes = latitudes;
        this.longitudes = longitudes;
    }
}
