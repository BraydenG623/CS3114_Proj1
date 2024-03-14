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
    
    private int x1, y1, x2, y2; // Spatial bounds

    
    @Override
    public int[] getBounds() {
        return new int[]{x1, y1, x2, y2};
    }
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
    public QuadNode insert(String name, int x, int y, int x1, int x2, int y1, int y2) {
        // Transform into a LeafNode and insert the point
        LeafNode newLeaf = new LeafNode(x1, x2, y1, y2);
        return newLeaf.insert(name, x, y, x1, x2, y1, y2);
    }

    
    
    @Override
    public QuadNode remove(String name, int x1, int x2, int y1, int y2) {
        //TODO: Implement:
        return this;
    }
    
    @Override
    public QuadNode remove(int x, int y, int x1, int x2, int y1, int y2) {
        
        return this;
    }
    
    @Override 
    public QuadNode search(String name) {
        //Always return null for empty nodes
        
        return null;
    }


    @Override
    public List<Point> regionsearch(int x, int y, int width, int height, int x1, int x2, int y1, int y2) {
        // No points in an empty node
        
        return Collections.emptyList();
    }

    @Override
    public boolean isLeaf() {
        // Technically not a leaf but an empty node
        return false;
    }

    @Override
    public List<Point> duplicates() {
        // No duplicates in an empty node
        return null;
    }
    
    @Override
    public void dump(int level) {
        
    }
}