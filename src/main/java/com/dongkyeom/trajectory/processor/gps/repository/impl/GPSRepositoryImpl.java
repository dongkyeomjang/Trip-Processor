package com.dongkyeom.trajectory.processor.gps.repository.impl;

import com.dongkyeom.trajectory.processor.core.exception.error.ErrorCode;
import com.dongkyeom.trajectory.processor.core.exception.type.CommonException;
import com.dongkyeom.trajectory.processor.gps.domain.GPS;
import com.dongkyeom.trajectory.processor.gps.repository.GPSRepository;
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
public class GPSRepositoryImpl implements GPSRepository {

    private final Connection connection;

    @Override
    public void save(GPS gps) {
        try {
            Table table = connection.getTable(TableName.valueOf("gps"));
            Put put = new Put(gps.getTripId().getBytes());
            put.addColumn("info".getBytes(), "agentId".getBytes(), gps.getAgentId().getBytes());
            put.addColumn("info".getBytes(), "latitude".getBytes(), gps.getLatitude().getBytes());
            put.addColumn("info".getBytes(), "longitude".getBytes(), gps.getLongitude().getBytes());
            table.put(put);
            table.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GPS findByIdOrElseThrow(String tripId) {
        try {
            Table table = connection.getTable(TableName.valueOf("gps"));
            Get get = new Get(tripId.getBytes());
            Result result = table.get(get);

            if (result.isEmpty()) {
                throw new CommonException(ErrorCode.NOT_FOUND_GPS);
            }

            GPS gps = GPS.builder()
                    .tripId(tripId)
                    .agentId(new String(result.getValue("info".getBytes(), "agentId".getBytes())))
                    .latitude(new String(result.getValue("info".getBytes(), "latitude".getBytes())))
                    .longitude(new String(result.getValue("info".getBytes(), "longitude".getBytes())))
                    .build();

            table.close();
            return gps;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CommonException e) {
            throw new CommonException(ErrorCode.NOT_FOUND_GPS);
        }
    }
}
