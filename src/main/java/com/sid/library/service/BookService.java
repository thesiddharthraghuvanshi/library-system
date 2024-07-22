package com.sid.library.service;

import com.sid.library.model.dtos.BookDto;
import com.sid.library.model.etos.BookEto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookService {

    ResponseEntity<String> registerBook(BookDto bookDto);

    ResponseEntity<List<BookEto>> getAllBooks();
}
