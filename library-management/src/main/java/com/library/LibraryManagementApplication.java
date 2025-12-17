package com.library;

import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner testDatabase(BookRepository bookRepo,LoanRepository loanRepo) {
		return args -> {
			System.out.println("\n=== ПЕРЕВІРКА НАПОВНЕННЯ БАЗИ ДАНИХ ===");

			System.out.println("КНИГИ В БАЗІ:");
			bookRepo.findAll().forEach(book ->
					System.out.println("- " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")"));

			System.out.println("\nАКТИВНІ ВИДАЧІ (LOANS):");
			loanRepo.findAll().forEach(loan ->
					System.out.println("- Книга ID: " + loan.getBook().getId() +
							" | Читач ID: " + loan.getReader().getId() +
							" | Дата повернення: " + loan.getDueDate()));

			System.out.println("========================================\n");
		};
	}
}