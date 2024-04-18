package com.nhnacademy.shoppingmall.common.page;

import java.util.List;
import lombok.Getter;

@Getter
public class Page<T> {

    private final List<T> content;

    private final int pageSize;

    private final long totalCount;

    private final int totalIndex;

    public Page(List<T> content, int pageSize, long totalCount) {
        this.content = content;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalIndex = (int) Math.ceil((double) totalCount / pageSize);
    }
}
