package com.example.book.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.book.domain.Book;
import com.example.book.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @ResponseBody
    public SaResult create(@RequestBody Book book) {
        if (bookService.create(book)) {
            return SaResult.ok();
        }
        return SaResult.error("增加失败");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public SaResult deleteById(@PathVariable Long id) {
        if (bookService.deleteById(id)) {
            return SaResult.ok();
        }
        return SaResult.error("删除失败");
    }

    @PutMapping("/{id}")
    @ResponseBody
    public SaResult updateById(@PathVariable Long id, @RequestBody Book book) {
        if (bookService.updateById(id, book)) {
            return SaResult.ok();
        }
        return SaResult.error("改正失败");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SaResult readById(@PathVariable Long id) {
        Book book;
        if ((book = bookService.readById(id)) != null) {
            return SaResult.ok().setData(book);
        }
        return SaResult.error("读取失败");
    }

    @GetMapping
    @ResponseBody
    public SaResult readBatchByPage(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "5") Integer size) {
        IPage<Book> books;
        if (StringUtils.isEmpty(keyword)) {
            books = bookService.readBatchByPage(current, size);
        } else {
            books = bookService.esReadBatchByPageWithKeyword(keyword, current - 1, size);
        }
        return SaResult.ok().setData(books);
    }

    @GetMapping("/importAll")
    @ResponseBody
    public SaResult importAll() {
        bookService.esImportAll();
        return SaResult.ok();
    }
}
