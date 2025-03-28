package com.dongkyeom.trajectory.processor.core.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class HBaseConfig {

    @Value("${hbase.zookeeper.quorum}")
    private String zookeeperQuorum;

    @Value("${hbase.zookeeper.property.clientPort}")
    private String zookeeperClientPort;

    @Bean
    public org.apache.hadoop.conf.Configuration hbaseConfiguration() {
        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", zookeeperQuorum);
        config.set("hbase.zookeeper.property.clientPort", zookeeperClientPort);
        return config;
    }

    @Bean(destroyMethod = "close")
    public Connection hbaseConnection(org.apache.hadoop.conf.Configuration configuration) throws IOException {
        return ConnectionFactory.createConnection(configuration);
    }

    @Bean
    public Admin hbaseAdmin(Connection connection) throws IOException {
        return connection.getAdmin();
    }
}
