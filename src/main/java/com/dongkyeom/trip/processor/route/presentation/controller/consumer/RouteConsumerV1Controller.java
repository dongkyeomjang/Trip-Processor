package com.dongkyeom.trip.processor.route.presentation.controller.consumer;

import com.dongkyeom.trip.processor.route.application.usecase.CreateRouteUseCase;
import com.dongkyeom.trip.processor.route.presentation.dto.request.RouteRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class RouteConsumerV1Controller {

    private final ObjectMapper objectMapper;
    private final CreateRouteUseCase createRouteUseCase;

    private final static String TOPIC = "create-route-trigger";

    @KafkaListener(
            topics = TOPIC
    )
    public void consumeCreateRouteTrigger(
            ConsumerRecord<String, Map<String, Object>> record
    ) {
        Map<String, Object> payload = record.value();

        RouteRequestDto routeRequestDto = objectMapper.convertValue(payload, RouteRequestDto.class);

        createRouteUseCase.execute(
                routeRequestDto
        );

    }

    @KafkaListener(
            topics = "raw-gps"
    )
    public void consumeRawGps(
            ConsumerRecord<String, Map<String, Object>> record
    ) {
    }
}
