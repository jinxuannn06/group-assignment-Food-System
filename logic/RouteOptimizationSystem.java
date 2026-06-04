package logic;

import models.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class RouteOptimizationSystem {
    private HashMap<String, Location> locations;
    private HashMap<String, List<Edge>> graph;

    public RouteOptimizationSystem() {
        this.locations = new HashMap<>();
        this.graph = new HashMap<>();
    }

    public void addLocation(Location location) {
        if (location == null || location.getLocationId() == null || location.getLocationId().isBlank()) {
            System.out.println("Error: Invalid location details provided.");
            return;
        }

        locations.putIfAbsent(location.getLocationId(), location);
        graph.putIfAbsent(location.getLocationId(), new ArrayList<>());
    }

    public void addRoad(String from, String to, double distance) {
        if (!locations.containsKey(from) || !locations.containsKey(to)) {
            System.out.println("Error: Both locations must exist before adding a road.");
            return;
        }

        if (distance <= 0) {
            System.out.println("Error: Road distance must be greater than 0.");
            return;
        }

        graph.get(from).add(new Edge(to, distance));
        graph.get(to).add(new Edge(from, distance));
    }

    public RouteResult findShortestRoute(String start, String destination) {
        if (!locations.containsKey(start) || !locations.containsKey(destination)) {
            return null;
        }

        HashMap<String, Double> distances = new HashMap<>();
        HashMap<String, String> previousLocations = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>();

        for (String locationId : locations.keySet()) {
            distances.put(locationId, Double.MAX_VALUE);
        }

        distances.put(start, 0.0);
        priorityQueue.add(new NodeDistance(start, 0.0));

        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();

            if (visited.contains(current.locationId)) {
                continue;
            }

            visited.add(current.locationId);

            if (current.locationId.equals(destination)) {
                break;
            }

            for (Edge edge : graph.get(current.locationId)) {
                if (visited.contains(edge.destination)) {
                    continue;
                }

                double newDistance = distances.get(current.locationId) + edge.distance;
                if (newDistance < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDistance);
                    previousLocations.put(edge.destination, current.locationId);
                    priorityQueue.add(new NodeDistance(edge.destination, newDistance));
                }
            }
        }

        double shortestDistance = distances.get(destination);
        if (shortestDistance == Double.MAX_VALUE) {
            return new RouteResult(Collections.emptyList(), shortestDistance);
        }

        List<String> path = buildPath(start, destination, previousLocations);
        return new RouteResult(path, shortestDistance);
    }

    public void displayShortestRoute(String start, String destination) {
        if (!locations.containsKey(start)) {
            System.out.println("Error: Start location '" + start + "' does not exist in the map.");
            return;
        }

        if (!locations.containsKey(destination)) {
            System.out.println("Error: Destination location '" + destination + "' does not exist in the map.");
            return;
        }

        RouteResult result = findShortestRoute(start, destination);
        if (result == null || !result.hasRoute()) {
            System.out.println("No route found from " + start + " to " + destination + ".");
            return;
        }

        System.out.println("\n--- SHORTEST DELIVERY ROUTE ---");
        System.out.println("From: " + locations.get(start).getLocationName());
        System.out.println("To: " + locations.get(destination).getLocationName());
        System.out.println("Path: " + String.join(" -> ", result.getPath()));
        System.out.printf("Total Distance: %.2f km%n", result.getTotalDistance());
    }

    public void displayLocations() {
        System.out.println("\n--- AVAILABLE LOCATION NODES ---");
        if (locations.isEmpty()) {
            System.out.println("No locations available.");
            return;
        }

        for (Location location : locations.values()) {
            System.out.println(location);
        }
    }

    public boolean hasLocation(String locationId) {
        return locations.containsKey(locationId);
    }

    private List<String> buildPath(String start, String destination, Map<String, String> previousLocations) {
        List<String> path = new ArrayList<>();
        String current = destination;

        while (current != null) {
            path.add(current);

            if (current.equals(start)) {
                break;
            }

            current = previousLocations.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    private static class Edge {
        private String destination;
        private double distance;

        public Edge(String destination, double distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }

    private static class NodeDistance implements Comparable<NodeDistance> {
        private String locationId;
        private double distance;

        public NodeDistance(String locationId, double distance) {
            this.locationId = locationId;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    public static class RouteResult {
        private List<String> path;
        private double totalDistance;

        public RouteResult(List<String> path, double totalDistance) {
            this.path = path;
            this.totalDistance = totalDistance;
        }

        public List<String> getPath() {
            return path;
        }

        public double getTotalDistance() {
            return totalDistance;
        }

        public boolean hasRoute() {
            return path != null && !path.isEmpty() && totalDistance != Double.MAX_VALUE;
        }
    }
}
