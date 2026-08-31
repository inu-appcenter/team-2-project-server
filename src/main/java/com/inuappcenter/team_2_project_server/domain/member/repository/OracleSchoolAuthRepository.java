package com.inuappcenter.team_2_project_server.domain.member.repository;

import com.inuappcenter.team_2_project_server.domain.member.dto.LocalAuthLoginDto;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@ConditionalOnProperty(
        name = "oracle.enabled",
        havingValue = "true"
)
public class OracleSchoolAuthRepository implements SchoolAuthRepository {

    private final JdbcTemplate jdbcTemplate;

    public OracleSchoolAuthRepository(@Qualifier("oracleJdbc") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<LocalAuthLoginDto> authenticate(String studentId, String password) {
        try {
            String result = jdbcTemplate.queryForObject(
                    "SELECT F_LOGIN_CHECK(?, ?) FROM DUAL", String.class, studentId, password);
            if (!"Y".equals(result)) {
                return Optional.empty();
            }
            return Optional.of(new LocalAuthLoginDto(studentId, "ROLE_USER"));

        } catch (Exception e) {
            log.error("Oracle 인증 연동 실패 (studentId={})", studentId, e);
            throw new MyException(ErrorCode.ORACLE_AUTH_UNAVAILABLE);
        }
    }
}
