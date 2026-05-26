package logic;

import models.User;
import models.Restaurant;
import java.util.LinkedList;
import java.util.HashMap;

 /* Data Structures Used: 
 * - LinkedList: For sequential storage, allowing efficient O(1) insertions/deletions.
 * - HashMap (Hash Table): For optimized search operations achieving O(1) time complexity.
 */
public class ManagementSystem {

    // Part A: Storage Management (Linked Lists) 
    // Linked Lists are used here because user and restaurant registrations happen dynamically.
    // Unlike arrays, a Linked List can expand or shrink at runtime without needing memory reallocation.
    private LinkedList<User> userList;
    private LinkedList<Restaurant> restaurantList;

    // Part B: Retrieval Optimization (Hash Tables)
    // Searching sequentially through a Linked List takes O(n) time. 
    // By mirroring our storage in Hash Maps, we look up objects using their Unique IDs as keys,
    // which processes via a hash function to locate the data instantly in O(1) constant time.
    private HashMap<String, User> userTable;
    private HashMap<String, Restaurant> restaurantTable;

    
    //Constructor initializing both the storage lists and retrieval hash tables.
    public ManagementSystem() {
        this.userList = new LinkedList<>();
        this.restaurantList = new LinkedList<>();
        this.userTable = new HashMap<>();
        this.restaurantTable = new HashMap<>();
    }

    //User's Managemment Logic
    //Registers a new customer/admin in the system.
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

    
    //Removes a user from storage and indexing maps.
    
    public void removeUser(String userId) {
        if (!userTable.containsKey(userId)) {
            System.out.println("Error: User ID " + userId + " not found.");
            return;
        }

        // Fetch the user object from our map in O(1) time to remove it from the list
        User userToRemove = userTable.get(userId);
        
        userList.remove(userToRemove); // Remove from Linked List
        userTable.remove(userId);      // Remove from Hash Table
        System.out.println("User with ID " + userId + " has been successfully removed.");
    }

    
    //OPTIMIZED RETRIEVAL: Fetches a user profile instantly.
    //Efficiency: O(1) constant time complexity.
    
    public User getUserProfile(String userId) {
        // Instead of iterating through the whole list, the key is hashed directly to its memory slot.
        return userTable.get(userId); 
    }


    //Restaurant Management Logic
    //Registers a new restaurant profile in the system.
    
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

    
    //Removes a restaurant profile from the system. 
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

    
    //OPTIMIZED RETRIEVAL: Fetches a restaurant profile instantly.
    //Efficiency: O(1) constant time complexity.
    public Restaurant getRestaurant(String restaurantId) {
        return restaurantTable.get(restaurantId);
    }


    //View System Logic
    //Displays all registered users and restaurants.
    //Sequential printing through the underlying lists.

    public void displayAll() {
        System.out.println("\n========= SYSTEM MANAGEMENT RECORDS =========");
        
        System.out.println("\n--- REGISTERED USERS ---");
        if (userList.isEmpty()) {
            System.out.println("No users are registered in the system.");
        } else {
            for (User u : userList) {
                // Assumes your User class has a structural toString() method
                System.out.println("[ID: " + u.getUserId() + "] Name: " + u.getName());
            }
        }

        System.out.println("\n--- REGISTERED RESTAURANTS ---");
        if (restaurantList.isEmpty()) {
            System.out.println("No restaurants are registered in the system.");
        } else {
            for (Restaurant r : restaurantList) {
                // Assumes your Restaurant class has a getRestaurantName() method
                System.out.println("[ID: " + r.getRestaurantId() + "] Name: " + r.getRestaurantName());
            }
        }
    }
}