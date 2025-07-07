Feature: Order Management
  As a user of the book order system
  I want to manage book orders
  So that I can create, view, and update orders

  Scenario: Successfully creating a new order and requesting stock reservation
    Given a book with ISBN "978-0321765723", title "The Lord of the Rings", author "J.R.R. Tolkien", price 25.00
    And an inventory item "978-0321765723" with initial stock of 10
    And a book with ISBN "978-0132350884", title "Clean Code", author "Robert C. Martin", price 35.00
    And an inventory item "978-0132350884" with initial stock of 5
    When I try to create an order for "Alice Wonderland" with the following items:
      | productId      | quantity |
      | 978-0321765723 | 2        |
      | 978-0132350884 | 1        |
    Then the order should be created successfully with status "PENDING"
    And an "OrderCreatedEvent" event should have been published for the order of "Alice Wonderland"

  Scenario: Confirming an order after successful stock reservation
    Given an existing order for "Alice Wonderland" with status "PENDING" and items:
      | productId      | quantity |
      | 978-0321765723 | 2        |
      | 978-0132350884 | 1        |
    And the stock for product "978-0321765723" is 8
    And the stock for product "978-0132350884" is 4
    When the external system confirms the order
    Then the order should transition to status "CONFIRMED"
    And the stock for product "978-0321765723" should be 6
    And the stock for product "978-0132350884" should be 3
    And a "ProductStockLowEvent" event should have been published for product "978-0132350884" with stock 3

  Scenario: Order creation fails due to insufficient stock
    Given a book with ISBN "978-0134786275", title "Effective Java", author "Joshua Bloch", price 40.00
    And an inventory item "978-0134786275" with initial stock of 2
    When I try to create an order for "Bob TheBuilder" with the following items:
      | productId      | quantity |
      | 978-0134786275 | 3        |
    Then the order creation should fail with message "Not enough stock for ISBN 978-0134786275. Requested: 3, Available: 2."
    And the stock for product "978-0134786275" should be 2

  Scenario: Order cancellation and stock release
    Given a book with ISBN "978-1491904244", title "Designing Data-Intensive Applications", author "Martin Kleppmann", price 60.00
    And an inventory item "978-1491904244" with initial stock of 5
    And an order for "John Doe" with the following items:
      | productId      | quantity |
      | 978-1491904244 | 2        |
    When I cancel the order
    Then the order should have status "CANCELLED"
    And an "OrderCancelledEvent" event should have been published for the order
    And the inventory service receives the stock release request for the order
    And the stock for product "978-1491904244" should be 5

  Scenario: Order confirmation fails due to insufficient stock (concurrent update)
    Given an existing order for "Eve" with status "PENDING" and items:
      | productId      | quantity |
      | 978-0321765723 | 2        |
    And the stock for product "978-0321765723" is 1
    When I try to confirm the order
    Then the confirmation should fail with message "Not enough stock for ISBN 978-0321765723. Requested: 2, Available: 1."
    And the order should have status "PENDING"
    And the stock for product "978-0321765723" should be 1

  Scenario: Successfully viewing an existing order
    Given a book with ISBN "978-1234567890", title "Test Book", author "Test Author", price 10.00
    And an inventory item "978-1234567890" with initial stock of 5
    When I try to create an order for "Viewer Customer" with the following items:
      | productId      | quantity |
      | 978-1234567890 | 1        |
    Then the order should be created successfully with status "PENDING"
    When I view the order with ID of the last created order
    Then the retrieved order should have customer name "Viewer Customer" and status "PENDING"
    And it should contain the following items:
      | productId      | quantity |
      | 978-1234567890 | 1        |

  Scenario: Attempting to view a non-existent order
    When I view the order with ID "nonExistentOrderId"
    Then the order retrieval should fail with status 404