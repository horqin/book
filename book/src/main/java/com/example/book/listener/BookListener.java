package com.example.book.listener;

import com.example.book.domain.Book;
import com.example.book.service.BookService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import java.util.List;

@Data
class Message<T> {
    private String       database;
    private String       table;
    private List<String> pkNames;
    private Boolean      isDdl;
    private String       type;
    private Long         es;
    private Long         ts;
    private String       sql;
    private List<T>      data;
    private List<T>      old;
}

@Controller
public class BookListener {

    @Autowired
    private BookService bookService;

    @KafkaListener(topics = "book-topic", groupId = "book-group")
    public void consume(String message) {
        Message<Book> msg = new Gson().fromJson(message, new TypeToken<Message<Book>>(){}.getType());
        if (StringUtils.equalsAny(msg.getType(), "INSERT", "UPDATE")) {
            for (Book book: msg.getData()) {
                if (book.getDeleted() == 0) {
                    bookService.esCreate(book); // 增加、改正
                } else {
                    bookService.esDeleteById(book.getId()); // 逻辑删除
                }
            }
        } else if (StringUtils.equals(msg.getType(), "DELETE")) {
            for (Book book: msg.getOld()) {
                bookService.esDeleteById(book.getId()); // 物理删除
            }
        }
    }
}
