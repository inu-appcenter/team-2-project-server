package com.inuappcenter.team_2_project_server.domain.member.repository;

import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "oracle.enabled", havingValue = "true")
public class OracleSchoolAuthRepository implements SchoolAuthRepository {

    private final JdbcTemplate jdbcTemplate;

    public OracleSchoolAuthRepository(@Qualifier("oracleJdbc") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean verify(String studentId, String password) {
        try {
            String result = jdbcTemplate.queryForObject(
                    "SELECT F_LOGIN_CHECK(?, ?) FROM DUAL", String.class, studentId, password);
            return "Y".equals(result);
        } catch (Exception e) {
            throw new MyException(ErrorCode.ORACLE_AUTH_UNAVAILABLE);
        }
    }
}
