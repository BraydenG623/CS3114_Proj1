import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;


/**
 * This class provides the abstract interface for the QuadNode 
 * to be implmented by the subclass (Internal, Leaf, and Emtpy node)
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * 
 * @version 2024-25-02
 */
@SuppressWarnings("unused")
public interface QuadNode {
    // Insert a point into the quadtree, might adjust to only spatial coordinates if managed externally
    QuadNode insert(Point point);

    // Remove a point from the quadtree, might adjust for spatial identification
    QuadNode remove(Point point);

    // Perform a region search, returning a list of points within a given region
    List<Point> regionSearch(int x, int y, int width, int height);

    // Check if the node is a leaf
    boolean isLeaf();

    // Report duplicates, returning a list of duplicate points or modifying method signature as needed
    List<Point> duplicates();
}
