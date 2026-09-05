package com.inuappcenter.team_2_project_server.domain.onboarding.enums;

/**
 * 온보딩 첫 질문 "어떤 목적으로 방문하셨나요?" 의 선택지
 * - RESEARCHER: 학부연구생 / 대학원생 -> 소속 연구실, 연구실 리뷰, (선택) 커피챗까지 등록
 * - EXPLORER: 연구실을 알아보는 중 -> 별도 저장 없이 온보딩만 완료
 */
public enum VisitPurpose {
    RESEARCHER,
    EXPLORER
}
