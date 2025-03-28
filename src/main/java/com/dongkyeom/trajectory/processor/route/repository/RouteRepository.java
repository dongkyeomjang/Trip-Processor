package com.dongkyeom.trajectory.processor.route.repository;

import com.dongkyeom.trajectory.processor.route.domain.Route;

public interface RouteRepository {

    void save(Route route);

    Route findByIdOrElseThrow(String tripId);
}
