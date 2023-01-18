package com.example.book.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.book.domain.Book;

public interface BookService extends IService<Book> {

    /**
     * 创建
     * @param book 书籍
     * @return 成功或失败
     */
    boolean create(Book book);

    /**
     * 删除
     * @param id 主键
     */
    boolean delete(Long id);

    /**
     * 更新
     * @param id 主键
     * @param book 书籍
     * @return 成功或失败
     */
    boolean update(Long id, Book book);

    /**
     * 搜索
     * @param id 主键
     * @return 书籍
     */
    Book search(Long id);

    /**
     * 分页搜索
     * @param current 当前的页码
     * @param size 总数
     * @return 分页的书籍
     */
    IPage<Book> search(Integer current, Integer size);

    /**
     * 简单导入全部
     */
    void esImportAll();

    /**
     * 简单创建
     * @param book 书籍
     */
    void esCreate(Book book);

    /**
     * 简单删除
     * @param id 主键
     */
    void esDelete(Long id);

    /**
     * 分页简单搜索
     * @param keyword 关键字
     * @param current 当前的页码
     * @param size 总数
     * @return 分页的书籍
     */
    IPage<Book> esSearch(String keyword, Integer current, Integer size);
}
