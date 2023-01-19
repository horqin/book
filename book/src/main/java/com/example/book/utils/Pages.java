package com.example.book.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class Pages {
    public static <T> Page<T> convert(org.springframework.data.domain.Page<T> from) {
        Page<T> to = new Page<>();
        to.setCurrent(from.getNumber()); // 当前页码
        to.setSize(from.getSize()); // 每页数量
        to.setPages(from.getTotalPages()); // 总页数
        to.setTotal(from.getTotalElements()); // 总条数
        to.setRecords(from.getContent()); // 分页数据
        return to;
    }
}
