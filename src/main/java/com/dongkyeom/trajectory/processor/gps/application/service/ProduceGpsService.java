package com.dongkyeom.trajectory.processor.gps.application.service;

import com.dongkyeom.trajectory.processor.gps.presentation.dto.request.GpsRequestDto;
import com.dongkyeom.trajectory.processor.gps.application.usecase.ProduceGpsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProduceGpsService implements ProduceGpsUseCase {

    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    private final static String TOPIC = "raw-gps";

    @Override
    public void execute(GpsRequestDto requestDto) {
        Map<String, Object> gpsData = Map.of(
                "trip_id", requestDto.tripId(),
                "agent_id", requestDto.agentId(),
            "latitude", requestDto.latitude(),
            "longitude", requestDto.longitude(),
            "timestamp", requestDto.timestamp()
        );

        kafkaTemplate.send(TOPIC, requestDto.tripId(), gpsData);
    }
}
