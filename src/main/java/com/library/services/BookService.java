package com.library.services;

import com.library.models.Book;
import com.library.models.BookCreatedEvent;
import com.library.projections.BookView;
import com.library.repositories.BookRepository;
import com.library.services.event.EventBus;
import com.library.userModel.UserBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private EventBus eventBus;

    @Transactional
    public Book createBook(Book book) {

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Назва книги не може бути порожньою");
        }

        Book saved = bookRepository.save(book);

        //  SYNC
        try {
            auditService.log("BOOK_CREATED_SYNC", saved.getTitle());
        } catch (Exception e) {
            System.err.println("Audit sync failed");
        }

        // ASYNC
        eventBus.publish(new BookCreatedEvent(
                saved.getId(),
                saved.getTitle()
        ));

        return saved;
    }

    @Transactional(readOnly = true)
    public List<UserBook> getAllBooks() {
        return bookRepository.findAllBy().stream()
                .map(bv -> {
                    UserBook ub = new UserBook();
                    ub.setId(bv.getId());
                    ub.setTitle(bv.getTitle());
                    ub.setIsbn(bv.getIsbn());
                    ub.setCategory(bv.getCategory() != null ? bv.getCategory().getName() : null);
                    return ub;
                })
                .toList();
    }

    @Transactional
    public Book updateBookTitle(Long id, String newTitle) {
        int updated = bookRepository.updateBookTitleNative(id, newTitle);
        if (updated == 0) {
            throw new RuntimeException("Книгу з ID " + id + " не знайдено");
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<BookView> findByTitleContainingIgnoreCase(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
