//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import student.TestableRandom;
//
//
//
///**
// * Implements the Flyweight pattern for empty nodes 
// * in a QuadTree structure.
// * Uses a singleton instance to represent all empty nodes.
// * 
// * @author Brayden Gardner, Ryan Kluttz
// * @version 1.0
// * @since 2024-02-25
// */
//@SuppressWarnings("unused")
//public class EmptyNode implements QuadNode {
//    
//    private int x1, y1, x2, y2; // Spatial bounds
//
//    
//    @Override
//    public int[] getBounds() {
//        return new int[]{x1, y1, x2, y2};
//    }
//    private static EmptyNode flyweight = null;
//
//    private EmptyNode() {
//        // Private constructor to prevent outside instantiation
//    }
//
//    public static EmptyNode getInstance() {
//        if (flyweight == null) {
//            flyweight = new EmptyNode();
//        }
//        return flyweight;
//    }
//
//    @Override
//    public QuadNode insert(String name, int x, int y, int x1, int x2, int y1, int y2) {
//        // Transform into a LeafNode and insert the point
//        LeafNode newLeaf = new LeafNode(x1, x2, y1, y2);
//        return newLeaf.insert(name, x, y, x1, x2, y1, y2);
//    }
//
//    
//    
//    @Override
//    public QuadNode remove(String name, int x1, int x2, int y1, int y2) {
//        //TODO: Implement:
//        return this;
//    }
//    
//    @Override
//    public QuadNode remove(int x, int y, int x1, int x2, int y1, int y2) {
//        
//        return this;
//    }
//    
//    @Override 
//    public QuadNode search(String name) {
//        //Always return null for empty nodes
//        
//        return null;
//    }
//
//
//    @Override
//    public List<Point> regionsearch(int x, int y, int width, int height, int x1, int x2, int y1, int y2) {
//        // No points in an empty node
//        
//        return Collections.emptyList();
//    }
//
//    @Override
//    public boolean isLeaf() {
//        // Technically not a leaf but an empty node
//        return false;
//    }
//
//    @Override
//    public List<Point> duplicates() {
//        // No duplicates in an empty node
//        return null;
//    }
//    
//    @Override
//    public void dump(int level) {
//        QuadTree.incrementNodeCount(); // Increment for the empty node.
//        printWithIndentation("Node at " + x1 + ", " + y1 + ", " + (x2 - x1) + ": Empty", level);
//    }
//
//    private void printWithIndentation(String text, int level) {
//        for (int i = 0; i < level; i++) {
//            System.out.print("     ");
//        }
//        System.out.println(text);
//    }
//
//}
//

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;

/**
 * Implements the Flyweight pattern for empty nodes
 * in a QuadTree structure.
 * Uses a singleton instance to represent all empty nodes.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-25
 */
@SuppressWarnings("unused")
public class EmptyNode implements QuadNode {




    private static EmptyNode flyweight = null;

    private EmptyNode() {
        // Private constructor to prevent outside instantiation
    }


    public static EmptyNode getInstance() {
        if (flyweight == null) {
            flyweight = new EmptyNode();
        }
        return flyweight;
    }


    @Override
    public QuadNode insert(String name, int x, int y, ArrayList<Integer> param) {
        // Create a new LeafNode covering the same region and insert the point
        List<Point> points = new ArrayList<>();
        LeafNode newLeaf = new LeafNode(param.get(0), param.get(1), param.get(2), points);
        return newLeaf.insert(name, x, y, param);
    }



    @Override
    public QuadNode remove(String name, int size) {
        // TODO: Implement:
        return this;
    }


    @Override
    public QuadNode remove(int x, int y, int size) {
        System.out.println("Point rejected: (" + x + ", " + y + ")");
        return this;
    }

    @Override
    public List<Point> regionsearch(int x, int y, int width, int height, int size) {
        // Empty node has no points, return an empty list
        return Collections.emptyList();
    }


    @Override
    public boolean isLeaf() {
        // Technically not a leaf but an empty node
        return false;
    }


    private void printWithIndentation(String text, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(text);
    }




    @Override
    public int dump(int level, int x, int y, int size) {
        // Indentation based on the level
        String indentation = "  ".repeat(level);
        System.out.println(indentation + "Node at " + x + ", " + y + ", " + size
            + ": Empty");
        
    

        // Returns 1 since it's counting the current node
        return 1;
    }

}
