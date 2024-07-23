package com.sid.library.ServiceImpl;

import com.sid.library.model.Book;
import com.sid.library.model.dtos.BookDto;
import com.sid.library.model.etos.BookEto;
import com.sid.library.repository.BookRepository;
import com.sid.library.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterBook_Success() {
        BookDto bookDto = new BookDto("978-3-16-148410-0", "Book Title", "Author");

        when(bookRepository.findByIsbnNumber("978-3-16-148410-0")).thenReturn(Optional.empty());

        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        ResponseEntity<String> responseEntity = bookService.registerBook(bookDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Book registered successfully", responseEntity.getBody());
    }

    @Test
    void testRegisterBook_BookExistsWithDifferentDetails() {
        BookDto bookDto = new BookDto("978-3-16-148410-0", "Book Title", "Author");

        Book existingBook = new Book(1, "978-3-16-148410-0", "Book Title", "Different Author", false);
        when(bookRepository.findByIsbnNumber("978-3-16-148410-0")).thenReturn(Optional.of(Collections.singletonList(existingBook)));

        ResponseEntity<String> responseEntity = bookService.registerBook(bookDto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("already exists with different title and/or author"));
    }

    @Test
    void testGetAllBooks_Success() {
        List<Book> books = List.of(
                new Book(1, "978-3-16-148410-0", "Book Title 1", "Author 1", false),
                new Book(2, "978-3-16-148410-1", "Book Title 2", "Author 2", false)
        );

        when(bookRepository.findAll()).thenReturn(books);

        ResponseEntity<List<BookEto>> responseEntity = bookService.getAllBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books.size(), responseEntity.getBody().size());
        assertEquals("Book Title 1", responseEntity.getBody().get(0).getBookTitle());
        assertEquals("Author 2", responseEntity.getBody().get(1).getBookAuthor());
    }

    @Test
    void testGetAllBooks_Exception() {
        when(bookRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<List<BookEto>> responseEntity = bookService.getAllBooks();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
    }
}

