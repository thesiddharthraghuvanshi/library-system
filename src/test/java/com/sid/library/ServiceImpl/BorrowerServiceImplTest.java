package com.sid.library.ServiceImpl;

import com.sid.library.model.Book;
import com.sid.library.model.Borrower;
import com.sid.library.model.etos.BorrowerEto;
import com.sid.library.repository.BookRepository;
import com.sid.library.repository.BorrowerRepository;
import com.sid.library.serviceImpl.BorrowerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class BorrowerServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerServiceImpl borrowerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterBorrower_Success() {
        String name = "First Last";
        String email = "first.last@mail.com";
        Borrower existingBorrower = null;

        when(borrowerRepository.findByEmail(email)).thenReturn(existingBorrower);

        when(borrowerRepository.save(any(Borrower.class))).thenReturn(new Borrower());

        ResponseEntity<String> responseEntity = borrowerService.registerBorrower(name, email);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Borrower registered successfully", responseEntity.getBody());
    }

    @Test
    void testRegisterBorrower_DuplicateName() {
        String name = "First Last";
        String email = "first.last@mail.com";
        Borrower existingBorrower = new Borrower(1, name, email, new ArrayList<>());

        when(borrowerRepository.findByEmail(email)).thenReturn(existingBorrower);

        ResponseEntity<String> responseEntity = borrowerService.registerBorrower(name, email);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Borrower with Email " + email+ " already exists with same name"));
    }

    @Test
    void testBorrowBook_Success() {
        Integer borrowerId = 1;
        Integer bookId = 1;
        Borrower borrower = new Borrower(borrowerId, "First Last", "first.last@mail.com", new ArrayList<>());
        Book book = new Book(bookId, "978-3-16-148410-0", "Book Title", "Author", false);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        ResponseEntity<BorrowerEto> responseEntity = borrowerService.borrowBook(borrowerId, bookId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testBorrowBook_BookNotFound() {
        Integer borrowerId = 1;
        Integer bookId = 1;
        Borrower borrower = new Borrower(borrowerId, "First Last", "first.last@mail.com", new ArrayList<>());

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));

        ResponseEntity<BorrowerEto> responseEntity = borrowerService.borrowBook(borrowerId, bookId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testReturnBook_Success() {
        Integer borrowerId = 1;
        Integer bookId = 1;
        Borrower borrower = new Borrower(borrowerId, "First Last", "first.last@mail.com", new ArrayList<>());
        Book book = new Book(bookId, "978-3-16-148410-0", "Book Title", "Author", true);

        borrower.getBooks().add(book);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        ResponseEntity<BorrowerEto> responseEntity = borrowerService.returnBook(borrowerId, bookId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testReturnBook_BookNotBorrowed() {
        Integer borrowerId = 1;
        Integer bookId = 1;
        Borrower borrower = new Borrower(borrowerId, "First Last", "first.last@mail.com", new ArrayList<>());
        Book book = new Book(bookId, "978-3-16-148410-0", "Book Title", "Author", false);

        borrower.getBooks().add(book);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));

        ResponseEntity<BorrowerEto> responseEntity = borrowerService.returnBook(borrowerId, bookId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}

