import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;


/**
 * Represents the QuadTree structure for organizing points 
 * in a two-dimensional space.
 * Supports operations such as insert, remove, regionSearch, 
 * and duplicates.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-24
 */
@SuppressWarnings("unused")
public class QuadTree {
    private QuadNode root;
    // World size defined as constants
    //Top left corner : x1, y1 (0,0)
    private static final int X1 = 0, Y1 = 0;
    
    //
    private static final int X2 = 1024, Y2 = 1024; 
    
    
    private static int nodeCount = 0; // Track the count of nodes printed


    public QuadTree() {
        // Initially, the tree is empty.
        this.root = EmptyNode.getInstance();
    }

    public void insert(String name, int x, int y) {
        // On the first insert, replace the root with a leafnode with world bounds, if necessary.
        if (root instanceof EmptyNode) {
            root = new LeafNode(X1, Y1, X2, Y2); // Adjust this line to match your constructor or factory pattern
        }
        // Now, insert the point into the tree, passing down the world bounds
        root = root.insert(name, x, y, X1, Y1, X2, Y2);
    }

    public void remove(String name) {
        if (!(root instanceof EmptyNode)) {
            root = root.remove(name, X1, Y1, X2, Y2);
        }
    }

    public void remove(int x, int y) {
        if (!(root instanceof EmptyNode)) {
            root = root.remove(x, y, X1, Y1, X2, Y2);
        }
    }
    
    public static void incrementNodeCount() {
        nodeCount++;
    }
    
    public void dump() {
        System.out.println("QuadTree dump:");
        nodeCount = 0; // Reset count before dumping
        if (root != null) {
            root.dump(0);
        }
        System.out.println(nodeCount + " quadtree nodes printed");
    }

}

