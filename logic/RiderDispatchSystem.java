package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import models.Rider;

public class RiderDispatchSystem {

    private PriorityQueue<Rider> riderHeap;

    public RiderDispatchSystem() {
        this.riderHeap = new PriorityQueue<>();
    }

    // Add rider into the heap
    public void addRider(Rider rider) {
        if (rider == null) {
            System.out.println("Invalid Rider");
            return;
        }
        if (!rider.isAvailable()) {
            System.out.println("Rider " + rider.getRiderName()+ " is not available.");
            return;
        }
        riderHeap.add(rider);
        System.out.println("Rider " + rider.getRiderName() + " has been added to the queue.");
    }

    // Pool, returns and removes the rider at top of the heap
    public Rider assignNearestRider() {
        if (riderHeap.isEmpty()) {
            System.out.println("No available riders now.");
            return null;
        }
        
        int totalRiders = riderHeap.size();
        int operations = 32 - Integer.numberOfLeadingZeros(totalRiders);

        Rider assigned = riderHeap.poll();
        assigned.setAvailable(false);
        System.out.println("\nPriority Queue (Heap) Search");
        System.out.println("\nNearest rider assigned is: " +  assigned);
        System.out.println("Total operations made: " + operations);
        System.out.println("Time complexity: O(log n)");
        return assigned;
    }

    // Display
    public void displayAvailableRiders() {
        if (riderHeap.isEmpty()) {
            System.out.println("No available riders now.");
            return;
        }
        // Copy a list first, then sort
        List<Rider> sorted = new ArrayList<>(riderHeap);
        sorted.sort(null); // Call the compareTo, to sort by distance
        System.out.println("\nAvailable riders are:");
        int i = 1;
        for (Rider rider : sorted) {
            System.out.println(i++ + ". " + rider);
        }
    }

    // Linear search
    // Perforamnce comparison, search rider one-by-one
    public Rider linearSearchNearest() {
        if (riderHeap.isEmpty()) {
            System.out.println("No available riders now.");
            return null;
        }

        // Create a copy
        List<Rider> all = new ArrayList<>(riderHeap);
        // Assume index 0 as nearest initially
        Rider nearest = all.get(0);
        // Counter for comparisons 
        int comparisons = 0;
        
        // Loop check every rider
        for  (Rider rider : all) {
            comparisons++; // Increment comparison count
            if (rider.getDistanceToRestaurant() < nearest.getDistanceToRestaurant()) {
                nearest = rider;
            }
        }

        nearest.setAvailable(false);
        riderHeap.remove(nearest); // Remove the assigned rider from the heap   

        System.out.println("\nLinear Search");
        System.out.println("\nLinear Search for " + all.size() + " riders to find nearest.");
        System.out.println("Nearest rider is: " + nearest);
        System.out.println("Total comparisons made: " + comparisons);
        System.out.println("Time complexity: O(n)");
        return nearest;
    }
}
