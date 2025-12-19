package com.library.controllers;

import com.library.models.Loan;
import com.library.repositories.projections.LoanView;
import com.library.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int issueBook(
            @RequestParam Long bookId,
            @RequestParam Long readerId
    ) {
        return loanService.issueBook(bookId, readerId);
    }

    @GetMapping
    public List<LoanView> getAllLoans() {
        return loanService.getAllLoans();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
