package com.dongkyeom.trajectory.processor.gps.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GPS {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    private final String tripId;


    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    private final String agentId;
    private final String latitude;
    private final String longitude;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public GPS(String tripId, String agentId, String latitude, String longitude) {
        this.tripId = tripId;
        this.agentId = agentId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
