DROP TABLE IF EXISTS book_manager;

CREATE TABLE book_manager(
  id INT NOT NULL AUTO_INCREMENT ,
  title VARCHAR(255) NOT NULL ,
  author VARCHAR(255) NOT NULL ,
  description VARCHAR(255) NOT NULL ,
  isbn VARCHAR(100) NOT NULL ,
  print_year INT NOT NULL ,
  read_already BIT NOT NULL ,
  PRIMARY KEY (id)
)
  AUTO_INCREMENT = 0;