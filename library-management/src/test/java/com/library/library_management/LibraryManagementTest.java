package com.library.library_management;

import com.library.models.Reader;
import com.library.repositories.ReaderRepository;
import com.library.repositories.LoanRepository;
import com.library.services.ReaderService;
import com.library.services.LoanService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("test")
//@Transactional

public class LibraryManagementTest {

    @Autowired private ReaderService readerService;
    @Autowired private ReaderRepository readerRepository;
    @Autowired private LoanService loanService;
    @Autowired private LoanRepository loanRepository;

    @Test
    public void testAnalyticalTopReadersQuery() {
        List<Reader> topReaders = readerRepository.findTop3ReadersByBorrowCount();

        assertNotNull(topReaders);
        assertTrue(topReaders.size() <= 3, "Має повернути не більше 3 записів");

        if (!topReaders.isEmpty()) {
            assertNotNull(topReaders.get(0).getFullName());
        }
    }

    @Test
    public void testTransactionRollbackOnFailure() {
        long initialCount = loanRepository.count();

        assertThrows(RuntimeException.class, () -> {
            loanService.issueBook(1L, 999L);
        });

        long finalCount = loanRepository.count();
        assertEquals(initialCount, finalCount, "Транзакція повинна була відкотитися, кількість записів не має змінитись");
    }
}