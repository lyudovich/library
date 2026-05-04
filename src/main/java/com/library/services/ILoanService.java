package com.library.services;

import com.library.projections.LoanView;
import com.library.userModel.UserLoan;
import java.util.List;

public interface ILoanService {
    int issueBook(Long bookId, Long readerId);
    List<UserLoan> getAllLoans();
    List<UserLoan> getActiveLoans();
    void deleteLoan(Long loanId);
}