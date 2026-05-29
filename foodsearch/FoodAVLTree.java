package foodsearch;

public class FoodAVLTree {
    private FoodNode root;

    public void insert(FoodItem item) {
        if (item == null || item.getName() == null || item.getName().trim().isEmpty()) {
            return;
        }

        root = insert(root, item);
    }

    private FoodNode insert(FoodNode node, FoodItem item) {
        if (node == null) {
            return new FoodNode(item);
        }

        int comparison = compareNames(item.getName(), node.item.getName());

        if (comparison < 0) {
            node.left = insert(node.left, item);
        } else if (comparison > 0) {
            node.right = insert(node.right, item);
        } else {
            node.item = item;
            return node;
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balance = getBalance(node);

        if (balance > 1 && compareNames(item.getName(), node.left.item.getName()) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && compareNames(item.getName(), node.right.item.getName()) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && compareNames(item.getName(), node.left.item.getName()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && compareNames(item.getName(), node.right.item.getName()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public FoodItem searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        return searchByName(root, name);
    }

    private FoodItem searchByName(FoodNode node, String name) {
        if (node == null) {
            return null;
        }

        int comparison = compareNames(name, node.item.getName());

        if (comparison == 0) {
            return node.item;
        } else if (comparison < 0) {
            return searchByName(node.left, name);
        } else {
            return searchByName(node.right, name);
        }
    }

    public void displaySortedFoods() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(FoodNode node) {
        if (node == null) {
            return;
        }

        inOrderTraversal(node.left);
        System.out.println(node.item);
        inOrderTraversal(node.right);
    }

    public void recommendByCategory(String category) {
        recommendByCategory(root, category);
    }

    private void recommendByCategory(FoodNode node, String category) {
        if (node == null || category == null) {
            return;
        }

        recommendByCategory(node.left, category);

        if (node.item.getCategory() != null
                && node.item.getCategory().trim().equalsIgnoreCase(category.trim())) {
            System.out.println(node.item);
        }

        recommendByCategory(node.right, category);
    }

    public void recommendByPriceRange(double minPrice, double maxPrice) {
        recommendByPriceRange(root, minPrice, maxPrice);
    }

    private void recommendByPriceRange(FoodNode node, double minPrice, double maxPrice) {
        if (node == null) {
            return;
        }

        recommendByPriceRange(node.left, minPrice, maxPrice);

        double price = node.item.getPrice();
        if (price >= minPrice && price <= maxPrice) {
            System.out.println(node.item);
        }

        recommendByPriceRange(node.right, minPrice, maxPrice);
    }

    private int getHeight(FoodNode node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    private int getBalance(FoodNode node) {
        if (node == null) {
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }

    private FoodNode rightRotate(FoodNode y) {
        FoodNode x = y.left;
        FoodNode temp = x.right;

        x.right = y;
        y.left = temp;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    private FoodNode leftRotate(FoodNode x) {
        FoodNode y = x.right;
        FoodNode temp = y.left;

        y.left = x;
        x.right = temp;

        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));

        return y;
    }

    private int compareNames(String firstName, String secondName) {
        return firstName.trim().compareToIgnoreCase(secondName.trim());
    }
}
