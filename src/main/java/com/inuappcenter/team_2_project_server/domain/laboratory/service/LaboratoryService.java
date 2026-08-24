package com.inuappcenter.team_2_project_server.domain.laboratory.service;

import com.inuappcenter.team_2_project_server.domain.laboratory.dto.request.LaboratoryCreateRequestDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.request.LaboratoryUpdateRequestDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;
    private final ProfessorRepository professorRepository;

    /**
     * 연구실 수동 생성 메서드
     */
    @Transactional
    public LaboratoryResponseDto createLab(
            LaboratoryCreateRequestDto request
    ) {
        Professor professor = professorRepository.findById(request.professorId())
                .orElseThrow(() -> new MyException(ErrorCode.PROFESSOR_NOT_FOUND));

        if (laboratoryRepository.existsByLabNameAndProfessorId(request.labName(), request.professorId())) {
            throw new MyException(ErrorCode.DUPLICATED_LABORATORY);
        }

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

        Laboratory savedLaboratory = laboratoryRepository.save(laboratory);

        return LaboratoryResponseDto.from(savedLaboratory);
    }

    /**
     * 연구실 단건 조회
     */
    @Transactional(readOnly = true)
    public LaboratoryResponseDto getLab(
            Long laboratoryId
    ) {
        Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new MyException(ErrorCode.LABORATORY_NOT_FOUND));

        return LaboratoryResponseDto.from(laboratory);
    }

    /**
     * 연구실 전제 조회
     */
    @Transactional(readOnly = true)
    public List<LaboratoryResponseDto> getAllLab() {
        return laboratoryRepository.findAll().stream()
                .map(LaboratoryResponseDto::from)
                .toList();
    }

    @Transactional
    public LaboratoryResponseDto updateLab(
            Long laboratoryId,
            LaboratoryUpdateRequestDto request
    ) {
        Laboratory laboratory = laboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new MyException(ErrorCode.LABORATORY_NOT_FOUND));

        laboratory.updateLab(
                request.labName(),
                request.location(),
                request.capacity(),
                request.introduction(),
                request.labUrl(),
                request.researchFieldRaw()
        );

        return LaboratoryResponseDto.from(laboratory);
    }
}
