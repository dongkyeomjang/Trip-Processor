package com.dongkyeom.trajectory.processor.route.domain;

import lombok.Builder;
import lombok.Getter;

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

    private final Step[] steps;

    private final WayPoint[] wayPoints;

    @Builder
    public Route(String tripId, String agentId, String fullGeometry, Step[] steps, WayPoint[] wayPoints) {
        this.tripId = tripId;
        this.agentId = agentId;
        this.fullGeometry = fullGeometry;
        this.steps = steps;
        this.wayPoints = wayPoints;
    }
}
