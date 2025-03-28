package com.dongkyeom.trajectory.processor.route.repository.impl;

import com.dongkyeom.trajectory.processor.core.exception.error.ErrorCode;
import com.dongkyeom.trajectory.processor.core.exception.type.CommonException;
import com.dongkyeom.trajectory.processor.route.domain.Route;
import com.dongkyeom.trajectory.processor.route.domain.Step;
import com.dongkyeom.trajectory.processor.route.domain.WayPoint;
import com.dongkyeom.trajectory.processor.route.repository.RouteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {

    private final Connection connection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void save(Route route) {
        try {
            Table table = connection.getTable(TableName.valueOf("route"));
            Put put = new Put(route.getTripId().getBytes());
            put.addColumn("info".getBytes(), "agentId".getBytes(), route.getAgentId().getBytes());
            put.addColumn("info".getBytes(), "geometry".getBytes(), route.getFullGeometry().getBytes());

            String stepsJson = objectMapper.writeValueAsString(route.getSteps());
            put.addColumn("info".getBytes(), "steps".getBytes(), stepsJson.getBytes());

            String waypointsJson = objectMapper.writeValueAsString(route.getWayPoints());
            put.addColumn("info".getBytes(), "waypoints".getBytes(), waypointsJson.getBytes());

            table.put(put);
            table.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Route findByIdOrElseThrow(String tripId) {
        try {
            Table table = connection.getTable(TableName.valueOf("route"));
            Get get = new Get(tripId.getBytes());
            Result result = table.get(get);

            if (result.isEmpty()) {
                throw new CommonException(ErrorCode.NOT_FOUND_ROUTE);
            }

            Route route = Route.builder()
                    .tripId(tripId)
                    .agentId(new String(result.getValue("info".getBytes(), "agentId".getBytes())))
                    .fullGeometry(new String(result.getValue("info".getBytes(), "geometry".getBytes())))
                    .steps(objectMapper.readValue(new String(result.getValue("info".getBytes(), "steps".getBytes())), Step[].class))
                    .wayPoints(objectMapper.readValue(new String(result.getValue("info".getBytes(), "waypoints".getBytes())), WayPoint[].class))
                    .build();

            table.close();
            return route;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CommonException e) {
            throw new CommonException(ErrorCode.NOT_FOUND_ROUTE);
        }
    }
}
