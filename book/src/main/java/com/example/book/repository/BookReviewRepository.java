package com.example.book.repository;

import com.example.book.domain.BookReview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookReviewRepository extends MongoRepository<BookReview, String> {
    List<BookReview> findByBookId(Long bookId);
}
