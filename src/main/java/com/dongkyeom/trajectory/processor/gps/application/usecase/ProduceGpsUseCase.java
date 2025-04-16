package com.dongkyeom.trajectory.processor.gps.application.usecase;

import com.dongkyeom.trajectory.processor.core.annotation.bean.UseCase;
import com.dongkyeom.trajectory.processor.gps.presentation.dto.request.GpsRequestDto;

@UseCase
public interface ProduceGpsUseCase {
    void execute(GpsRequestDto gpsRequestDto);
}
