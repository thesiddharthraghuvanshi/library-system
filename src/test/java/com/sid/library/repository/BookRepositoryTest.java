package com.sid.library.repository;

import com.sid.library.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByIsbnNumber() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbnNumber("1234567890");

        bookRepository.save(book);

        Optional<List<Book>> foundBooksOptional = bookRepository.findByIsbnNumber("1234567890");

        Assertions.assertTrue(foundBooksOptional.isPresent(), "Expected to find a book");
        List<Book> foundBooks = foundBooksOptional.get();
        Assertions.assertEquals(1, foundBooks.size(), "Expected to find exactly one book");

        Book foundBook = foundBooks.get(0);
        Assertions.assertEquals("Test Book", foundBook.getTitle(), "Expected book title to match");
    }
}
