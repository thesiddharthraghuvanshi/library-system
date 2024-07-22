package com.sid.library.controller;

import com.sid.library.model.dtos.BookDto;
import com.sid.library.model.etos.BookEto;
import com.sid.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("register")
    public ResponseEntity<String> registerBook(@RequestBody BookDto bookDto) {
        log.info("Inside registerBook | BookController");
        return bookService.registerBook(bookDto);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<BookEto>> getAllBooks() {
        log.info("Inside getAllBooks | BookController");
        return bookService.getAllBooks();
    }
}
