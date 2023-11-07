package com.study.content.service;

import com.study.base.model.PageParams;
import com.study.base.model.PageResult;
import com.study.content.model.dto.QueryCourseParamsDto;
import com.study.content.model.po.CourseBase;

//课程信息管理接口
public interface CourseBaseInfoService {
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);
}
