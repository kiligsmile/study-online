package com.study.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 分页查询 分页参数
 * @Author: kiligsmile
 * @Date: 2023/10/28 10:04 PM
 * @Param:
 * @Return:
*/

@Data
@ToString
public class PageParams {

//    当前页数
    @ApiModelProperty("页码")
    private Long pageNo=1L;
//    每页显示参数
    @ApiModelProperty("每页记录数")
    private Long pageSize=30L;

    public PageParams() {
    }

    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
