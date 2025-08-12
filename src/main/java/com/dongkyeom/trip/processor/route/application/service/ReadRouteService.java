package com.dongkyeom.trip.processor.route.application.service;

import com.dongkyeom.trip.processor.route.application.usecase.ReadRouteUseCase;
import com.dongkyeom.trip.processor.route.domain.Route;
import com.dongkyeom.trip.processor.route.presentation.dto.response.ReadRouteResponseDto;
import com.dongkyeom.trip.processor.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadRouteService implements ReadRouteUseCase {

    private final RouteRepository routeRepository;

    @Override
    public ReadRouteResponseDto execute() {
        List<Route> routes = routeRepository.findAll();

        List<ReadRouteResponseDto.RouteDto> routeDtos = routes.stream()
                .map(route -> new ReadRouteResponseDto.RouteDto(
                        route.getTripId(),
                        route.getAgentId(),
                        route.getFullGeometry(),
                        route.getLatitudes(),
                        route.getLongitudes()))
                .toList();

        return new ReadRouteResponseDto(routeDtos);
    }
}
