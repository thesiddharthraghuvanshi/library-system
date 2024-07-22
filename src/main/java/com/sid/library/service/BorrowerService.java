package com.sid.library.service;

import com.sid.library.model.etos.BorrowerEto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface BorrowerService {

    ResponseEntity<String> registerBorrower(String name, String mail);

    ResponseEntity<BorrowerEto> borrowBook(Integer borrowerId, Integer bookId);

    ResponseEntity<BorrowerEto> returnBook(Integer borrowerId, Integer bookId);
}
