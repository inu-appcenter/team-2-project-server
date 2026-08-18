package com.inuappcenter.team_2_project_server.domain.laboratory.entity;

import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laboratory_id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Department department;

    @Column(name = "lab_name", nullable = false)
    String labName;

    String location;

    Long capacity;

    String introduction;

    String area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    Professor professor;

    @Column(name = "lab_url")
    String labUrl;
}
