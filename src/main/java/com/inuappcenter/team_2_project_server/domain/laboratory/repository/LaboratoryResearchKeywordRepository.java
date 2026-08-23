package com.inuappcenter.team_2_project_server.domain.laboratory.repository;

import com.inuappcenter.team_2_project_server.domain.laboratory.entity.Laboratory;
import com.inuappcenter.team_2_project_server.domain.laboratory.entity.LaboratoryResearchArea;
import com.inuappcenter.team_2_project_server.domain.laboratory.entity.ResearchArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoryResearchKeywordRepository extends JpaRepository<LaboratoryResearchArea, Long> {
    boolean existsByLaboratoryAndResearchKeyword(Laboratory laboratory, ResearchArea researchKeyword);
}
