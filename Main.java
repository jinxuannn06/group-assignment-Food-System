import java.util.Scanner;


import logic.ManagementSystem;
import models.User;
import models.Restaurant;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize all members' logic classes
        ManagementSystem member1 = new ManagementSystem();


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
                    System.out.println("\n[2] Order Processing System active.");
                    //Member 2
                    break;

                case 3:
                    System.out.println("\n[3] Assigning nearest delivery rider...");
                    //Member 3
                    break;

                case 4:
                    System.out.println("\n[4] Calculating shortest delivery pathway...");
                    //Member 4
                    break;

                case 5:
                    System.out.println("\n[5] Fast Food Search System active.");
                    //Member 5
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
}
