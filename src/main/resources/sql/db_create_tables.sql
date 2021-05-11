/* delete tables if they exist already - ensuring a clean db*/
DROP TABLE IF EXISTS stocks.user_stocks CASCADE;
DROP TABLE IF EXISTS stocks.person CASCADE;
DROP TABLE IF EXISTS stocks.quotes CASCADE;


/* creates a table to store stock quote data */
CREATE TABLE stocks.quotes
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  symbol VARCHAR(4) NOT NULL,
  quoteDate DATETIME NOT NULL,
  price DECIMAL(8,2) NOT NULL
);

/* creates a table to store a list of people */
CREATE TABLE stocks.person
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL
);

/* A list of people and their stocks */
CREATE TABLE stocks.user_stocks
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    person_id INT NOT NULL,
    quote_id INT NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person (ID),
    FOREIGN KEY (quote_id) REFERENCES quotes (ID)
);