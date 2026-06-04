package logic;

import foodsearch.FoodAVLTree;
import foodsearch.FoodItem;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import models.Location;
import models.Restaurant;
import models.Rider;
import models.User;

public final class CsvDataLoader {
    private static final Path DATA_DIR = Path.of("data");

    private CsvDataLoader() {
    }

    public static void loadDemoData(
            ManagementSystem managementSystem,
            RiderDispatchSystem riderDispatchSystem,
            RouteOptimizationSystem routeOptimizationSystem,
            FoodAVLTree foodTree) {

        System.out.println("\nLoading shared CSV demo data...");

        Set<String> locationIds = loadLocations(routeOptimizationSystem, DATA_DIR.resolve("locations.csv"));
        loadRoads(routeOptimizationSystem, DATA_DIR.resolve("roads.csv"), locationIds);
        loadUsers(managementSystem, DATA_DIR.resolve("users.csv"));
        Set<String> restaurantIds = loadRestaurants(
                managementSystem,
                DATA_DIR.resolve("restaurants.csv"),
                locationIds);
        loadFoods(foodTree, DATA_DIR.resolve("foods.csv"), restaurantIds);
        loadRiders(riderDispatchSystem, DATA_DIR.resolve("riders.csv"));

        System.out.println("CSV demo data loading complete.");
    }

    private static Set<String> loadLocations(RouteOptimizationSystem routeOptimizationSystem, Path path) {
        Set<String> locationIds = new HashSet<>();
        int loaded = 0;

        for (CsvRow row : readRows(path, 2)) {
            String locationId = row.columns.get(0);
            String locationName = row.columns.get(1);

            if (isBlank(locationId) || isBlank(locationName)) {
                warn(path, row.lineNumber, "locationId and locationName are required.");
                continue;
            }

            if (!locationIds.add(locationId)) {
                warn(path, row.lineNumber, "duplicate locationId '" + locationId + "' skipped.");
                continue;
            }

            routeOptimizationSystem.addLocation(new Location(locationId, locationName));
            loaded++;
        }

        System.out.println("Loaded " + loaded + " locations from " + path + ".");
        return locationIds;
    }

    private static void loadRoads(RouteOptimizationSystem routeOptimizationSystem, Path path, Set<String> locationIds) {
        int loaded = 0;

        for (CsvRow row : readRows(path, 3)) {
            String from = row.columns.get(0);
            String to = row.columns.get(1);
            String distanceText = row.columns.get(2);

            if (!locationIds.contains(from) || !locationIds.contains(to)) {
                warn(path, row.lineNumber, "road references an unknown location.");
                continue;
            }

            Double distance = parsePositiveDouble(path, row.lineNumber, "distance", distanceText);
            if (distance == null) {
                continue;
            }

            routeOptimizationSystem.addRoad(from, to, distance);
            loaded++;
        }

        System.out.println("Loaded " + loaded + " roads from " + path + ".");
    }

    private static void loadUsers(ManagementSystem managementSystem, Path path) {
        int loaded = 0;

        for (CsvRow row : readRows(path, 3)) {
            String userId = row.columns.get(0);
            String name = row.columns.get(1);
            String contactInfo = row.columns.get(2);

            if (isBlank(userId) || isBlank(name) || isBlank(contactInfo)) {
                warn(path, row.lineNumber, "userId, name, and contactInfo are required.");
                continue;
            }

            if (managementSystem.getUserProfile(userId) != null) {
                warn(path, row.lineNumber, "duplicate userId '" + userId + "' skipped.");
                continue;
            }

            managementSystem.registerUser(new User(userId, name, contactInfo));
            loaded++;
        }

        System.out.println("Loaded " + loaded + " users from " + path + ".");
    }

    private static Set<String> loadRestaurants(
            ManagementSystem managementSystem,
            Path path,
            Set<String> locationIds) {

        Set<String> restaurantIds = new HashSet<>();
        int loaded = 0;

        for (CsvRow row : readRows(path, 3)) {
            String restaurantId = row.columns.get(0);
            String restaurantName = row.columns.get(1);
            String location = row.columns.get(2);

            if (isBlank(restaurantId) || isBlank(restaurantName) || isBlank(location)) {
                warn(path, row.lineNumber, "restaurantId, restaurantName, and location are required.");
                continue;
            }

            if (!locationIds.contains(location)) {
                warn(path, row.lineNumber, "restaurant location '" + location + "' does not exist.");
                continue;
            }

            if (managementSystem.getRestaurant(restaurantId) != null || !restaurantIds.add(restaurantId)) {
                warn(path, row.lineNumber, "duplicate restaurantId '" + restaurantId + "' skipped.");
                continue;
            }

            managementSystem.registerRestaurant(new Restaurant(restaurantId, restaurantName, location));
            loaded++;
        }

        System.out.println("Loaded " + loaded + " restaurants from " + path + ".");
        return restaurantIds;
    }

