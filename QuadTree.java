//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import student.TestableRandom;
//
//
///**
// * Represents the QuadTree structure for organizing points 
// * in a two-dimensional space.
// * Supports operations such as insert, remove, regionSearch, 
// * and duplicates.
// * 
// * @author Brayden Gardner, Ryan Kluttz
// * @version 1.0
// * @since 2024-02-24
// */
//@SuppressWarnings("unused")
//public class QuadTree {
//    private QuadNode root;
//    // World size defined as constants
//    //Top left corner : x1, y1 (0,0)
//    private static final int X1 = 0, Y1 = 0;
//    
//    //
//    private static final int X2 = 1024, Y2 = 1024; 
//    
//    private static int nodeCount = 0; // Track the count of nodes printed
//
//
//    public QuadTree() {
//        // Initially, the tree is empty.
//        this.root = EmptyNode.getInstance();
//    }
//
//    public void insert(String name, int x, int y) {
//        // On the first insert, replace the root with a leafnode with world bounds, if necessary.
//        if (root instanceof EmptyNode) {
//            root = new LeafNode(X1, Y1, X2, Y2); // Adjust this line to match your constructor or factory pattern
//        }
//        // Now, insert the point into the tree, passing down the world bounds
//        root = root.insert(name, x, y, X1, Y1, X2, Y2);
//        System.out.println("Point inserted: (" + name + ", " + x + ", " + y + ")");
//    }
//
//    public void remove(String name) {
//        if (!(root instanceof EmptyNode)) {
//            root = root.remove(name, X1, Y1, X2, Y2);
//        }
//    }
//
//    public void remove(int x, int y) {
//        if (!(root instanceof EmptyNode)) {
//            root = root.remove(x, y, X1, Y1, X2, Y2);
//        }
//    }
//    
//    
//    
//    public static void incrementNodeCount() {
//        nodeCount++;
//    }
//    
//
//    public void dump() {
//        System.out.println("QuadTree dump:");
//        nodeCount = 0; // Reset count before dumping
//        if (root != null) {
//            root.dump(0);
//        }
//        System.out.println(nodeCount + " quadtree nodes printed");
//    }
//
//}

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private List<Point> pointList;
    // The dimension of the world space
    private static final int WORLD_SIZE = 1024; // Assuming a square world
    private static final int TOP_LEFT_X = 0;
    private static final int TOP_LEFT_Y = 0;
    
    public QuadTree() {
        // Initially, the tree is empty, so we create a flyweight for the root.
        this.root = EmptyNode.getInstance();
    }

    public void insert(String name, int x, int y) {
        // The entire world space is represented in the root.
        // The size is passed to handle splits accurately.
        if(x < 0 || y < 0 || x > 1024 || y > 1024) {
            System.out.println("Point rejected: (" + name + ", " + x + ", " + y + ")");
        }
        ArrayList<Integer> param = new ArrayList<>();
        param.add(TOP_LEFT_X);
        param.add(TOP_LEFT_Y);
        param.add(WORLD_SIZE);
        System.out.println("Point inserted: (" + name + ", " + x + ", " + y + ")");
        this.root = root.insert(name, x, y, param);
    }

    public void remove(String name) {
        // Size is passed to properly find and remove the point by name.
        this.root = root.remove(name, WORLD_SIZE);
        if(this.root == null) {
            System.out.println("Point not removed: " + name);
        }
    }

    public void remove(int x, int y) {
        // Size is passed to accurately locate and remove the point by coordinates.
        this.root = root.remove(x, y, WORLD_SIZE);
    }
    
    public void dump() {
        System.out.println("QuadTree dump:");
        int nodeCount = root.dump(0, TOP_LEFT_X, TOP_LEFT_Y, WORLD_SIZE); // Dump the tree starting from the root at level 0
        System.out.println(nodeCount + " quadtree nodes printed");
    }

    public void regionsearch(int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        System.out.println("Points intersecting region (" + x + ", " + y + ", " + width + ", " + height + ")");
        this.pointList = root.regionsearch(x, y, width, height, WORLD_SIZE);
    }
}
