package com.inuappcenter.team_2_project_server.laboratory;

import com.inuappcenter.team_2_project_server.domain.department.College;
import com.inuappcenter.team_2_project_server.domain.department.Department;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.LaboratoryExcelRow;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.ProfessorExcelRow;
import com.inuappcenter.team_2_project_server.domain.laboratory.entity.Laboratory;
import com.inuappcenter.team_2_project_server.domain.laboratory.entity.LaboratoryResearchArea;
import com.inuappcenter.team_2_project_server.domain.laboratory.entity.ResearchArea;
import com.inuappcenter.team_2_project_server.domain.laboratory.repository.LaboratoryRepository;
import com.inuappcenter.team_2_project_server.domain.laboratory.repository.LaboratoryResearchKeywordRepository;
import com.inuappcenter.team_2_project_server.domain.laboratory.repository.ResearchKeywordRepository;
import com.inuappcenter.team_2_project_server.domain.laboratory.service.ExcelLabParser;
import com.inuappcenter.team_2_project_server.domain.laboratory.service.LaboratoryExcelImportService;
import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;
import com.inuappcenter.team_2_project_server.domain.member.repository.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class LaboratoryExcelImportServiceTest {

    private ExcelLabParser excelLabParser;
    private ProfessorRepository professorRepository;
    private LaboratoryRepository laboratoryRepository;
    private ResearchKeywordRepository researchKeywordRepository;
    private LaboratoryResearchKeywordRepository laboratoryResearchKeywordRepository;
    private LaboratoryExcelImportService laboratoryExcelImportService;

    @BeforeEach
    void setUp() {
        excelLabParser = mock(ExcelLabParser.class);
        professorRepository = mock(ProfessorRepository.class);
        laboratoryRepository = mock(LaboratoryRepository.class);
        researchKeywordRepository = mock(ResearchKeywordRepository.class);
        laboratoryResearchKeywordRepository = mock(LaboratoryResearchKeywordRepository.class);
        laboratoryExcelImportService = new LaboratoryExcelImportService(
                excelLabParser,
                professorRepository,
                laboratoryRepository,
                researchKeywordRepository,
                laboratoryResearchKeywordRepository
        );
    }

    @Test
    void importExcel_saves_professor_laboratory_and_research_keyword() {
        MockMultipartFile file = excelFile();
        ProfessorExcelRow professorRow = professorRow("인공지능");
        LaboratoryExcelRow laboratoryRow = laboratoryRow();
        Professor professor = professor();
        ResearchArea researchArea = ResearchArea.create("인공지능");

        given(excelLabParser.parseProfessors(file)).willReturn(List.of(professorRow));
        given(excelLabParser.parseLaboratories(file)).willReturn(List.of(laboratoryRow));
        given(professorRepository.findByDepartmentAndNameAndEmail(
                Department.COMPUTER_ENGINEERING,
                "홍길동",
                "hong@inu.ac.kr"
        )).willReturn(Optional.empty())
                .willReturn(Optional.of(professor));
        given(professorRepository.save(any(Professor.class))).willReturn(professor);
        given(laboratoryRepository.existsByLabNameAndProfessor("AI연구실", professor)).willReturn(false);
        given(researchKeywordRepository.findByArea("인공지능")).willReturn(Optional.empty());
        given(researchKeywordRepository.save(any(ResearchArea.class))).willReturn(researchArea);
        given(laboratoryResearchKeywordRepository.existsByLaboratoryAndResearchKeyword(
                any(Laboratory.class),
                any(ResearchArea.class)
        )).willReturn(false);

        laboratoryExcelImportService.importExcel(file);

        verify(professorRepository).save(any(Professor.class));
        verify(laboratoryRepository).save(any(Laboratory.class));
        verify(researchKeywordRepository).save(any(ResearchArea.class));
        verify(laboratoryResearchKeywordRepository).save(any(LaboratoryResearchArea.class));
    }

    @Test
    void importExcel_skips_laboratory_when_laboratory_already_exists() {
        MockMultipartFile file = excelFile();
        ProfessorExcelRow professorRow = professorRow("인공지능");
        LaboratoryExcelRow laboratoryRow = laboratoryRow();
        Professor professor = professor();

        given(excelLabParser.parseProfessors(file)).willReturn(List.of(professorRow));
        given(excelLabParser.parseLaboratories(file)).willReturn(List.of(laboratoryRow));
        given(professorRepository.findByDepartmentAndNameAndEmail(
                Department.COMPUTER_ENGINEERING,
                "홍길동",
                "hong@inu.ac.kr"
        )).willReturn(Optional.of(professor));
        given(laboratoryRepository.existsByLabNameAndProfessor("AI연구실", professor)).willReturn(true);

        laboratoryExcelImportService.importExcel(file);

        verify(professorRepository, never()).save(any(Professor.class));
        verify(laboratoryRepository, never()).save(any(Laboratory.class));
        verify(researchKeywordRepository, never()).findByArea(anyString());
        verify(laboratoryResearchKeywordRepository, never()).save(any(LaboratoryResearchArea.class));
    }

    private MockMultipartFile excelFile() {
        return new MockMultipartFile(
                "file",
                "laboratories.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                new byte[]{1}
        );
    }

    private ProfessorExcelRow professorRow(String researchAreaRaw) {
        return new ProfessorExcelRow(
                College.COLLEGE_OF_INFORMATION_TECHNOLOGY,
                Department.COMPUTER_ENGINEERING,
                "홍길동",
                "교수",
                researchAreaRaw,
                "032-000-0000",
                "hong@inu.ac.kr"
        );
    }

    private LaboratoryExcelRow laboratoryRow() {
        return new LaboratoryExcelRow(
                College.COLLEGE_OF_INFORMATION_TECHNOLOGY,
                Department.COMPUTER_ENGINEERING,
                "AI연구실",
                "홍길동",
                "hong@inu.ac.kr",
                "https://lab.example.com"
        );
    }

    private Professor professor() {
        return Professor.create(
                "홍길동",
                "교수",
                College.COLLEGE_OF_INFORMATION_TECHNOLOGY,
                Department.COMPUTER_ENGINEERING,
                "032-000-0000",
                "hong@inu.ac.kr"
        );
    }
}
