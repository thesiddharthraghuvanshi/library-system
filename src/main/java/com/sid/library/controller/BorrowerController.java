package com.sid.library.controller;

import com.sid.library.model.etos.BorrowerEto;
import com.sid.library.service.BorrowerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("borrower")
@Slf4j
public class BorrowerController {

    @Autowired
    BorrowerService borrowerService;

    @PostMapping("register")
    public ResponseEntity<String> registerBorrower(@RequestParam String borrowerName, @RequestParam @Email(message = "Please provide a valid email address") String borrowerMail) {
        log.info("Inside registerBorrower | BorrowerController");
        return borrowerService.registerBorrower(borrowerName, borrowerMail);
    }

    @PutMapping("borrow")
    public ResponseEntity<BorrowerEto> borrowBook(@RequestParam Integer borrowerId, @RequestParam Integer bookId) {
        log.info("Inside borrowBook | BorrowerController");
        return borrowerService.borrowBook(borrowerId, bookId);
    }

    @PutMapping("return")
    public ResponseEntity<BorrowerEto> returnBook(@RequestParam Integer borrowerId, @RequestParam Integer bookId) {
        log.info("Inside returnBook | BorrowerController");
        return borrowerService.returnBook(borrowerId, bookId);
    }

}
