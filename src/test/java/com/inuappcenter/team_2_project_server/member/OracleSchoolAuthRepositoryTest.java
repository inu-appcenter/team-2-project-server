package com.inuappcenter.team_2_project_server.member;

import com.inuappcenter.team_2_project_server.domain.member.repository.OracleSchoolAuthRepository;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class
OracleSchoolAuthRepositoryTest {

    private static final String LOGIN_CHECK_SQL = "SELECT F_LOGIN_CHECK(?, ?) FROM DUAL";

    private JdbcTemplate jdbcTemplate;
    private OracleSchoolAuthRepository oracleSchoolAuthRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        oracleSchoolAuthRepository = new OracleSchoolAuthRepository(jdbcTemplate);
    }

    @Test
    void verify_returns_true_when_oracle_function_returns_y() {
        given(jdbcTemplate.queryForObject(
                eq(LOGIN_CHECK_SQL),
                eq(String.class),
                eq("20240001"),
                eq("password")
        )).willReturn("Y");

        boolean result = oracleSchoolAuthRepository.verify("20240001", "password");

        assertThat(result).isTrue();
    }

    @Test
    void verify_returns_false_when_oracle_function_does_not_return_y() {
        given(jdbcTemplate.queryForObject(
                eq(LOGIN_CHECK_SQL),
                eq(String.class),
                eq("20240001"),
                eq("wrong-password")
        )).willReturn("N");

        boolean result = oracleSchoolAuthRepository.verify("20240001", "wrong-password");

        assertThat(result).isFalse();
    }

    @Test
    void verify_throws_custom_exception_when_oracle_query_fails() {
        given(jdbcTemplate.queryForObject(
                eq(LOGIN_CHECK_SQL),
                eq(String.class),
                eq("20240001"),
                eq("password")
        )).willThrow(new DataAccessResourceFailureException("oracle connection failed"));

        assertThatThrownBy(() -> oracleSchoolAuthRepository.verify("20240001", "password"))
                .isInstanceOf(MyException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.ORACLE_AUTH_UNAVAILABLE);
    }
}
