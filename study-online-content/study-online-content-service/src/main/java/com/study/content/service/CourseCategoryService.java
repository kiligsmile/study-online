package com.study.content.service;

import com.study.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

public interface CourseCategoryService {
    public List<CourseCategoryTreeDto> queryTreeNodes (String id);
}
