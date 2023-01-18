package com.example.book.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.book.domain.Book;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    @ResponseBody
    public SaResult create(@RequestBody Book book) {
        if (bookService.create(book)) {
            return SaResult.ok();
        }
        return SaResult.error("创建失败");
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public SaResult delete(@PathVariable Long id) {
        if (bookService.delete(id)) {
            return SaResult.ok();
        }
        return SaResult.error("删除失败");
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public SaResult update(@PathVariable Long id, @RequestBody Book book) {
        if (bookService.update(id, book)) {
            return SaResult.ok();
        }
        return SaResult.error("更新失败");
    }

    @GetMapping("/search/{id}")
    @ResponseBody
    public SaResult search(@PathVariable Long id) {
        Book book;
        if ((book = bookService.search(id)) != null) {
            return SaResult.ok().setData(book);
        }
        return SaResult.error("搜索失败");
    }

    @GetMapping("/search")
    @ResponseBody
    public SaResult search(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "5") Integer size) {
        IPage<Book> books = bookService.search(current, size);
        return SaResult.ok().setData(books);
    }

    @GetMapping("/importAll")
    @ResponseBody
    public SaResult esImportAll() {
        bookService.esImportAll();
        return SaResult.ok();
    }

    @GetMapping
    @ResponseBody
    public SaResult search(@RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "5") Integer size) {
        IPage<Book> books = bookService.esSearch(keyword, current - 1, size);
        return SaResult.ok().setData(books);
    }
}
