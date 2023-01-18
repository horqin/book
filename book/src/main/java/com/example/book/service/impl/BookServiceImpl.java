package com.example.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.book.domain.Book;
import com.example.book.mapper.BookMapper;
import com.example.book.repository.BookRepository;
import com.example.book.service.BookService;
import com.example.book.utils.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Override
    public boolean create(Book book) {
        return this.save(book);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(Long id, Book book) {
        return this.update(book, new LambdaUpdateWrapper<Book>().eq(Book::getId, id));
    }

    @Override
    public Book search(Long id) {
        return this.getById(id);
    }

    @Override
    public IPage<Book> search(Integer current, Integer size) {
        return this.page(new Page<>(current, size));
    }

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void esImportAll() {
        List<Book> books = this.list();
        bookRepository.saveAll(books);
    }

    @Override
    public void esCreate(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void esDelete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public IPage<Book> esSearch(String keyword, Integer current, Integer size) {
        org.springframework.data.domain.Page<Book> page = bookRepository.findByTitle(keyword, PageRequest.of(current, size));
        return Pages.convert(page);
    }
}
