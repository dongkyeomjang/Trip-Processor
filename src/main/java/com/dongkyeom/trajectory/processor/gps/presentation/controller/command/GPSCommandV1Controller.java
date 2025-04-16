package com.dongkyeom.trajectory.processor.gps.presentation.controller.command;

import com.dongkyeom.trajectory.processor.core.dto.ResponseDto;
import com.dongkyeom.trajectory.processor.gps.application.usecase.ProduceGpsUseCase;
import com.dongkyeom.trajectory.processor.gps.presentation.dto.request.GpsRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gps")
public class GPSCommandV1Controller {
    private final ProduceGpsUseCase produceGpsUseCase;

    @PostMapping("")
    public ResponseDto<Void> produceGps(
            @RequestBody @Valid GpsRequestDto gpsRequestDto
    ) {
        produceGpsUseCase.execute(gpsRequestDto);
        return ResponseDto.ok(null);
    }
}
