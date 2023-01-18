package com.example.book.repository;

import com.example.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book, Long> {
    Page<Book> findByTitle(String title, Pageable page);
}
