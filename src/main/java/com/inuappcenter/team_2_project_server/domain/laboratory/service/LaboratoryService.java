package com.inuappcenter.team_2_project_server.domain.laboratory.service;

import com.inuappcenter.team_2_project_server.domain.laboratory.dto.request.LaboratoryCreateRequestDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.response.LaboratoryResponseDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.entity.Laboratory;
import com.inuappcenter.team_2_project_server.domain.laboratory.repository.LaboratoryRepository;
import com.inuappcenter.team_2_project_server.domain.member.entity.Professor;
import com.inuappcenter.team_2_project_server.domain.member.repository.ProfessorRepository;
import com.inuappcenter.team_2_project_server.global.error.ex.ErrorCode;
import com.inuappcenter.team_2_project_server.global.error.ex.MyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LaboratoryService {
    private LaboratoryRepository laboratoryRepository;
    private ProfessorRepository professorRepository;

    /**
     * 연구실 수동 생성 메서드
     */
    @Transactional
    public LaboratoryResponseDto createLab(
            LaboratoryCreateRequestDto request
    ) {
        if (laboratoryRepository.existsByLabNameAndProfessorId(request.labName(), request.professorId())) {
            throw new MyException(ErrorCode.DUPLICATED_LABORATORY);
        }

        Professor professor = professorRepository.findById(request.professorId())
                .orElseThrow(() -> new MyException(ErrorCode.PROFESSOR_NOT_FOUND));


        Laboratory laboratory = Laboratory.create(
                request.college(),
                request.department(),
                request.labName(),
                request.location(),
                request.capacity(),
                request.introduction(),
                professor,
                request.labUrl(),
                request.researchFieldRaw()
        );

        return LaboratoryResponseDto.from(laboratory);
    }
}
