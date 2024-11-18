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
