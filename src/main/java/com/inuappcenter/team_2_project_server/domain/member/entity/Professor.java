package com.inuappcenter.team_2_project_server.domain.member.entity;

import com.inuappcenter.team_2_project_server.domain.member.ProfessorPosition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "professor_position")
    ProfessorPosition professorPosition;

    @Column(nullable = false)
    String name;

    private Professor(String name, ProfessorPosition professorPosition) {
        this.name = name;
        this.professorPosition = professorPosition;
    }

    public static Professor create(String name, ProfessorPosition professorPosition) {
        return new Professor(name, professorPosition);
    }
}
