package com.dongkyeom.trip.processor.route.repository.impl;

import com.dongkyeom.trip.processor.core.exception.error.ErrorCode;
import com.dongkyeom.trip.processor.core.exception.type.CommonException;
import com.dongkyeom.trip.processor.route.domain.Route;
import com.dongkyeom.trip.processor.route.repository.RouteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            put.addColumn("info".getBytes(), "latitude".getBytes(), objectMapper.writeValueAsBytes(route.getLatitudes()));
            put.addColumn("info".getBytes(), "longitude".getBytes(), objectMapper.writeValueAsBytes(route.getLongitudes()));


            table.put(put);
            table.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Route findByIdOrElseNull(String tripId) {
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
                    .latitudes(objectMapper.readValue(
                            result.getValue("info".getBytes(), "latitude".getBytes()),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class)))
                    .longitudes(objectMapper.readValue(
                            result.getValue("info".getBytes(), "longitude".getBytes()),
                            objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class)))
                    .build();

            table.close();
            return route;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CommonException e) {
            return null;
        }
    }

    @Override
    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();
        try (Table table = connection.getTable(TableName.valueOf("route"));
             ResultScanner scanner = table.getScanner(new Scan())) {

            for (Result result : scanner) {
                String tripId = Bytes.toString(result.getRow());

                Route route = Route.builder()
                        .tripId(tripId)
                        .agentId(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("agentId"))))
                        .fullGeometry(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("geometry"))))
                        .latitudes(objectMapper.readValue(
                                result.getValue(Bytes.toBytes("info"), Bytes.toBytes("latitude")),
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class)))
                        .longitudes(objectMapper.readValue(
                                result.getValue(Bytes.toBytes("info"), Bytes.toBytes("longitude")),
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class)))
                        .build();

                routes.add(route);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return routes;
    }

    @Override
    public List<Route> findByTripIdOrElseNull(String tripId) {
        List<Route> routes = new ArrayList<>();
        try (Table table = connection.getTable(TableName.valueOf("route"));
             ResultScanner scanner = table.getScanner(new Scan().setFilter(new org.apache.hadoop.hbase.filter.SingleColumnValueFilter(
                     Bytes.toBytes("info"), Bytes.toBytes("tripId"), org.apache.hadoop.hbase.filter.CompareFilter.CompareOp.EQUAL, Bytes.toBytes(tripId))))) {

            for (Result result : scanner) {
                String tripIdFromResult = Bytes.toString(result.getRow());

                Route route = Route.builder()
                        .tripId(tripIdFromResult)
                        .agentId(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("agentId"))))
                        .fullGeometry(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("geometry"))))
                        .latitudes(objectMapper.readValue(
                                result.getValue(Bytes.toBytes("info"), Bytes.toBytes("latitude")),
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class)))
                        .longitudes(objectMapper.readValue(
                                result.getValue(Bytes.toBytes("info"), Bytes.toBytes("longitude")),
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class)))
                        .build();

                routes.add(route);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return routes;
    }
}
