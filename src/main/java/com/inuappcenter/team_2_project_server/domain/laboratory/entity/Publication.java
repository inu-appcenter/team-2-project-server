package com.inuappcenter.team_2_project_server.domain.laboratory.entity;

import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratory_id")
    Laboratory laboratory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    Professor professor;

    String title;

    String researchersRaw;

    String platform;

    String year;

    String type;

    String status;

    String doi;

    @Column(name = "source_url")
    String sourceURL;

    private Publication(
            Laboratory laboratory,
            Professor professor,
            String title,
            String researchersRaw,
            String platform,
            String year,
            String type,
            String status,
            String doi,
            String sourceURL
    ) {
        this.laboratory = laboratory;
        this.professor = professor;
        this.title = title;
        this.researchersRaw = researchersRaw;
        this.platform = platform;
        this.year = year;
        this.type = type;
        this.status = status;
        this.doi = doi;
        this.sourceURL = sourceURL;
    }

    public static Publication create(
            Laboratory laboratory,
            Professor professor,
            String title,
            String researchersRaw,
            String platform,
            String year,
            String type,
            String status,
            String doi,
            String sourceURL
    ) {
        return new Publication(laboratory, professor, title, researchersRaw, platform, year, type, status, doi, sourceURL);
    }

}
