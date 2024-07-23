package com.sid.library.repository;

import com.sid.library.model.Borrower;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BorrowerRepositoryTest {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Test
    public void testFindByEmail() {
        Borrower borrower = new Borrower();
        borrower.setName("John Doe");
        borrower.setEmail("johndoe@example.com");

        borrowerRepository.save(borrower);

        Borrower foundBorrower = borrowerRepository.findByEmail("johndoe@example.com");

        Assertions.assertNotNull(foundBorrower, "Expected to find a borrower");
        Assertions.assertEquals("John Doe", foundBorrower.getName(), "Expected borrower name to match");
    }
}
