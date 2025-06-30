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

## Order Creation and Stock Deduction

- When an order is placed, the **Order Service** performs a **synchronous stock availability check** for each item via the **Inventory Service**.
- If stock is sufficient, the **Order Service** publishes a domain event:  
  **`OrderCreatedEvent`**, including productIds and desired quantities.
- The order is initially set to the **`PENDING`** status.
- The **Order Service** includes a `confirmOrder` method. When this method is called (e.g., by an external system),
  it performs a **synchronous stock deduction** for each order line via the **Inventory Service**.

## Concurrent Stock Management

- The **Inventory Service** manages stock levels using optimistic locking to handle **concurrent access at the database level**.

## Cancellation Handling

- If an order is cancelled, an event **`OrderCancelledEvent`** is emitted to **increment the stock back**. This functionality is planned but not yet implemented.

## Low Stock Management

- If an **InventoryItem** drops below a **critical threshold** after a stock operation, a domain event:
  **`ProductStockLowEvent`** is emitted. The consumption of this event by a restocking process is not yet implemented.
