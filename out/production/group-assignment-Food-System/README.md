# group-assignment-Food-System

## Food Search & Recommendation using AVL Tree

### Module Overview
This module manages food search and recommendation for the Smart Food Delivery & Order Management System.

It uses an AVL Tree, which is a self-balancing Binary Search Tree. Food items are stored in the tree and compared by food name. This allows food items to be inserted, searched, and displayed in sorted order efficiently.

### Package Structure
```text
foodsearch/
├── FoodItem.java
├── FoodNode.java
├── FoodAVLTree.java
└── FoodSearchDemo.java
```

### Class Descriptions
- `FoodItem.java`: Stores food details such as `foodId`, `name`, `category`, `price`, and `restaurantId`.
- `FoodNode.java`: Represents one node in the AVL Tree and stores `item`, `left`, `right`, and `height`.
- `FoodAVLTree.java`: Contains the AVL Tree logic for insert, search, traversal, recommendation, height update, balance factor, and rotations.
- `FoodSearchDemo.java`: Tests the module with sample food items.

### Main Features
- `insert(FoodItem item)`: Adds a food item into the AVL Tree.
- `searchByName(String name)`: Searches for a food item by name.
- `displaySortedFoods()`: Displays all food items in alphabetical order using in-order traversal.
- `recommendByCategory(String category)`: Displays food items that match a category.
- `recommendByPriceRange(double minPrice, double maxPrice)`: Displays food items within a price range.

### AVL Tree Explanation
Smaller food names are placed in the left subtree, while larger food names are placed in the right subtree. If a duplicate food name is inserted, the existing food item is updated.

After insertion, the node height is updated. The balance factor is calculated as:

```text
balance factor = height(left subtree) - height(right subtree)
```

If the tree becomes unbalanced, AVL rotations are performed to keep the tree balanced.

### AVL Rotations
- LL Case: Perform a right rotation.
- RR Case: Perform a left rotation.
- LR Case: Perform a left rotation, then a right rotation.
- RL Case: Perform a right rotation, then a left rotation.

### Logical Structure Diagram
```text
                 Nasi Lemak
                /          \
       Chicken Rice        Sushi
        /       \          /
    Burger   Fried Rice  Pizza
```

In-order traversal visits the left subtree, then the current node, then the right subtree. This displays food items in alphabetical order.

### Time Complexity
| Operation | Time Complexity |
| --- | --- |
| Insert food item | O(log n) |
| Search food by name | O(log n) |
| Display sorted foods | O(n) |
| Recommend by category | O(n) |
| Recommend by price range | O(n) |

Category and price recommendations are `O(n)` because the AVL Tree is ordered by food name, not by category or price.

### Compile and Run
Full project:
```bash
javac Main.java logic/*.java models/*.java foodsearch/*.java
java Main
```

Food Search module demo:
```bash
javac foodsearch/*.java
java foodsearch.FoodSearchDemo
```

### Integration Note
The latest `main` branch does not contain a shared `models.FoodItem` class, so this module keeps `foodsearch.FoodItem`. If the team later adds `models.FoodItem`, the module can be updated to import and use the shared model.

### Author
Food Search & System Lead: Manxi
