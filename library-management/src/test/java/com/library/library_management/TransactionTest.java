package com.library.library_management;

import com.library.repositories.LoanRepository;
import com.library.services.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionTest {

    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void testTransactionRollback() {
        long initialCount = loanRepository.count();

        try {
            loanService.issueBook(999L, 999L);
        } catch (Exception e) {
        }

        long finalCount = loanRepository.count();

        Assertions.assertEquals(initialCount, finalCount, "Транзакція не відкотилася!");
    }
}