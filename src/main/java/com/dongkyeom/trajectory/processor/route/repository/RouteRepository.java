package com.dongkyeom.trajectory.processor.route.repository;

import com.dongkyeom.trajectory.processor.route.domain.Route;

import java.util.List;

public interface RouteRepository {

    void save(Route route);

    Route findByIdOrElseNull(String tripId);

    List<Route> findAll();
}
