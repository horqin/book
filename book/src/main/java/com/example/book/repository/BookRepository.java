package com.example.book.repository;

import com.example.book.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, Long> {
    org.springframework.data.domain.Page<Book> findByTitle(String keyword, Pageable page);
}
