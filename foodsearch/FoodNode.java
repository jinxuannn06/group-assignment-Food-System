package foodsearch;

class FoodNode {
    FoodItem item;
    FoodNode left;
    FoodNode right;
    int height;

    FoodNode(FoodItem item) {
        this.item = item;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
