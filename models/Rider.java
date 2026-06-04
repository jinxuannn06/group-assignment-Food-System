package models;

public class Rider implements Comparable<Rider> {
    private String riderId;
    private String ridername;
    private String currentLocation;
    private double distanceToRestaurant; 
    private boolean isAvailable;

    public Rider(String riderId, String ridername, String currentLocation, boolean isAvailable) {
        this.riderId = riderId;
        this.ridername = ridername;
        this.currentLocation = currentLocation;
        this.distanceToRestaurant = 0.0;
        this.isAvailable = isAvailable;
    }

    // Min-Heap
    // Rider with smallest distance always has highest priority
    @Override
    public int compareTo(Rider other) {
        return Double.compare(this.distanceToRestaurant, other.distanceToRestaurant);
    }

    public String getRiderId() {
        return riderId;
    }
    public String getRiderName() {
        return ridername;
    }
    public String getCurrentLocation() {
        return currentLocation;
    }
    public double getDistanceToRestaurant() {
        return distanceToRestaurant;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }
    public void setRidername(String ridername) {
        this.ridername = ridername;
    }
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public void setDistanceToRestaurant(double distanceToRestaurant) {
        this.distanceToRestaurant = distanceToRestaurant;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "Rider [ID=" + riderId + ", Name=" + ridername + ", Location=" + currentLocation + ", Distance=" + String.format("%.1f", distanceToRestaurant) + "km" +
                ", Status=" + (isAvailable ? "Available" : "Not Available") + "]";
    }
}
