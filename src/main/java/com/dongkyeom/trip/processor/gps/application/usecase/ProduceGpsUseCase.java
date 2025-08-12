package com.dongkyeom.trip.processor.gps.application.usecase;

import com.dongkyeom.trip.processor.core.annotation.bean.UseCase;
import com.dongkyeom.trip.processor.gps.presentation.dto.request.GpsRequestDto;

@UseCase
public interface ProduceGpsUseCase {
    void execute(GpsRequestDto gpsRequestDto);
}
