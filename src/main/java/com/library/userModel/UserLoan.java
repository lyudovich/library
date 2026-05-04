package com.library.userModel;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserLoan {
    Long id;
    LocalDate loanDate;
    LocalDate dueDate;
    LocalDate returnDate;

    Long bookId;
    String bookTitle;
    String bookIsbn;

    Long readerId;
    String readerFullName;
    String readerEmail;
}