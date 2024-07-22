package com.sid.library.controller;

import com.sid.library.model.dtos.BookDto;
import com.sid.library.model.etos.BookEto;
import com.sid.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testRegisterBook() throws Exception {
        BookDto bookDto = new BookDto("1234567890", "Test Book", "John Doe");

        when(bookService.registerBook(any(BookDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Book registered successfully"));

        ResponseEntity<String> responseEntity = bookController.registerBook(bookDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Book registered successfully", responseEntity.getBody());
    }

    @Test
    public void testGetAllBooks() {
        List<BookEto> books = Arrays.asList(
                new BookEto(1, "1234567890", "Test Book 1", "John Doe", false),
                new BookEto(2, "0987654321", "Test Book 2", "Jane Smith", false)
        );

        when(bookService.getAllBooks()).thenReturn(ResponseEntity.ok(books));

        ResponseEntity<List<BookEto>> responseEntity = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }
}