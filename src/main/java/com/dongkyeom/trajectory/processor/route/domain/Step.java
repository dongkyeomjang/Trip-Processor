package com.dongkyeom.trajectory.processor.route.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Step {

    private final String geometry;

    @Builder
    public Step(String geometry) {
        this.geometry = geometry;
    }

}
