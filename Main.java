import foodsearch.FoodAVLTree;
import foodsearch.FoodItem;
import java.util.Scanner;
import logic.CsvDataLoader;
import logic.ManagementSystem;
import logic.OrderProcessingSystem;
import logic.RiderDispatchSystem;
import logic.RouteOptimizationSystem;
import models.Order;
import models.Restaurant;
import models.Rider;
import models.User;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize all members' logic classes
        ManagementSystem member1 = new ManagementSystem();
        OrderProcessingSystem member2 = new OrderProcessingSystem();
        RiderDispatchSystem riderDispatchSystem = new RiderDispatchSystem();
        RouteOptimizationSystem member4 = new RouteOptimizationSystem();
        FoodAVLTree foodSearchTree = new FoodAVLTree();
        CsvDataLoader.loadDemoData(member1, riderDispatchSystem, member4, foodSearchTree);


        while (true) {
            System.out.println("\n===============================================");
            System.out.println(" SMART FOOD DELIVERY & ORDER MANAGEMENT SYSTEM ");
            System.out.println("================================================");
            System.out.println("1. User & Restaurant Management");
            System.out.println("2. Order Flow & Undo Feature");
            System.out.println("3. Delivery Assignment");
            System.out.println("4. Route Optimization ");
            System.out.println("5. Food Search");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n[1] User & Restaurant Management Options:");
                    System.out.println("a. Register User");
                    System.out.println("b. Remove User");
                    System.out.println("c. Register Restaurant");
                    System.out.println("d. Display System Records");
                    System.out.print("Select an option (a-d): ");
                    String subChoice1 = scanner.nextLine();

                    if (subChoice1.equalsIgnoreCase("a")) {
                        System.out.print("Enter User ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Contact Info: ");
                        String contact = scanner.nextLine();
                        
                        member1.registerUser(new User(id, name, contact));

                    } else if (subChoice1.equalsIgnoreCase("b")) {
                        System.out.print("Enter User ID to remove: ");
                        String id = scanner.nextLine();
                        member1.removeUser(id);

                    } else if (subChoice1.equalsIgnoreCase("c")) {
                        System.out.print("Enter Restaurant ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Restaurant Name: ");
                        String rName = scanner.nextLine();
                        System.out.print("Enter Location Node: ");
                        String loc = scanner.nextLine();
                        
                        member1.registerRestaurant(new Restaurant(id, rName, loc));

                    } else if (subChoice1.equalsIgnoreCase("d")) {
                        member1.displayAll();
                    }
                    break;

                case 2:
                    System.out.println("\n[2] Order Processing & Undo System Active:");
                    System.out.println("a. Place New Order");
                    System.out.println("b. Process Next Order");
                    System.out.println("c. Undo Last Placed Order");
                    System.out.println("d. View Pending Order Queue");
                    System.out.print("Select an option (a-d): ");
                    String subChoice2 = scanner.nextLine();

                    if (subChoice2.equalsIgnoreCase("a")) {
                        System.out.print("Enter Order ID: ");
                        String oId = scanner.nextLine();
                        System.out.print("Enter User ID: ");
                        String uId = scanner.nextLine();
                        System.out.print("Enter Restaurant ID: ");
                        String rId = scanner.nextLine();
                        System.out.print("Enter Food Details: ");
                        String details = scanner.nextLine();
                        System.out.print("Enter Price: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        // Validation checkpoint using Member 1's validation maps
                        if (member1.getUserProfile(uId) == null) {
                            System.out.println("Error: User ID does not exist in registrations.");
                        } else if (member1.getRestaurant(rId) == null) {
                            System.out.println("Error: Restaurant ID does not exist in registrations.");
                        } else {
                            member2.placeOrder(new Order(oId, uId, rId, details, price));
                        }

                    } else if (subChoice2.equalsIgnoreCase("b")) {
                        member2.processNextOrder();

                    } else if (subChoice2.equalsIgnoreCase("c")) {
                        member2.undoLastOrder();

                    } else if (subChoice2.equalsIgnoreCase("d")) {
                        member2.displayOrderFlow();
                    }
                    break;

                case 3:
                    System.out.println("\n[3] Assigning nearest delivery rider...");
                    System.out.println("a. Add Rider");
                    System.out.println("b. Assign Nearest Rider");
                    System.out.println("c. View Available Riders");
                    System.out.println("d. Linear Search Demo (for comparison)");
                    System.out.print("Select an option (a-d): ");
                    String subChoice3 = scanner.nextLine();

                    if (subChoice3.equalsIgnoreCase("a")) {
                        System.out.println("Enter Rider ID: ");
                        String riderId = scanner.nextLine().trim();
                        System.out.println("Enter Rider Name: ");
                        String riderName = scanner.nextLine().trim();
                        System.out.println("Enter Current Location Node: ");
                        String riderLocation = scanner.nextLine().trim();

                        if (!member4.hasLocation(riderLocation)) {
                            System.out.println("Error: Location '" + riderLocation + "' is not available in the route map.");
                            System.out.println("* Register restaurants using one of the available location nodes.");
                            member4.displayLocations(); 
                        } else{
                            riderDispatchSystem.addRider(new Rider(riderId, riderName, riderLocation, true));
                        }
                        
                    } else if (subChoice3.equalsIgnoreCase("b")) {
                        System.out.print("\n--- REGISTERED REASTAURANTS ---");
                        member1.displayAll();

                        System.out.print("\nEnter Restaurant ID to assign nearest rider: ");
                        String restaurantId = scanner.nextLine();

                        Restaurant restaurant = member1.getRestaurant(restaurantId);
                        if (restaurant == null) {
                            System.out.println("Error: Restaurant ID does not exist in registrations.");
                            break;
                        } else {
                            riderDispatchSystem.assignNearestRiderToRestaurant(restaurant.getLocation(), member4);
                        }
                         
                    } else if (subChoice3.equalsIgnoreCase("c")) {
                        member1.displayAll(); 
                        System.out.print("\nEnter Restaurant ID to check riders for: ");
                        String restId = scanner.nextLine().trim();
                        Restaurant restaurant = member1.getRestaurant(restId);
                        if (restaurant == null) {
                            System.out.println("Error: Restaurant ID does not exist in registrations.");
                            break;
                        } else {
                            riderDispatchSystem.displayAvailableRiders(restaurant.getLocation(), member4);
                        }
                    } else if (subChoice3.equalsIgnoreCase("d")) {
                        System.out.print("\n--- REGISTERED REASTAURANTS ---");
                        member1.displayAll();

                        System.out.print("\nEnter Restaurant ID to assign nearest rider: ");
                        String restaurantId = scanner.nextLine();

                        Restaurant restaurant = member1.getRestaurant(restaurantId);
                        if (restaurant == null) {
                            System.out.println("Error: Restaurant ID does not exist in registrations.");
                            break;
                        } else {
                            riderDispatchSystem.linearSearchNearest(restaurant.getLocation(), member4);
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n[4] Calculating shortest delivery pathway...");
                    System.out.println("a. View Available Location Nodes");
                    System.out.println("b. Find Shortest Route");
                    System.out.print("Select an option (a-b): ");
                    String subChoice4 = scanner.nextLine();

                    if (subChoice4.equalsIgnoreCase("a")) {
                        member4.displayLocations();

                    } else if (subChoice4.equalsIgnoreCase("b")) {
                        System.out.print("Enter Restaurant ID: ");
                        String restaurantId = scanner.nextLine();
                        Restaurant restaurant = member1.getRestaurant(restaurantId);

                        if (restaurant == null) {
                            System.out.println("Error: Restaurant ID does not exist in registrations.");
                            break;
                        }

                        String startLocation = restaurant.getLocation();
                        if (!member4.hasLocation(startLocation)) {
                            System.out.println("Error: Restaurant location '" + startLocation + "' is not available in the route map.");
                            System.out.println("Tip: Register restaurants using one of the available location nodes.");
                            member4.displayLocations();
                            break;
                        }

                        System.out.print("Enter Customer Location Node: ");
                        String destination = scanner.nextLine();
                        member4.displayShortestRoute(startLocation, destination);

                    } else {
                        System.out.println("Invalid route optimization option.");
                    }
                    break;

                case 5:
                    runFoodSearchMenu(scanner, foodSearchTree);
                    break;

                case 6:
                    System.out.println("Exiting System... See you next time!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid input. Please choose options 1 through 6.");
            }
        }
    }

    private static void runFoodSearchMenu(Scanner scanner, FoodAVLTree foodTree) {
        while (true) {
            System.out.println("\n[5] Food Search & Recommendation");
            System.out.println("1. Display all foods");
            System.out.println("2. Search food by name");
            System.out.println("3. Recommend by category");
            System.out.println("4. Recommend by price range");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice (1-5): ");

            String foodChoice = scanner.nextLine();

            if (foodChoice.equals("1")) {
                System.out.println("\nAll foods in sorted order:");
                foodTree.displaySortedFoods();

            } else if (foodChoice.equals("2")) {
                System.out.print("Enter food name: ");
                String foodName = scanner.nextLine();
                FoodItem foundFood = foodTree.searchByName(foodName);

                if (foundFood == null) {
                    System.out.println("Food item not found.");
                } else {
                    System.out.println("Search result:");
                    System.out.println(foundFood);
                }

            } else if (foodChoice.equals("3")) {
                System.out.print("Enter category: ");
                String category = scanner.nextLine();
                System.out.println("Recommended foods in " + category + " category:");
                foodTree.recommendByCategory(category);

            } else if (foodChoice.equals("4")) {
                double minPrice = readDouble(scanner, "Enter minimum price: RM");
                double maxPrice = readDouble(scanner, "Enter maximum price: RM");

                if (minPrice > maxPrice) {
                    System.out.println("Minimum price cannot be greater than maximum price.");
                } else {
                    System.out.println("Recommended foods from RM" + minPrice + " to RM" + maxPrice + ":");
                    foodTree.recommendByPriceRange(minPrice, maxPrice);
                }

            } else if (foodChoice.equals("5")) {
                return;

            } else {
                System.out.println("Invalid food search option.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a number.");
            }
        }
    }
}
