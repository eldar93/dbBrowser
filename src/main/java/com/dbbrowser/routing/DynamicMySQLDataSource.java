package com.dbbrowser.routing;

import com.dbbrowser.routing.holder.ConnContextHolder;
import com.dbbrowser.routing.token.ConnConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DynamicMySQLDataSource extends AbstractRoutingDataSource {

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    private final ConcurrentMap<String, HikariDataSource> sources = new ConcurrentHashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return ConnContextHolder.getClientConnectionId();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        ConnConfig lookupKey = (ConnConfig) determineCurrentLookupKey();
        addOrUpdateDataSource(lookupKey);
        return sources.get(lookupKey.getId());
    }

    @Override
    public void afterPropertiesSet() {

    }

    private void addOrUpdateDataSource(ConnConfig config) {
        String key = config.getId();
        if (sources.containsKey(key)) {
            HikariDataSource ds = sources.get(key);
            if (!this.isCurrentConfiguration(ds, config)) {
                ds.close();
                sources.put(key, this.buildDataSource(config));
            }
        } else {
            sources.put(key, this.buildDataSource(config));
        }
    }

    private HikariDataSource buildDataSource(ConnConfig connConfig) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(connConfig.getUrl());
        //hikariConfig.setDriverClassName(DRIVER_NAME);
        hikariConfig.setUsername(connConfig.getUser());
        hikariConfig.setPassword(connConfig.getPassword());

        return new HikariDataSource(hikariConfig);
    }

    private boolean isCurrentConfiguration(HikariDataSource dataSource, ConnConfig config) {
        return Objects.equals(dataSource.getJdbcUrl(), config.getUrl())
                && Objects.equals(dataSource.getUsername(), config.getUser())
                && Objects.equals(dataSource.getPassword(), config.getPassword());
    }

}
