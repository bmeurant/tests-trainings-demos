# Use Case: Book Selling Platform

Our platform will be modeled with a clear separation between business contexts:

## Catalog Context

- **Book** entities have a unique **ISBN**, a **title**, an **author**, and a **price**.
- This context does **not** manage stock.

## Inventory Context

- **InventoryItem** represents a stocked item for a given **productId** (e.g., ISBN, SKU).
- Each item includes:
    - Available **quantity** (stock),
    - Specific **inventory management rules** (e.g., restocking thresholds).
- This is a **distinct and generic aggregate**.

## Order Context

- **Customers** can place **orders** that contain one or more items.

---

## Asynchronous Stock Reservation

- When an order is placed, the **Order Service** publishes a domain event:  
  **`OrderCreatedEvent`**, including productIds and desired quantities.
- The **Inventory Service** listens for this event and attempts to reserve the stock.
- The order is initially set to the **`PENDING`** status.

## Atomic Stock Decrease/Increase

- The **Inventory Service** performs **atomic SQL operations** like:  
  `UPDATE ... SET stock = stock - X WHERE stock >= X`
- This handles **concurrent access at the database level**.

## Stock Validation

- An order can only move to **`CONFIRMED`** status **if** stock reservation is validated via a return event, e.g.:
  **`StockReservedEvent`**

## Cancellation Handling

- If an order is cancelled, an event **`OrderCancelledEvent`** is emitted to **increment the stock back**.

## Low Stock Management

- If an **InventoryItem** drops below a **critical threshold** after an operation, a domain event:
  **`ProductStockLowEvent`** is emitted.
- This triggers a **generic restocking process**.