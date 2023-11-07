package com.study.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @Description:  课程查询条件模型类
 * @Author: kiligsmile
 * @Date: 2023/10/28 10:24 PM
 * @Param:
 * @Return:
*/


@Data
@ToString
public class QueryCourseParamsDto {
    private String auditStatus;
    private String courseName;
    private String publishStatus;
}
