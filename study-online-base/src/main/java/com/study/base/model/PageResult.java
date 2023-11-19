package com.study.base.model;

import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 分页查询结果模型
 * @Author: kiligsmile
 * @Date: 2023/10/28 10:29 PM
 * @Param:
 * @Return:
*/

@Data
@ToString
public class PageResult<T> implements Serializable {
    private List<T> items;
    private long counts;
    private long page;
    private long pageSize;

    public PageResult(List<T> items, long counts, long page, long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }
}