    private static void loadFoods(FoodAVLTree foodTree, Path path, Set<String> restaurantIds) {
        Set<String> foodIds = new HashSet<>();
        int loaded = 0;

        for (CsvRow row : readRows(path, 5)) {
            String foodId = row.columns.get(0);
            String name = row.columns.get(1);
            String category = row.columns.get(2);
            String priceText = row.columns.get(3);
            String restaurantId = row.columns.get(4);

            if (isBlank(foodId) || isBlank(name) || isBlank(category) || isBlank(restaurantId)) {
                warn(path, row.lineNumber, "foodId, name, category, and restaurantId are required.");
                continue;
            }

            if (!foodIds.add(foodId)) {
                warn(path, row.lineNumber, "duplicate foodId '" + foodId + "' skipped.");
                continue;
            }

            if (!restaurantIds.contains(restaurantId)) {
                warn(path, row.lineNumber, "food references unknown restaurantId '" + restaurantId + "'.");
                continue;
            }

            Double price = parsePositiveDouble(path, row.lineNumber, "price", priceText);
            if (price == null) {
                continue;
            }

            foodTree.insert(new FoodItem(foodId, name, category, price, restaurantId));
            loaded++;
        }

        System.out.println("Loaded " + loaded + " foods from " + path + ".");
    }

    private static void loadRiders(RiderDispatchSystem riderDispatchSystem, Path path) {
        Set<String> riderIds = new HashSet<>();
        int loaded = 0;

        for (CsvRow row : readRows(path, 4)) {
            String riderId = row.columns.get(0);
            String riderName = row.columns.get(1);
            String currentLocation = row.columns.get(2);
            String isAvailableText = row.columns.get(3);

            if (isBlank(riderId) || isBlank(riderName) || isBlank(currentLocation)) {
                warn(path, row.lineNumber, "riderId, riderName, and currentLocation are required.");
                continue;
            }

            if (!riderIds.add(riderId)) {
                warn(path, row.lineNumber, "duplicate riderId '" + riderId + "' skipped.");
                continue;
            }

            Boolean isAvailable = parseBoolean(path, row.lineNumber, "isAvailable", isAvailableText);
            if (isAvailable == null) {
                continue;
            }

            riderDispatchSystem.addRider(new Rider(riderId, riderName, currentLocation, isAvailable));
            loaded++;
        }

        System.out.println("Loaded " + loaded + " riders from " + path + ".");
    }

    private static List<CsvRow> readRows(Path path, int expectedColumns) {
        List<CsvRow> rows = new ArrayList<>();

        if (!Files.exists(path)) {
            System.out.println("Warning: data file not found: " + path);
            return rows;
        }

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            boolean headerSkipped = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (isBlank(line)) {
                    continue;
                }

                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                List<String> columns = parseCsvLine(line);
                if (columns.size() != expectedColumns) {
                    warn(path, i + 1, "expected " + expectedColumns + " columns but found " + columns.size() + ".");
                    continue;
                }

                rows.add(new CsvRow(i + 1, columns));
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read " + path + ": " + e.getMessage());
        }

        return rows;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> columns = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char current = line.charAt(i);

            if (current == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    field.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (current == ',' && !inQuotes) {
                columns.add(field.toString().trim());
                field.setLength(0);
            } else {
                field.append(current);
            }
        }

        columns.add(field.toString().trim());
        return columns;
    }

    private static Double parsePositiveDouble(Path path, int lineNumber, String columnName, String value) {
        try {
            double parsed = Double.parseDouble(value);
            if (parsed <= 0) {
                warn(path, lineNumber, columnName + " must be greater than 0.");
                return null;
            }

            return parsed;
        } catch (NumberFormatException e) {
            warn(path, lineNumber, columnName + " must be a valid number.");
            return null;
        }
    }

    private static Boolean parseBoolean(Path path, int lineNumber, String columnName, String value) {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }

        if ("false".equalsIgnoreCase(value)) {
            return false;
        }

        warn(path, lineNumber, columnName + " must be true or false.");
        return null;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static void warn(Path path, int lineNumber, String message) {
        System.out.println("Warning: " + path + " line " + lineNumber + ": " + message);
    }

    private static class CsvRow {
        private int lineNumber;
        private List<String> columns;

        private CsvRow(int lineNumber, List<String> columns) {
            this.lineNumber = lineNumber;
            this.columns = columns;
        }
    }
}
