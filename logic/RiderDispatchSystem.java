package logic;

import models.Rider;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
        Rider assigned = riderHeap.poll();
        assigned.setAvailable(false);
        System.out.println("\nNearest rider assigned is: " +  assigned);
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
            System.out.println(i + ". " + rider);
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
        // Loop check every rider
        for  (Rider rider : all) {
            if (rider.getDistanceToRestaurant() < nearest.getDistanceToRestaurant()) {
                nearest = rider;
            }
        }
        System.out.println("\nLinear Search for " + all.size() + " riders to find nearest.");
        System.out.println("Nearest rider is: " + nearest);
        return nearest;
    }
}
