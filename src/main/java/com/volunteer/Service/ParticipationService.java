package com.volunteer.Service;

import com.volunteer.DTO.ParticipationDto;
import com.volunteer.Entity.ParticipationEntity;
import com.volunteer.Repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    // 참여 글 저장
    public void writeSave(String title, String item, String category, LocalDateTime startDate, LocalDateTime endDate) {
        ParticipationEntity participationEntity = new ParticipationEntity();
        participationEntity.setParticipationTitle(title);
        participationEntity.setParticipationItem(item);
        participationEntity.setCategory(category);
        participationEntity.setParticipationStart(startDate);
        participationEntity.setParticipationEnd(endDate);

        participationEntity.setAdminId(1L);  // 하드코딩으로 adminId 설정

        participationRepository.save(participationEntity); // DB에 참여 저장
    }

    // 참여 글 수정
    public void updateParticipation(Long id, String title, String item, String category,
                                    LocalDateTime startDate, LocalDateTime endDate) {
        ParticipationEntity participationEntity = participationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("참여를 찾을 수 없습니다."));

        participationEntity.setParticipationTitle(title);
        participationEntity.setParticipationItem(item);
        participationEntity.setCategory(category);
        participationEntity.setParticipationStart(startDate);
        participationEntity.setParticipationEnd(endDate);

        participationRepository.save(participationEntity);  // DB에 수정된 참여 저장
    }

    // 참여 ID로 찾기
    public ParticipationDto getParticipationById(Long id) {
        return participationRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    // 전체 참여 가져오기
    public List<ParticipationDto> getAllParticipations() {
        return participationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // DTO로 변환
    private ParticipationDto convertToDto(ParticipationEntity entity) {
        return new ParticipationDto(entity.getId(), entity.getParticipationTitle(), entity.getParticipationItem(),
                entity.getCategory(), entity.getParticipationStart(), entity.getParticipationEnd());
    }
}
