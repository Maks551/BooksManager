DROP TABLE IF EXISTS book_manager;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1;

CREATE TABLE book_manager(
  id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  title VARCHAR(255) NOT NULL ,
  author VARCHAR(255) NOT NULL ,
  description VARCHAR(255) NOT NULL ,
  isbn VARCHAR(100) NOT NULL ,
  print_year INT NOT NULL ,
  read_already BOOLEAN NOT NULL
);