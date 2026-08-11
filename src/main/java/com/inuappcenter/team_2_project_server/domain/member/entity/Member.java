package com.inuappcenter.team_2_project_server.domain.member.entity;

import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "member",
        uniqueConstraints = {@UniqueConstraint(name = "uk_user_student_number", columnNames = "student_number")}
)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "student_number", nullable = false, unique = true)
    private String studentNumber;

    @Column(name = "nickname")
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String email;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    private String role;

    private Member(
            String studentNumber,
            String nickName,
            Department department,
            String email,
            LocalDateTime lastLoginAt,
            String role
    ) {
        this.studentNumber = studentNumber;
        this.nickName = nickName;
        this.department = department;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.role = role;
    }

    public static Member create(
            String studentNumber
    ) {
        return new Member(studentNumber, studentNumber, null, null, LocalDateTime.now(), "ROLE_USER");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
