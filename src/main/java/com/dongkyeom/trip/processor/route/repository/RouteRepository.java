package com.dongkyeom.trip.processor.route.repository;

import com.dongkyeom.trip.processor.route.domain.Route;

import java.util.List;

public interface RouteRepository {

    void save(Route route);

    Route findByIdOrElseNull(String tripId);

    List<Route> findAll();

    List<Route> findByTripIdOrElseNull(String agentId);
}
