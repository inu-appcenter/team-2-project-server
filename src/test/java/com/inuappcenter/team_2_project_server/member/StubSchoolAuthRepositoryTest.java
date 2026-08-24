package com.inuappcenter.team_2_project_server.member;

import com.inuappcenter.team_2_project_server.domain.member.repository.StubSchoolAuthRepository;
import com.inuappcenter.team_2_project_server.global.config.LocalAuthConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StubSchoolAuthRepositoryTest {

    private StubSchoolAuthRepository stubSchoolAuthRepository;

    @BeforeEach
    void setUp() {
        LocalAuthConfig localAuthConfig = new LocalAuthConfig();
        LocalAuthConfig.SeedUser seedUser = new LocalAuthConfig.SeedUser();
        seedUser.setStudentId("local_admin");
        seedUser.setPassword("local_admin");
        seedUser.setRoles(List.of("ROLE_ADMIN"));
        localAuthConfig.setSeedUsers(List.of(seedUser));

        stubSchoolAuthRepository = new StubSchoolAuthRepository(localAuthConfig);
    }

    @Test
    void authenticate_returns_seed_user_role_when_password_matches() {
        var result = stubSchoolAuthRepository.authenticate("local_admin", "local_admin");

        assertThat(result).isPresent();
        assertThat(result.get().studentNumber()).isEqualTo("local_admin");
        assertThat(result.get().role()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void authenticate_returns_empty_when_password_does_not_match() {
        var result = stubSchoolAuthRepository.authenticate("local_admin", "wrong-password");

        assertThat(result).isEmpty();
    }

    @Test
    void authenticate_returns_empty_when_seed_user_does_not_exist() {
        var result = stubSchoolAuthRepository.authenticate("unknown", "unknown");

        assertThat(result).isEmpty();
    }
}
