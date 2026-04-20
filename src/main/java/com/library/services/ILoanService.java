package com.library.services;

import com.library.projections.LoanView;
import java.util.List;

public interface ILoanService {
    int issueBook(Long bookId, Long readerId);
    List<LoanView> getAllLoans();
    List<LoanView> getActiveLoans();
    void deleteLoan(Long loanId);
}