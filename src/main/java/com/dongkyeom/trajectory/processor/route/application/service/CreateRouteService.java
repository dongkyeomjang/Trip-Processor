package com.dongkyeom.trajectory.processor.route.application.service;

import com.dongkyeom.trajectory.processor.core.dto.RouteResponseDto;
import com.dongkyeom.trajectory.processor.core.utility.OSRMUtil;
import com.dongkyeom.trajectory.processor.core.utility.PolylineMergeUtil;
import com.dongkyeom.trajectory.processor.core.utility.RestClientUtil;
import com.dongkyeom.trajectory.processor.route.application.usecase.CreateRouteUseCase;
import com.dongkyeom.trajectory.processor.route.domain.Route;
import com.dongkyeom.trajectory.processor.route.presentation.dto.request.RouteRequestDto;
import com.dongkyeom.trajectory.processor.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateRouteService implements CreateRouteUseCase {

    private final RouteRepository routeRepository;
    private final RestClientUtil restClientUtil;
    private final OSRMUtil osrmUtil;

    @Override
    public void execute(RouteRequestDto requestDto) {
        // osrm 호출
        String url = osrmUtil.createOSRMRequestUrl(
                requestDto.from().latitude(),
                requestDto.from().longitude(),
                requestDto.to().latitude(),
                requestDto.to().longitude()
        );

        JSONObject jsonObject = restClientUtil.sendGetMethod(url);
        Route route = routeRepository.findByIdOrElseNull(requestDto.to().tripId());

        try {
            RouteResponseDto routeResponseDto = osrmUtil.mapToRouteResponseDto(jsonObject);

            if (route == null) {
                List<Double> latitudes = new ArrayList<>();
                List<Double> longitudes = new ArrayList<>();

                latitudes.add(requestDto.from().latitude());
                latitudes.add(requestDto.to().latitude());
                longitudes.add(requestDto.from().longitude());
                longitudes.add(requestDto.to().longitude());

                route = Route.builder()
                        .tripId(requestDto.to().tripId())
                        .agentId(requestDto.to().agentId())
                        .fullGeometry(routeResponseDto.routes().get(0).geometry())
                        .latitudes(latitudes)
                        .longitudes(longitudes)
                        .build();
                // 새로운 경로 저장
                routeRepository.save(route);

            } else {
                // 기존 경로에 새로운 geometry 추가
                String mergedGeometry = PolylineMergeUtil.mergePolylines(
                        route.getFullGeometry(),
                        routeResponseDto.routes().get(0).geometry()
                );

                route.getLatitudes().add(requestDto.to().latitude());
                route.getLongitudes().add(requestDto.to().longitude());

                route = Route.builder()
                        .tripId(route.getTripId())
                        .agentId(route.getAgentId())
                        .fullGeometry(mergedGeometry)
                        .latitudes(route.getLatitudes())
                        .longitudes(route.getLongitudes())
                        .build();

                routeRepository.save(route);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
