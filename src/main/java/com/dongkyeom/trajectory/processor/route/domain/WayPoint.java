package com.dongkyeom.trajectory.processor.route.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WayPoint {

    private final String[] location;

    @Builder
    public WayPoint(String[] location) {
        this.location = location;
    }
}
