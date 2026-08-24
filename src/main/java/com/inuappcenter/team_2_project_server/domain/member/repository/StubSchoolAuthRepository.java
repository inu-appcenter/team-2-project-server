package com.inuappcenter.team_2_project_server.domain.member.repository;

import com.inuappcenter.team_2_project_server.domain.member.dto.LocalAuthLoginDto;
import com.inuappcenter.team_2_project_server.global.config.LocalAuthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "oracle.enabled",
        havingValue = "false",
        matchIfMissing = true
)
public class StubSchoolAuthRepository implements SchoolAuthRepository {

    private final LocalAuthConfig localAuthConfig;

    @Override
    public Optional<LocalAuthLoginDto> authenticate(String studentId, String password) {
        return localAuthConfig.findByStudentId(studentId)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> new LocalAuthLoginDto(
                        user.getStudentId(),
                        user.primaryRole()
                ));
    }

}
