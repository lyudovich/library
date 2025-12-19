CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE authors (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(150) NOT NULL
);

CREATE TABLE books (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       isbn VARCHAR(20) NOT NULL UNIQUE,
                       category_id BIGINT NOT NULL,
                       version BIGINT DEFAULT 0,

                       CONSTRAINT fk_books_category
                           FOREIGN KEY (category_id)
                               REFERENCES categories(id)
                               ON DELETE RESTRICT
);

CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_isbn ON books(isbn);

CREATE TABLE readers (
                         id BIGSERIAL PRIMARY KEY,
                         full_name VARCHAR(200) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                         deleted_at TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_readers_email ON readers(email);

CREATE TABLE loans (
                       id BIGSERIAL PRIMARY KEY,
                       book_id BIGINT NOT NULL,
                       reader_id BIGINT NOT NULL,
                       loan_date DATE NOT NULL DEFAULT CURRENT_DATE,
                       due_date DATE NOT NULL,
                       return_date DATE,

                       CONSTRAINT chk_loan_dates CHECK (due_date >= loan_date),
                       CONSTRAINT chk_return_date CHECK (return_date IS NULL OR return_date >= loan_date),

                       CONSTRAINT fk_loans_book
                           FOREIGN KEY (book_id)
                               REFERENCES books(id),
                       CONSTRAINT fk_loans_reader
                           FOREIGN KEY (reader_id)
                               REFERENCES readers(id)
);

CREATE TABLE book_authors (
                              book_id BIGINT NOT NULL,
                              author_id BIGINT NOT NULL,
                              PRIMARY KEY (book_id, author_id),
                              CONSTRAINT fk_ba_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
                              CONSTRAINT fk_ba_author FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
);