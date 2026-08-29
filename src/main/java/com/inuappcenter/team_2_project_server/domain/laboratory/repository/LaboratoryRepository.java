package com.inuappcenter.team_2_project_server.domain.laboratory.repository;

import com.inuappcenter.team_2_project_server.domain.laboratory.entity.Laboratory;
import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    Optional<Laboratory> findByLabNameAndProfessor_Name(String labName, String professorName);

    boolean existsByLabNameAndProfessor(String labName, Professor professor);

    boolean existsByLabNameAndProfessorId(String labName, Long professorId);

    List<Laboratory> findByLabNameContainingIgnoreCaseOrProfessor_NameContainingIgnoreCase(
            String labNameKeyword,
            String professorNameKeyword
    );
}
