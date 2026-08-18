package com.inuappcenter.team_2_project_server.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProfessorPosition {
    ASSISTANT("조교수"),
    ASSOCIATE("부교수"),
    FULL("정교수"),
    CHAIR("석좌교수"),
    VISITING("초빙교"),
    ADJUNCT("겸임교수"),
    RESEARCH("연구교수"),
    LECTURER("시간강사"),
    EMERITUS("명예교수"),
    ;

    private final String description;
}
