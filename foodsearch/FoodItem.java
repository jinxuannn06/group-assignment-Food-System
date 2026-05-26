package foodsearch;

public class FoodItem {
    private String foodId;
    private String name;
    private String category;
    private double price;
    private String restaurantId;

    public FoodItem(String foodId, String name, String category, double price, String restaurantId) {
        this.foodId = foodId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "foodId='" + foodId + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", restaurantId='" + restaurantId + '\'' +
                '}';
    }
}
