package com.example.book.service.impl;

import com.example.book.domain.BookReview;
import com.example.book.domain.BookReviewReply;
import com.example.book.repository.BookReviewRepository;
import com.example.book.service.BookReviewService;
import com.example.book.service.UserService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    @Autowired
    private BookReviewRepository bookReviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    @Override
    public boolean createReview(BookReview bookReview) {
        bookReview.setUsername(userService.currentUser().getUsername());
        bookReview.setReplies(Collections.emptyList());
        try {
            bookReviewRepository.save(bookReview);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean createReviewReplyById(String id, BookReviewReply bookReviewReply) {
        bookReviewReply.setUsername(userService.currentUser().getUsername());
        UpdateResult updateResult = mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)),
                new Update().push("replies", bookReviewReply), BookReview.class);
        return updateResult.getMatchedCount() == 1L;
    }

    @Override
    public List<BookReview> readByBookId(Long bookId) {
        return bookReviewRepository.findByBookId(bookId);
    }
}
