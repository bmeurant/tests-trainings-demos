Feature: Inventory Management
  As a user of the book order system
  I want to manage inventory items
  So that I can view stock levels

  Scenario: Successfully retrieve stock for an existing inventory item
    Given an inventory item "978-0321765723" with initial stock of 10
    When I request the stock for ISBN "978-0321765723"
    Then the retrieved stock should be 10

  Scenario: Attempt to retrieve stock for a non-existent inventory item
    When I request the stock for ISBN "nonExistentISBN"
    Then the stock retrieval should fail with status 404 and message "Inventory item with ISBN nonExistentISBN not found."