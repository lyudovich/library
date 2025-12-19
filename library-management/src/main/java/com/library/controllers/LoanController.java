package com.library.controllers;

import com.library.models.Loan;
import com.library.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan issueBook(
            @RequestParam Long bookId,
            @RequestParam Long readerId
    ) {
        return loanService.issueBook(bookId, readerId);
    }
}
