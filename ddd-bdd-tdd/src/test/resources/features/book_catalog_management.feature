Feature: Book Catalog Management
  As a user of the book order system
  I want to manage the book catalog
  So that I can view available books and their details

  Scenario: Successfully retrieve a book by its ISBN
    Given a book with ISBN "978-0321765723", title "The Lord of the Rings", author "J.R.R. Tolkien", price 25.00
    When I retrieve the book with ISBN "978-0321765723"
    Then the retrieved book should have ISBN "978-0321765723", title "The Lord of the Rings", author "J.R.R. Tolkien", and price 25.00

  Scenario: Attempt to retrieve a non-existent book by ISBN
    When I retrieve the book with ISBN "nonExistentISBN"
    Then the book retrieval should fail with status 404 and message "Book with ISBN nonExistentISBN not found in catalog."

  Scenario: Successfully retrieve all books from the catalog
    Given a book with ISBN "978-0321765723", title "The Lord of the Rings", author "J.R.R. Tolkien", price 25.00
    And a book with ISBN "978-0132350884", title "Clean Code", author "Robert C. Martin", price 35.00
    When I retrieve all books
    Then the retrieved book list should contain 2 books
    And the retrieved book list should contain a book with ISBN "978-0321765723"
    And the retrieved book list should contain a book with ISBN "978-0132350884"

  Scenario: Retrieve all books from an empty catalog
    Given the catalog is empty
    When I retrieve all books
    Then the retrieved book list should be empty
