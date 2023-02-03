package com.example.book.service;

import com.example.book.domain.BookReview;
import com.example.book.domain.BookReviewReply;

import java.util.List;

public interface BookReviewService {

    /**
     * 增加评论
     * @param bookReview 评论
     * @return 成功或失败
     */
    boolean createReview(BookReview bookReview);

    /**
     * 增加回复
     * @param id 主键
     * @param bookReviewReply 回复
     * @return 成功或失败
     */
    boolean createReviewReplyById(String id, BookReviewReply bookReviewReply);

    /**
     * 查询
     * @param bookId 书籍主键
     * @return 评论列表
     */
    List<BookReview> readByBookId(Long bookId);
}
