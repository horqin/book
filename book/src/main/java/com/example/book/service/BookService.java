package com.example.book.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.book.domain.Book;

public interface BookService extends IService<Book> {

    /**
     * 增加
     * @param book 书籍
     * @return 成功或失败
     */
    boolean create(Book book);

    /**
     * 删除
     * @param id 主键
     */
    boolean deleteById(Long id);

    /**
     * 改正
     * @param id 主键
     * @param book 书籍
     * @return 成功或失败
     */
    boolean updateById(Long id, Book book);

    /**
     * 查询
     * @param id 主键
     * @return 书籍
     */
    Book readById(Long id);

    /**
     * 分页查询
     * @param current 当前页码
     * @param size 每页数量
     * @return 分页的书籍
     */
    IPage<Book> readBatchByPage(Integer current, Integer size);

    /**
     * ES 导入
     */
    void esImportAll();

    /**
     * ES 增加
     * @param book 书籍
     */
    void esCreate(Book book);

    /**
     * ES 删除
     * @param id 主键
     */
    void esDeleteById(Long id);

    /**
     * ES 带关键词的分页查询
     * @param keyword 关键字
     * @param current 当前页码
     * @param size 每页数量
     * @return 分页的书籍
     */
    IPage<Book> esReadBatchByPageWithKeyword(String keyword, Integer current, Integer size);
}
