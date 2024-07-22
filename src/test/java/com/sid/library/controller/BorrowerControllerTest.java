package com.sid.library.controller;

import com.sid.library.model.etos.BorrowerEto;
import com.sid.library.service.BorrowerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowerControllerTest {

    @Mock
    private BorrowerService borrowerService;

    @InjectMocks
    private BorrowerController borrowerController;

    @Test
    public void testRegisterBorrower() {
        String borrowerName = "John Doe";
        String borrowerMail = "john.doe@example.com";

        when(borrowerService.registerBorrower(anyString(), anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Borrower registered successfully"));

        ResponseEntity<String> responseEntity = borrowerController.registerBorrower(borrowerName, borrowerMail);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Borrower registered successfully", responseEntity.getBody());
    }

    @Test
    public void testBorrowBook() {
        Integer borrowerId = 1;
        Integer bookId = 101;
        BorrowerEto borrowerEto = new BorrowerEto(borrowerId, "John Doe", "", List.of());

        when(borrowerService.borrowBook(borrowerId, bookId))
                .thenReturn(ResponseEntity.ok(borrowerEto));

        ResponseEntity<BorrowerEto> responseEntity = borrowerController.borrowBook(borrowerId, bookId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(borrowerEto, responseEntity.getBody());
    }

    @Test
    public void testReturnBook() {
        // Given
        Integer borrowerId = 1;
        Integer bookId = 101;
        BorrowerEto borrowerEto = new BorrowerEto(borrowerId, "John Doe", "", List.of());

        when(borrowerService.returnBook(borrowerId, bookId))
                .thenReturn(ResponseEntity.ok(borrowerEto));

        ResponseEntity<BorrowerEto> responseEntity = borrowerController.returnBook(borrowerId, bookId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(borrowerEto, responseEntity.getBody());
    }
}
