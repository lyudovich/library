INSERT INTO categories (name) VALUES ('Фантастика'), ('Наука'), ('Класика');

INSERT INTO authors (name) VALUES ('Джордж Орвелл'), ('Айзек Азімов'), ('Стівен Кінг');

INSERT INTO books (title, isbn, category_id) VALUES
                                                 ('1984', '978-0451524935', 3),
                                                 ('Фундація', '978-0553293357', 1),
                                                 ('Сяйво', '978-0307743657', 1);

INSERT INTO book_authors (book_id, author_id) VALUES (1, 1), (2, 2), (3, 3);

INSERT INTO readers (full_name, email, is_deleted) VALUES
                                                       ('Іван Петренко', 'ivan@email.com', false),
                                                       ('Марія Коваленко', 'maria@email.com', false),
                                                       ('Олексій Бойко', 'oleksii@email.com', false);


INSERT INTO loans (book_id, reader_id, loan_date, due_date, return_date) VALUES
(1, 1, '2025-11-01', '2025-11-15', '2025-11-14'),
(2, 2, '2025-12-10', '2025-12-24', NULL),
(3, 3, '2025-11-20', '2025-12-04', NULL);
