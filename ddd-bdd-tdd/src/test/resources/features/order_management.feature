Feature: Order Management
  As a user of the book order system
  I want to manage book orders
  So that I can create, view, and update orders

  Scenario: Successfully creating a new order with available products and stock deduction
    Given a book with ISBN "978-0321765723", title "The Lord of the Rings", author "J.R.R. Tolkien", price 25.00
    And an inventory item "978-0321765723" with initial stock of 10
    And a book with ISBN "978-0132350884", title "Clean Code", author "Robert C. Martin", price 35.00
    And an inventory item "978-0132350884" with initial stock of 5
    When I try to create an order for "Alice Wonderland" with the following items:
      | productId        | quantity |
      | 978-0321765723 | 2        |
      | 978-0132350884 | 1        |
    Then the order should be created successfully with status "PENDING"
    And an "OrderCreatedEvent" event should have been published for the order of "Alice Wonderland"
    And the inventory service receives the stock deduction request for the order
    And the order should transition to status "CONFIRMED"
    And the stock for product "978-0321765723" should be 8
    And the stock for product "978-0132350884" should be 4
    And a "ProductStockLowEvent" event should have been published for product "978-0132350884" with stock 4