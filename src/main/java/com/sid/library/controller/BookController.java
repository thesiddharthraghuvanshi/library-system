package com.sid.library.controller;

import com.sid.library.model.dtos.BookDto;
import com.sid.library.model.etos.BookEto;
import com.sid.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("book")
@Tag(name = "Book Controller", description = "Endpoints for managing books")
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("register")
    @Operation(summary = "Register a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered the book"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<String> registerBook(@RequestBody BookDto bookDto) {
        log.info("Inside registerBook | BookController");
        return bookService.registerBook(bookDto);
    }

    @GetMapping("getAll")
    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the books"),
            @ApiResponse(responseCode = "404", description = "No books found")
    })
    public ResponseEntity<List<BookEto>> getAllBooks() {
        log.info("Inside getAllBooks | BookController");
        return bookService.getAllBooks();
    }
}
