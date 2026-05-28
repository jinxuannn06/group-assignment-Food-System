package logic;

import models.Order;
import java.util.LinkedList;
import java.util.HashSet;

public class OrderProcessingSystem {
    // FIFO Queue for processing orders in order
    private LinkedList<Order> orderQueue;
    // LIFO Stack for managing actions to support Undo operations
    private LinkedList<Order> undoStack;
    private HashSet<String> activeOrderIds;

    public OrderProcessingSystem() {
        this.orderQueue = new LinkedList<>();
        this.undoStack = new LinkedList<>();
        this.activeOrderIds = new HashSet<>();
    }

    // Enqueue an order into system and push to undo history
    public void placeOrder(Order order) {
        if (order == null) 
            return;

        if (activeOrderIds.contains(order.getOrderId())) {
            System.out.println("Error: Order ID " + order.getOrderId() + " already exists in the queue!");
            return; // Reject placement immediately
        }

        orderQueue.addLast(order);  // Queue Behavior: Adds to the tail
        undoStack.addFirst(order); // Stack Behavior: Pushes to the head
        activeOrderIds.add(order.getOrderId());
        System.out.println("Success: Order " + order.getOrderId() + " placed into processing queue.");
    }

    // Undo Feature: Reverts the last placed order
    public void undoLastOrder() {
        // Loop to discard orders from the stack that have already been processed from the queue
        while (!undoStack.isEmpty()) {
            // Peek at the top order to check if it's still in the queue before removing it from the stack
            Order lastPlacedOrder = undoStack.peekFirst();

            if (orderQueue.contains(lastPlacedOrder)) {
                // If it is still in the queue, perform the actual undo
                undoStack.removeFirst(); // Pop from stack
                orderQueue.remove(lastPlacedOrder); // Remove from queue
                activeOrderIds.remove(lastPlacedOrder.getOrderId());
                System.out.println("Undo Successful: Order " + lastPlacedOrder.getOrderId() + " has been cancelled.");
                return; // Exit out safely after successful undo
            } else {
                // AUTO-CLEANUP: The order was already processed, discard it from stack and check the next one
                undoStack.removeFirst();
            }
        }
        // If the loop finishes or stack was empty initially
        System.out.println("Error: No active pending orders found in history to undo.");
    }

    // Dequeue Feature: Simulates kitchen processing the earliest incoming order
    public Order processNextOrder() {
        if (orderQueue.isEmpty()) {
            System.out.println("System: No active orders in queue to process.");
            return null;
        }

        // Poll from Queue (FIFO)
        Order nextOrder = orderQueue.removeFirst();
        activeOrderIds.remove(nextOrder.getOrderId());
        System.out.println("Processing System: Handling " + nextOrder);
        return nextOrder;
    }

    // Displays current snapshot of the data queues
    public void displayOrderFlow() {
        System.out.println("\n--- CURRENT ACTIVE ORDERS ---");
        if (orderQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            int Position = 1;
            for (Order o : orderQueue) {
                System.out.println(Position + ". " + o);
                Position++;
            }
        }
    }
}
