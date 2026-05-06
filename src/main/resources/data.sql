INSERT INTO categories (name) VALUES
  ('Фантастика'),
  ('Наука'),
  ('Класика');

INSERT INTO authors (name) VALUES
   ('Джордж Орвелл'),
   ('Айзек Азімов'),
   ('Стівен Кінг');

INSERT INTO books (title, isbn, category_id, version) VALUES
  ('1984', '978-0451524935', 6, 0),
  ('Фундація', '978-0553293357', 5, 0),
  ('Сяйво', '978-0307743657', 7, 0);

INSERT INTO book_authors (book_id, author_id) VALUES
  (1, 1),
  (2, 2),
  (3, 3);

INSERT INTO readers (full_name, email, is_deleted, updated_at) VALUES
   ('Іван Петренко', 'ivan@email.com', false, NOW()),
   ('Марія Коваленко', 'maria@email.com', false, NOW()),
   ('Олексій Бойко', 'oleksii@email.com', false, NOW());

INSERT INTO loans (book_id, reader_id, loan_date, due_date, return_date) VALUES
     (1, 1, CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '4 days', CURRENT_DATE - INTERVAL '1 day'),
     (2, 2, CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE + INTERVAL '9 days', NULL),
     (3, 3, CURRENT_DATE - INTERVAL '2 days', CURRENT_DATE + INTERVAL '12 days', NULL);

INSERT INTO users (id, username, password, role)
VALUES (1, 'admin', '$2a$10$kdPoG3cFPLUHvVudt//CKeV/L/uqiWJKqh.6mlqftTbwtoJA6Nw9S',
        'USER');

