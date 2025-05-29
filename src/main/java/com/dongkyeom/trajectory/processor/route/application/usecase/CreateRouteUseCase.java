package com.dongkyeom.trajectory.processor.route.application.usecase;

import com.dongkyeom.trajectory.processor.core.annotation.bean.UseCase;
import com.dongkyeom.trajectory.processor.route.presentation.dto.request.RouteRequestDto;

@UseCase
public interface CreateRouteUseCase {

    void execute(RouteRequestDto requestDto);
}
