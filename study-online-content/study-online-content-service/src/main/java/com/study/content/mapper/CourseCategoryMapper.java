package com.study.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.content.model.dto.CourseCategoryTreeDto;
import com.study.content.model.po.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author kiligsmile
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    public List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
