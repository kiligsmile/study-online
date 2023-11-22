package com.study.content.service;

import com.study.base.model.PageParams;
import com.study.base.model.PageResult;
import com.study.content.model.dto.AddCourseDto;
import com.study.content.model.dto.CourseBaseInfoDto;
import com.study.content.model.dto.QueryCourseParamsDto;
import com.study.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.RequestBody;

//课程信息管理接口
public interface CourseBaseInfoService {
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * @Description: 新增课程 
     * @Author: kiligsmile
     * @Date: 2023/11/19 8:11 PM
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @Return: 课程详细信息
    */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
}
