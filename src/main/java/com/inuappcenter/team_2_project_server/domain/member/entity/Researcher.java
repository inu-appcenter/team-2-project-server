package com.inuappcenter.team_2_project_server.domain.member.entity;

import com.inuappcenter.team_2_project_server.domain.laboratory.entity.Laboratory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
public class Researcher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String iris;

    @Column(name = "iris_yn")
    boolean irisYN;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratory_id")
    Laboratory laboratory;
}
