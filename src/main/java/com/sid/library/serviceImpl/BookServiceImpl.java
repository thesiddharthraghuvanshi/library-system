package com.sid.library.serviceImpl;

import com.sid.library.model.Book;
import com.sid.library.model.dtos.BookDto;
import com.sid.library.model.etos.BookEto;
import com.sid.library.repository.BookRepository;
import com.sid.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    @Transactional
    public ResponseEntity<String> registerBook(BookDto bookDto) {
        log.info("Inside registerBook function | BookService");
        try {
            Optional<List<Book>> existingBooks = bookRepository.findByIsbnNumber(bookDto.getBookISBN());

            if (existingBooks.isPresent() && !existingBooks.get().isEmpty()) {
                Book existingBook = existingBooks.get().get(0);
                if (!existingBook.getTitle().equals(bookDto.getBookTitle()) ||
                        !existingBook.getAuthor().equals(bookDto.getBookAuthor())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with ISBN " + bookDto.getBookISBN() + " already exists with different title and/or author");
                }
            }

            Book book = new Book();
            book.setIsbnNumber(bookDto.getBookISBN());
            book.setTitle(bookDto.getBookTitle());
            book.setAuthor(bookDto.getBookAuthor());
            book.setBorrowed(false);
            log.warn("Calling bookRepository");
            bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body("Book registered successfully");
        } catch (DataAccessException e) {
            log.error("Data access error at registerBook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register book due to database issue");
        } catch (Exception e) {
            log.error("Error at registerBook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register book: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<BookEto>> getAllBooks() {
        log.info("Inside getAllBooks function | BookService");
        try {
            log.warn("Calling bookRepository");
            List<Book> books = bookRepository.findAll();
            List<BookEto> booksEto = books.stream()
                    .map(this::mapToBookEto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(booksEto);
        } catch (Exception e) {
            log.error("Error at getAllBooks: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    private BookEto mapToBookEto(Book book) {
        return new BookEto(
                book.getBookId(),
                book.getIsbnNumber(),
                book.getTitle(),
                book.getAuthor(),
                book.getBorrowed()
        );
    }
}
