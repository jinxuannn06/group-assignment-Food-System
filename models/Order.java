package models;

public class Order {
    private String orderId;
    private String userId;
    private String restaurantId;
    private String foodDetails;
    private double price;

    public Order(String orderId, String userId, String restaurantId, String foodDetails, double price) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.foodDetails = foodDetails;
        this.price = price;
    }

    // Getters and Setters
    public String getOrderId() { 
        return orderId; 
    }

    public String getUserId() { 
        return userId; 
    }

    public String getRestaurantId() { 
        return restaurantId; 
    }

    public String getFoodDetails() { 
        return foodDetails; 
    }

    public double getPrice() {
         return price; 
        }

    @Override
    public String toString() {
        return "Order [ID=" + orderId + ", User=" + userId + ", Restaurant=" + restaurantId + 
               ", Items=" + foodDetails + ", Total=RM" + price + "]";
    }
}
