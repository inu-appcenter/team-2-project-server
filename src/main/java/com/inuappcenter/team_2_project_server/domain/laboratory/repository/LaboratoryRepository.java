package com.inuappcenter.team_2_project_server.domain.laboratory.repository;

import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.domain.laboratory.entity.Laboratory;
import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    // 단건(Optional)이 아닌 List로 받아 서비스 단에서 첫 번째 결과만 사용한다.
    List<Laboratory> findByLabNameAndDepartmentAndProfessor_Name(String labName, Department department, String professorName);

    boolean existsByLabNameAndProfessorAndDepartment(String labName, Professor professor, Department department);

    boolean existsByLabNameAndProfessorIdAndDepartment(String labName, Long professorId, Department department);

    List<Laboratory> findByLabNameContainingIgnoreCaseOrProfessor_NameContainingIgnoreCase(
            String labNameKeyword,
            String professorNameKeyword
    );
}
