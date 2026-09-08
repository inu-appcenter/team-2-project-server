package com.inuappcenter.team_2_project_server.domain.bugReport.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    INVALID_INFO("허위정보"),
    ERROR("버그/오류"),
    ETC("기타");


    private final String description;
}
