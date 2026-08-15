package com.inuappcenter.team_2_project_server.domain.member.repository;

// 해당 구현체를 통해 로컬에서 oracle.enabled 값에 따라 true면 Oracle, false면 Stud를 사용한다.
public interface SchoolAuthRepository {
    boolean verify(String studentId, String password);
}
