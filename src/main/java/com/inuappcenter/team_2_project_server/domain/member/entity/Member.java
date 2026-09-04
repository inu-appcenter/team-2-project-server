package com.inuappcenter.team_2_project_server.domain.member.entity;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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
    Long id;

    @Column(name = "student_number", nullable = false, unique = true)
    String studentNumber;

    @Column(name = "nickname")
    String nickName;

    @Enumerated(EnumType.STRING)
    College college;

    @Enumerated(EnumType.STRING)
    Department department;

    String email;

    @Column(name = "last_login_at")
    LocalDateTime lastLoginAt;

    @Column(name = "is_new", nullable = false)
    @ColumnDefault("false")
    boolean isNew = true;

    String role;

    private Member(
            String studentNumber,
            String nickName,
            College college,
            Department department,
            String email,
            LocalDateTime lastLoginAt,
            String role
    ) {
        this.studentNumber = studentNumber;
        this.nickName = nickName;
        this.college = college;
        this.department = department;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.role = role;
    }

    public static Member create(
            String studentNumber,
            String nickName,
            College college,
            Department department,
            String email
    ) {
        return new Member(studentNumber, nickName, college, department, email, LocalDateTime.now(), "ROLE_USER");
    }

    // 외부에서 role을 받아서 member를 만드는 정적 팩토리 메서드
    public static Member createWithRole(
            String studentNumber,
            String nickName,
            College college,
            Department department,
            String email,
            String role
    ) {
        return new Member(studentNumber, nickName, college, department, email, LocalDateTime.now(), role);
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

    public void updateMemberProfile(
            String nickName,
            Department department,
            String email
    ) {
        if (nickName != null) {
            this.nickName = nickName;
        }
        if (department != null) {
            this.department = department;
        }
        if (email != null) {
            this.email = email;
        }
    }

    public void updateIsNew() {
        this.isNew = false;
    }

    public void recordLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
