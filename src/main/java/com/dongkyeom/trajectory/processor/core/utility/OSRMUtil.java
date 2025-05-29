package com.dongkyeom.trajectory.processor.core.utility;

import com.dongkyeom.trajectory.processor.core.dto.RouteResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OSRMUtil {

    @Value("${osrm.url}")
    private String osrmUrl;

    @Value("${osrm.path}")
    private String path;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createOSRMRequestUrl(double startLat, double startLon, double endLat, double endLon) {
        return UriComponentsBuilder.fromHttpUrl(osrmUrl)
                .path(path)
                .path("/{startLon},{startLat};{endLon},{endLat}")
                .queryParam("steps", "false")
                .queryParam("skip_waypoints", "true")
                .buildAndExpand(startLon, startLat, endLon, endLat)
                .toUriString();
    }

    public RouteResponseDto mapToRouteResponseDto(JSONObject jsonObject) throws Exception {
        return objectMapper.readValue(jsonObject.toString(), RouteResponseDto.class);
    }



}
