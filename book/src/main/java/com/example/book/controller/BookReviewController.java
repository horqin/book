package com.example.book.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.book.domain.BookReview;
import com.example.book.domain.BookReviewReply;
import com.example.book.service.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-review")
public class BookReviewController {

    @Autowired
    private BookReviewService bookReviewService;

    @PostMapping
    @ResponseBody
    public SaResult createReview(@RequestBody BookReview bookReview) {
        if (bookReviewService.createReview(bookReview)) {
            return SaResult.ok();
        }
        return SaResult.error("增加评论失败");
    }

    @PutMapping("/{id}")
    @ResponseBody
    public SaResult createReviewReplyById(@PathVariable String id, @RequestBody BookReviewReply bookReviewReply) {
        if (bookReviewService.createReviewReplyById(id, bookReviewReply)) {
            return SaResult.ok();
        }
        return SaResult.error("增加回复失败");
    }

    @GetMapping("/{bookId}")
    @ResponseBody
    public SaResult readByBookId(@PathVariable Long bookId) {
        List<BookReview> bookReview = bookReviewService.readByBookId(bookId);
        return SaResult.ok().setData(bookReview);
    }
}
