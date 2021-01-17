package org.vijayian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.vijayian.entity.Book;

import java.util.Date;

/**
 *
 *
 * @author ViJay
 * @date 2021-01-16
 */
@RestController
public class JsonController {

    @GetMapping
    public Book book(){
        Book book = new Book();
        book.setAuthor("张麻子");
        book.setName("book01");
        book.setTime(new Date());
        book.setAge(18);
        //>> TODO jackson json {"name":"book01","author":"张麻子","time":"2021-01-17"}
        //>> TODO gson {"name":"book01","author":"张麻子","time":"2021-01-17"}
        //>> TODO fastjson { "age":18, "author":"张麻子", "name":"book01", "time":"2021-01-17" }.
        return book;
    }


}
