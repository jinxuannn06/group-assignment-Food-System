package foodsearch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FoodSearchDemo {
    public static void main(String[] args) {
        FoodAVLTree foodTree = new FoodAVLTree();

        List<FoodItem> loadedFoods = loadFoodsFromCsv(foodTree, Path.of("data", "foods.csv"));
        if (loadedFoods.isEmpty()) {
            System.out.println("No food data available for demo.");
            return;
        }

        System.out.println("All foods in sorted order:");
        foodTree.displaySortedFoods();

        FoodItem searchTarget = loadedFoods.get(0);
        System.out.println();
        System.out.println("Search result for " + searchTarget.getName() + ":");
        FoodItem searchResult = foodTree.searchByName(searchTarget.getName());
        System.out.println(searchResult);

        String category = searchTarget.getCategory();
        System.out.println();
        System.out.println("Recommended foods in " + category + " category:");
        foodTree.recommendByCategory(category);

        double minPrice = getMinimumPrice(loadedFoods);
        double maxPrice = getMaximumPrice(loadedFoods);
        System.out.println();
        System.out.printf("Recommended foods from RM%.2f to RM%.2f:%n", minPrice, maxPrice);
        foodTree.recommendByPriceRange(minPrice, maxPrice);
    }

    private static List<FoodItem> loadFoodsFromCsv(FoodAVLTree foodTree, Path path) {
        List<FoodItem> loadedFoods = new ArrayList<>();

        if (!Files.exists(path)) {
            System.out.println("Food data file not found: " + path);
            return loadedFoods;
        }

        int loaded = 0;

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            boolean headerSkipped = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                List<String> columns = parseCsvLine(line);
                if (columns.size() != 5) {
                    warn(path, i + 1, "expected 5 columns but found " + columns.size() + ".");
                    continue;
                }

                String foodId = columns.get(0);
                String name = columns.get(1);
                String category = columns.get(2);
                String priceText = columns.get(3);
                String restaurantId = columns.get(4);

                if (isBlank(foodId) || isBlank(name) || isBlank(category) || isBlank(restaurantId)) {
                    warn(path, i + 1, "foodId, name, category, and restaurantId are required.");
                    continue;
                }

                try {
                    double price = Double.parseDouble(priceText);
                    if (price <= 0) {
                        warn(path, i + 1, "price must be greater than 0.");
                        continue;
                    }

                    FoodItem item = new FoodItem(foodId, name, category, price, restaurantId);
                    foodTree.insert(item);
                    loadedFoods.add(item);
                    loaded++;
                } catch (NumberFormatException e) {
                    warn(path, i + 1, "price must be a valid number.");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + path + ": " + e.getMessage());
            return loadedFoods;
        }

        System.out.println("Loaded " + loaded + " foods from " + path + ".");
        System.out.println();
        return loadedFoods;
    }

    private static double getMinimumPrice(List<FoodItem> foods) {
        double minimumPrice = foods.get(0).getPrice();

        for (FoodItem food : foods) {
            if (food.getPrice() < minimumPrice) {
                minimumPrice = food.getPrice();
            }
        }

        return minimumPrice;
    }

    private static double getMaximumPrice(List<FoodItem> foods) {
        double maximumPrice = foods.get(0).getPrice();

        for (FoodItem food : foods) {
            if (food.getPrice() > maximumPrice) {
                maximumPrice = food.getPrice();
            }
        }

        return maximumPrice;
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

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static void warn(Path path, int lineNumber, String message) {
        System.out.println("Warning: " + path + " line " + lineNumber + ": " + message);
    }
}
