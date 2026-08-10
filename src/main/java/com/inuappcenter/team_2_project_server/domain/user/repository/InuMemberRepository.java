package com.inuappcenter.team_2_project_server.domain.user.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "oracle.enabled", havingValue = "true")
public class InuMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public InuMemberRepository(@Qualifier("oracleJdbc") JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public boolean verify(String studentId, String password){
        try{
            String result = jdbcTemplate.queryForObject(
                    "SELECT F_LOGIN_CHECK(?, ?) FROM DUAL", String.class, studentId, password);
            return "Y".equals(result);
        }catch (Exception e) {
            return false;
        }
    }
}
