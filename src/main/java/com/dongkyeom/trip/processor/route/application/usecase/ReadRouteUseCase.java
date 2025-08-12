package com.dongkyeom.trip.processor.route.application.usecase;

import com.dongkyeom.trip.processor.core.annotation.bean.UseCase;
import com.dongkyeom.trip.processor.route.presentation.dto.response.ReadRouteResponseDto;

@UseCase
public interface ReadRouteUseCase {

    ReadRouteResponseDto execute();
}
