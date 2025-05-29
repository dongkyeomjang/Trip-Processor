package com.dongkyeom.trajectory.processor.route.presentation.controller.query;

import com.dongkyeom.trajectory.processor.core.dto.ResponseDto;
import com.dongkyeom.trajectory.processor.route.application.usecase.ReadRouteUseCase;
import com.dongkyeom.trajectory.processor.route.presentation.dto.response.ReadRouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routes")
public class RouteQueryV1Controller {

    private final ReadRouteUseCase readRouteUseCase;

    @GetMapping("")
    public ResponseDto<ReadRouteResponseDto> readRoutes() {
        return ResponseDto.ok(readRouteUseCase.execute());
    }
}
