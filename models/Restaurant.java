package models;

public class Restaurant {
    private String restaurantId;
    private String restaurantName;
    private String location;

    // Constructor
    public Restaurant(String restaurantId, String restaurantName, String location) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.location = location;
    }

    // Getters and Setters
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Structural toString for Member 1's displayAll() function
    @Override
    public String toString() {
        return "Restaurant [ID=" + restaurantId + ", Name=" + restaurantName + ", Location=" + location + "]";
    }
}