package com.dongkyeom.trip.processor.route.presentation.controller.query;

import com.dongkyeom.trip.processor.core.dto.ResponseDto;
import com.dongkyeom.trip.processor.route.application.usecase.ReadRouteDetailUseCase;
import com.dongkyeom.trip.processor.route.application.usecase.ReadRouteUseCase;
import com.dongkyeom.trip.processor.route.presentation.dto.response.ReadRouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routes")
public class RouteQueryV1Controller {

    private final ReadRouteUseCase readRouteUseCase;
    private final ReadRouteDetailUseCase readRouteDetailUseCase;

    @GetMapping("")
    public ResponseDto<ReadRouteResponseDto> readRoutes() {
        return ResponseDto.ok(readRouteUseCase.execute());
    }

    @GetMapping("/{tripId}/details")
    public ResponseDto<ReadRouteResponseDto> readRouteDetails(
            @PathVariable ("tripId") String tripId
    ) {
        return ResponseDto.ok(readRouteDetailUseCase.execute(tripId));
    }
}
