package com.example.book.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class Pages {
    public static <T> Page<T> convert(org.springframework.data.domain.Page<T> from) {
        Page<T> to = new Page<>();
        to.setCurrent(from.getNumber() + 1); // 当前页
        to.setSize(from.getSize()); // 每页条数
        to.setPages(from.getTotalPages()); // 总页数
        to.setTotal(from.getTotalElements()); // 总条数
        to.setRecords(from.getContent()); // 当前页记录
        return to;
    }
}
