package com.inuappcenter.team_2_project_server.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "spring.app.local-auth")
public class LocalAuthConfig {

    // 시드 유저를 담을 리스트
    private List<SeedUser> seedUsers = new ArrayList<>();

    public void setSeedUsers(List<SeedUser> seedUsers) {
        this.seedUsers = seedUsers;
    }

    public Optional<SeedUser> findByStudentId(String studentId) {
        return seedUsers.stream()
                .filter(user -> user.getStudentId().equals(studentId))
                .findFirst();
    }

    public static class SeedUser {
        private String studentId;
        private String password;
        private List<String> roles = new ArrayList<>();

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        public String primaryRole() {
            if (roles == null || roles.isEmpty()) {
                return "ROLE_USER";
            }
            return roles.get(0);
        }
    }

}
