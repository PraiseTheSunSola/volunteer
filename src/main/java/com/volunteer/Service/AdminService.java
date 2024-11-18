package com.volunteer.Service;

import com.volunteer.DTO.ContentDto;
import com.volunteer.Entity.ContentEntity;
import com.volunteer.Repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ContentRepository contentRepository;

    public void contentSave(ContentDto contentDto){
        ContentEntity contentEntity = contentDto.createEntity();
        System.out.println(contentEntity.getContent());
        System.out.println(contentEntity.getTitle());
        System.out.println(contentEntity.getAge());
        System.out.println(contentEntity.getId());
        System.out.println(contentEntity.getCategory());
        System.out.println(contentEntity.getAgencyName());
        System.out.println(contentEntity.getAgencyTel());
        System.out.println(contentEntity.getAgencyEmail());
        System.out.println(contentEntity.getGroup());
        System.out.println(contentEntity.getRecruitCount());
        System.out.println(contentEntity.getGoalCost());
        System.out.println(contentEntity.getRecruitStart().toString());
        System.out.println(contentEntity.getRecruitEnd().toString());
        System.out.println(contentEntity.getActivityStart().toString());
        System.out.println(contentEntity.getActivityEnd().toString());

        contentRepository.save(contentEntity); //테이블에 저장
    }

    public List<ContentDto> all() {
        List<ContentEntity> contentList = contentRepository.findAll();
        List<ContentDto> contentDtoList = new ArrayList<>();
        for (ContentEntity contentEntity : contentList) {
            contentDtoList.add(ContentDto.of(contentEntity));
        }

        return contentDtoList;
    }

    public Page<ContentDto> getContentList(Pageable pageable) {
        List<ContentEntity> contentList = contentRepository.findAll(pageable).getContent();
        Long count = contentRepository.count();

        List<ContentDto> contentDtoList = new ArrayList<>();
        for (ContentEntity contentEntity : contentList) {
            contentDtoList.add(ContentDto.of(contentEntity));
        }

        return new PageImpl<>(contentDtoList, pageable, count);
    }
}
