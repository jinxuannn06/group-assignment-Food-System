package foodsearch;

public class FoodSearchDemo {
    public static void main(String[] args) {
        FoodAVLTree foodTree = new FoodAVLTree();

        FoodItem nasiLemak = new FoodItem("F001", "Nasi Lemak", "Rice", 8.50, "R001");
        FoodItem chickenRice = new FoodItem("F002", "Chicken Rice", "Rice", 9.00, "R002");
        FoodItem burger = new FoodItem("F003", "Beef Burger", "Western", 12.90, "R003");
        FoodItem laksa = new FoodItem("F004", "Laksa", "Noodles", 10.50, "R001");
        FoodItem salad = new FoodItem("F005", "Caesar Salad", "Healthy", 11.00, "R004");

        foodTree.insert(nasiLemak);
        foodTree.insert(chickenRice);
        foodTree.insert(burger);
        foodTree.insert(laksa);
        foodTree.insert(salad);

        System.out.println("All foods in sorted order:");
        foodTree.displaySortedFoods();

        System.out.println();
        System.out.println("Search result for Nasi Lemak:");
        FoodItem searchResult = foodTree.searchByName("Nasi Lemak");
        System.out.println(searchResult);

        System.out.println();
        System.out.println("Recommended foods in Rice category:");
        foodTree.recommendByCategory("Rice");

        System.out.println();
        System.out.println("Recommended foods from RM8.00 to RM11.00:");
        foodTree.recommendByPriceRange(8.00, 11.00);
    }
}
