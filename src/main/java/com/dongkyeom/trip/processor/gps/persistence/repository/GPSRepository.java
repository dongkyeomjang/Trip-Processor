package com.dongkyeom.trip.processor.gps.persistence.repository;

import com.dongkyeom.trip.processor.gps.domain.GPS;

public interface GPSRepository {

    void save(GPS gps);

    GPS findByIdOrElseThrow(String tripId);
}
