package models;

public class Rider implements Comparable<Rider> {
    private String riderId;
    private String ridername;
    private double distanceToRestaurant; // Key for priority
    private boolean isAvailable;

    public Rider(String riderId, String ridername, double distanceToRestaurant, boolean isAvailable) {
        this.riderId = riderId;
        this.ridername = ridername;
        this.distanceToRestaurant = distanceToRestaurant;
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
    public void setDistanceToRestaurant(double distanceToRestaurant) {
        this.distanceToRestaurant = distanceToRestaurant;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "Rider [ID=" + riderId + ", Name=" + ridername + ", Distance=" + String.format("%.1f", distanceToRestaurant) + "km" +
                ", Status=" + isAvailable + "]";
    }
}
