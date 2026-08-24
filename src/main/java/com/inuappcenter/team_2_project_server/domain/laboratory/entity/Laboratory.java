package com.inuappcenter.team_2_project_server.domain.laboratory.entity;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;
import com.inuappcenter.team_2_project_server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "laboratory",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_laboratory_lab_name_professor",
                        columnNames = {"lab_name", "professor_id"}
                )
        }
)
public class Laboratory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laboratory_id")
    Long id;

    @Enumerated(EnumType.STRING)
    College college;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Department department;

    @Column(name = "lab_name", nullable = false)
    String labName;

    String location;

    Long capacity;

    String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    Professor professor;

    @Column(name = "lab_url")
    String labUrl;

    // 엑셀 원문을 그대로 저장
    @Column(name = "research_field_raw")
    String researchFieldRaw;

    private Laboratory(
            College college,
            Department department,
            String labName,
            String location,
            Long capacity,
            String introduction,
            Professor professor,
            String labUrl,
            String researchFieldRaw
    ) {
        this.college = college;
        this.department = department;
        this.labName = labName;
        this.location = location;
        this.capacity = capacity;
        this.introduction = introduction;
        this.professor = professor;
        this.labUrl = labUrl;
        this.researchFieldRaw = researchFieldRaw;
    }

    public static Laboratory create(
            College college,
            Department department,
            String labName,
            String location,
            Long capacity,
            String introduction,
            Professor professor,
            String labUrl,
            String researchFieldRaw
    ) {
        return new Laboratory(college, department, labName, location, capacity, introduction, professor, labUrl, researchFieldRaw);
    }

    public void updateLab(
            String labName,
            String location,
            Long capacity,
            String introduction,
            String labUrl,
            String researchFieldRaw
    ) {
        this.labName = labName;
        this.location = location;
        this.capacity = capacity;
        this.introduction = introduction;
        this.labUrl = labUrl;
        this.researchFieldRaw = researchFieldRaw;
    }
}
