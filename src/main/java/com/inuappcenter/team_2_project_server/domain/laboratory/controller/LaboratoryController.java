package com.inuappcenter.team_2_project_server.domain.laboratory.controller;

import com.inuappcenter.team_2_project_server.domain.laboratory.dto.request.LaboratoryCreateRequestDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.request.LaboratoryUpdateRequestDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.dto.response.LaboratoryResponseDto;
import com.inuappcenter.team_2_project_server.domain.laboratory.service.LaboratoryExcelImportService;
import com.inuappcenter.team_2_project_server.domain.laboratory.service.LaboratoryService;
import com.inuappcenter.team_2_project_server.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/laboratory")
public class LaboratoryController implements LaboratoryApiSpecification {
    private final LaboratoryExcelImportService laboratoryExcelImportService;
    private final LaboratoryService laboratoryService;

    @Override
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<Void>> importLaboratory(@RequestPart MultipartFile file) {
        laboratoryExcelImportService.importExcel(file);
        return ResponseEntity.ok(
                ResponseDto.of(null, "연구실 편람 동기화 완료")
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDto<LaboratoryResponseDto>> createLaboratory(
            @RequestBody LaboratoryCreateRequestDto request
    ) {
        LaboratoryResponseDto response = laboratoryService.createLab(request);

        return ResponseEntity.ok(
                ResponseDto.of(response, "연구실 생성 성공")
        );
    }


    @GetMapping("/{laboratoryId}")
    public ResponseEntity<ResponseDto<LaboratoryResponseDto>> getLaboratory(
            @PathVariable Long laboratoryId
    ) {
        LaboratoryResponseDto response = laboratoryService.getLab(laboratoryId);

        return ResponseEntity.ok(
                ResponseDto.of(response, "연구실 조회 성공")
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<LaboratoryResponseDto>>> getAllLaboratory() {
        List<LaboratoryResponseDto> responses = laboratoryService.getAllLab();

        return ResponseEntity.ok(
                ResponseDto.of(responses, "전체 연구실 조회 성공")
        );
    }

    @PatchMapping("/{laboratoryId}")
    public ResponseEntity<ResponseDto<LaboratoryResponseDto>> updateLaboratory(
            @PathVariable Long laboratoryId,
            @RequestBody LaboratoryUpdateRequestDto request
    ) {
        LaboratoryResponseDto response = laboratoryService.updateLab(laboratoryId, request);

        return ResponseEntity.ok(
                ResponseDto.of(response, "연구실 수정 성공")
        );
    }

    @DeleteMapping("/{laboratoryId}")
    public ResponseEntity<ResponseDto<Long>> deleteLaboratory(
            @PathVariable Long laboratoryId
    ) {
        laboratoryService.deleteLab(laboratoryId);
        return ResponseEntity.ok(
                ResponseDto.of(laboratoryId, "연구실 삭제 완료")
        );
    }
}
