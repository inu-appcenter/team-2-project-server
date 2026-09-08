package com.inuappcenter.team_2_project_server.domain.bugReport.entity;

import com.inuappcenter.team_2_project_server.domain.bugReport.enums.Category;
import com.inuappcenter.team_2_project_server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table
public class BugReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 1000)
    private String content;

    private String email;

    private BugReport(
            Category category,
            String content,
            String email
    ) {
        this.category = category;
        this.content = content;
        this.email = email;
    }

    public static BugReport create(
            Category category,
            String content,
            String email
    ) {
        return new BugReport(category, content, email);
    }
}
