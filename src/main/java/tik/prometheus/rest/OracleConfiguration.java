package tik.prometheus.rest;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class OracleConfiguration {
    @Value("${database.host}")
    String host;
    @Value("${database.port}")
    Integer port;
    @Value("${database.username}")
    String username;
    @Value("${database.password}")
    String password;
    @Value("${database.service}")
    String service;
    @Value("${database.schema}")
    String schema;

    @Bean
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        String url = "jdbc:oracle:thin:@//%s:%s/%s".formatted(host, port, service);
        System.out.println(url);
        dataSource.setURL(url);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }
}