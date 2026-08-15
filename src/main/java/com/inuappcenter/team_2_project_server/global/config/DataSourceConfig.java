package com.inuappcenter.team_2_project_server.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 서비스의 메인 DB연결을 등록하는 설정 클래스
 */
@Configuration
public class DataSourceConfig {

    /**
     * spring.datasource를 기준으로 메인 서비스 DB를 확정
     */
    @Primary // 해당 어노테이션으로 메인 DB를 결정
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
}
