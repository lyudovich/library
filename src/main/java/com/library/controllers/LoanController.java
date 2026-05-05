package com.library.controllers;

import com.library.core.services.ILoanService;
import com.library.userModel.UserLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int issueBook(
            @RequestParam Long bookId,
            @RequestParam Long readerId
    ) {
        return loanService.issueBook(bookId, readerId);
    }

    @GetMapping
    public List<UserLoan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
