package com.inuappcenter.team_2_project_server.domain.member.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(
        name = "oracle.enabled",
        havingValue = "false",
        matchIfMissing = true
)
public class StubSchoolAuthRepository implements SchoolAuthRepository {

    @Override
    public boolean verify(String studentId, String password) {
        return "local_user".equals(studentId) && "local_user".equals(password)
                || ("local_admin".equals(studentId) && "local_admin".equals(password));
    }
}
