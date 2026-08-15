package com.inuappcenter.team_2_project_server.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * SSO 로그인을 위한 학교 OracleDB 등록 설정 클래스
 */
@Configuration
@ConditionalOnProperty(name = "oracle.enabled", havingValue = "true") // oracle.enabled=true가 설정되어 있을 때만 이 설정 클래스가 활성화
public class OracleConfig {

    /**
     * ooracle.datasource.* 설정을 읽어서 Oracle용 DataSource 생성
     */
    @Bean
    @ConfigurationProperties(prefix = "oracle.datasource")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * 해당 JdbcTemplate을 주입받아 F_LOGIN_CHECK(?, ?)를 호출
     */
    @Bean
    public JdbcTemplate oracleJdbc(@Qualifier("oracleDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
