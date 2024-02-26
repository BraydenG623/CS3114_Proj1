import java.lang.reflect.Array;
import java.util.ArrayList;
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
    public QuadNode insert(Point point) {
        // Transform into a LeafNode and insert the point
        LeafNode newLeaf = new LeafNode();
        return newLeaf.insert(point);
    }

    @Override
    public QuadNode remove(Point point) {
        // Nothing to remove in an empty node
        return this;
    }

    @Override
    public List<Point> regionSearch(int x, int y, int width, int height) {
        // No points in an empty node
        return null;
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
}