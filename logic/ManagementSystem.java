package logic;

import models.User;
import models.Restaurant;
import java.util.LinkedList;
import java.util.HashMap;

public class ManagementSystem {

    private LinkedList<User> userList;
    private LinkedList<Restaurant> restaurantList;
    private HashMap<String, User> userTable;
    private HashMap<String, Restaurant> restaurantTable;

    public ManagementSystem() {
        this.userList = new LinkedList<>();
        this.restaurantList = new LinkedList<>();
        this.userTable = new HashMap<>();
        this.restaurantTable = new HashMap<>();
    }

    //Insertion into Linked List and Hash Table takes O(1) time.
    public void registerUser(User user) {
        if (user == null || user.getUserId() == null) {
            System.out.println("Error: Invalid user details provided.");
            return;
        }
        
        // Prevent duplicate IDs
        if (userTable.containsKey(user.getUserId())) {
            System.out.println("Error: User with ID " + user.getUserId() + " already exists.");
            return;
        }

        userList.add(user);                      // Append to storage list - O(1)
        userTable.put(user.getUserId(), user);   // Insert into hash table for indexing - O(1)
        System.out.println("User successfully registered: " + user.getName());
    }
    
    public void removeUser(String userId) {
        if (!userTable.containsKey(userId)) {
            System.out.println("Error: User ID " + userId + " not found.");
            return;
        }

        User userToRemove = userTable.get(userId);
        
        userList.remove(userToRemove); // Remove from Linked List
        userTable.remove(userId);      // Remove from Hash Table
        System.out.println("User with ID " + userId + " has been successfully removed.");
    }


    public User getUserProfile(String userId) {
        // O(1) retrieve from hash table
        return userTable.get(userId); 
    }

    public void registerRestaurant(Restaurant restaurant) {
        if (restaurant == null || restaurant.getRestaurantId() == null) {
            System.out.println("Error: Invalid restaurant details provided.");
            return;
        }

        if (restaurantTable.containsKey(restaurant.getRestaurantId())) {
            System.out.println("Error: Restaurant ID " + restaurant.getRestaurantId() + " already exists.");
            return;
        }

        restaurantList.add(restaurant);
        restaurantTable.put(restaurant.getRestaurantId(), restaurant);
        System.out.println("Restaurant successfully registered: " + restaurant.getRestaurantName());
    }
 
    public void removeRestaurant(String restaurantId) {
        if (!restaurantTable.containsKey(restaurantId)) {
            System.out.println("Error: Restaurant ID " + restaurantId + " not found.");
            return;
        }

        Restaurant restaurantToRemove = restaurantTable.get(restaurantId);
        
        restaurantList.remove(restaurantToRemove);
        restaurantTable.remove(restaurantId);
        System.out.println("Restaurant with ID " + restaurantId + " has been removed.");
    }

    
    //Fetches a restaurant profile instantly.
    public Restaurant getRestaurant(String restaurantId) {
        return restaurantTable.get(restaurantId);
    }

    public void displayAll() {
        System.out.println("\n========= SYSTEM MANAGEMENT RECORDS =========");
        
        System.out.println("\n--- REGISTERED USERS ---");
        if (userList.isEmpty()) {
            System.out.println("No users are registered in the system.");
        } else {
            for (User u : userList) {
                System.out.println("[ID: " + u.getUserId() + "] Name: " + u.getName());
            }
        }

        System.out.println("\n--- REGISTERED RESTAURANTS ---");
        if (restaurantList.isEmpty()) {
            System.out.println("No restaurants are registered in the system.");
        } else {
            for (Restaurant r : restaurantList) {
                System.out.println("[ID: " + r.getRestaurantId() + "] Name: " + r.getRestaurantName()
                        + " | Location: " + r.getLocation());
            }
        }
    }
}
