import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;

/**
 * Represents the QuadTree structure for organizing points in a two-dimensional space.
 * Supports operations such as insert, remove, regionSearch, and duplicates.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-24
 */
@SuppressWarnings("unused")
public class QuadTree {
    private QuadNode root;
    // The dimension of the world space
    private static final int WORLD_SIZE = 1024; // Assuming a square world

    public QuadTree() {
        // Initially, the tree is empty, so we create a flyweight for the root.
        this.root = EmptyNode.getInstance();
    }

    public void insert(String name, int x, int y) {
        // The entire world space is represented in the root.
        // The size is passed to handle splits accurately.
        this.root = root.insert(name, x, y, WORLD_SIZE);
    }

    public void remove(String name) {
        // Size is passed to properly find and remove the point by name.
        this.root = root.remove(name, WORLD_SIZE);
    }

    public void remove(int x, int y) {
        // Size is passed to accurately locate and remove the point by coordinates.
        this.root = root.remove(x, y, WORLD_SIZE);
    }
    
    public void dump() {
        System.out.println("QuadTree dump:");
        int nodeCount = root.dump(0); // Dump the tree starting from the root at level 0
        System.out.println(nodeCount + " quadtree nodes printed");
    }

    // Other QuadTree methods (like regionSearch, search, duplicates) can be added here following a similar pattern.
}
