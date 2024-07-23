package com.sid.library.serviceImpl;

import com.sid.library.model.Book;
import com.sid.library.model.Borrower;
import com.sid.library.model.etos.BookEto;
import com.sid.library.model.etos.BorrowerEto;
import com.sid.library.repository.BookRepository;
import com.sid.library.repository.BorrowerRepository;
import com.sid.library.service.BorrowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BorrowerServiceImpl implements BorrowerService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BorrowerRepository borrowerRepository;

    @Override
    public ResponseEntity<String> registerBorrower(String name, String email) {
        log.info("Inside registerBorrower function | BorrowerService");
        try {
            Borrower existingBorrower = borrowerRepository.findByEmail(email);

            if(existingBorrower != null){
                if(existingBorrower.getName().equalsIgnoreCase(name)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Borrower with Email " + email+ " already exists with same name");
                }
            }

            Borrower borrower = new Borrower();
            borrower.setName(name);
            borrower.setEmail(email);
            log.warn("Calling borrowerRepository");
            borrowerRepository.save(borrower);
            return ResponseEntity.status(HttpStatus.CREATED).body("Borrower registered successfully");
        } catch (Exception e) {
            log.error("Error at registerBorrower: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register borrower: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<BorrowerEto> borrowBook(Integer borrowerId, Integer bookId) {
        log.info("Inside borrowBook function | BorrowerService");
        try {
            log.warn("Calling bookRepository");
            Optional<Book> book = bookRepository.findById(bookId);

            if (book.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else if(book.get().getBorrowed()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Book updatedBook = updateBookStatus(book.get(), true);

            return updateBorrowerBooks(borrowerId, updatedBook, true);
        } catch (Exception e) {
            log.error("Error at borrowBook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle exception appropriately
        }
    }

    @Override
    @Transactional
    public ResponseEntity<BorrowerEto> returnBook(Integer borrowerId, Integer bookId) {
        log.info("Inside returnBook function | BorrowerService");
        try {
            log.warn("Calling bookRepository");
            Optional<Book> book = bookRepository.findById(bookId);

            if (book.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else if(!book.get().getBorrowed()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Book updatedBook = updateBookStatus(book.get(), false);

            return updateBorrowerBooks(borrowerId, updatedBook, false);
        } catch (Exception e) {
            log.error("Error at returnBook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle exception appropriately
        }
    }

    private Book updateBookStatus(Book book, Boolean borrowed) {
        log.info("Inside updateBookStatus function | BorrowerService");
        try {
            book.setBorrowed(borrowed);
            log.warn("Calling bookRepository");
            return bookRepository.save(book);
        } catch (Exception e) {
            log.error("Error at updateBookStatus: " + e.getMessage());
            return null;
        }
    }

    @Transactional
    private ResponseEntity<BorrowerEto> updateBorrowerBooks(Integer borrowerId, Book book, Boolean borrowed) {
        log.info("Inside updateBorrowerBooks function | BorrowerService");
        try {
            log.warn("Calling borrowerRepository");
            Optional<Borrower> optionalBorrower = borrowerRepository.findById(borrowerId);
            if (optionalBorrower.isPresent()) {
                Borrower borrower = optionalBorrower.get();
                List<Book> borrowedBooks = borrower.getBooks();
                if (borrowed) {
                    borrowedBooks.add(book);
                } else {
                    if(borrowedBooks.contains(book)) {
                        borrowedBooks.remove(book);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                    }
                }
                borrower.setBooks(borrowedBooks);
                log.warn("Calling borrowerRepository");
                borrowerRepository.save(borrower);

                return ResponseEntity.ok().body(mapToBorrowerEto(borrower));
            } else {
                log.warn("Calling bookRepository");
                book.setBorrowed(!borrowed);
                bookRepository.save(book);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error at updateBorrowerBooks: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private BorrowerEto mapToBorrowerEto(Borrower borrower) {
        return new BorrowerEto(
                borrower.getBorrowerId(),
                borrower.getName(),
                borrower.getEmail(),
                borrower.getBooks().stream()
                        .map(b -> new BookEto(
                                b.getBookId(),
                                b.getIsbnNumber(),
                                b.getTitle(),
                                b.getAuthor(),
                                b.getBorrowed()))
                        .collect(Collectors.toList())
        );
    }
}
