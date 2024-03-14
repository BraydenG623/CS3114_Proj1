import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;

/**
 * This class provides the abstract interface for the QuadNode
 * to be implmented by the subclass (Internal, Leaf, and Empty node)
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * 
 * @version 2024-25-02
 */
@SuppressWarnings("unused")
public interface QuadNode {
    // Insert a point into the quadtree, might adjust to only spatial
    // coordinates if managed externally
    QuadNode insert(String name, int x, int y, int x1, int y1, int x2, int y2);


    // Remove a point from the quadtree, might adjust for spatial identification
    QuadNode remove(String name, int x1, int y1, int x2, int y2);


    // Remove a point at the point x and y
    QuadNode remove(int x, int y, int x1, int y1, int x2, int y2);


    // Perform a region search, returning a list of points within a given region
    List<Point> regionsearch(
        int x,
        int y,
        int width,
        int height,
        int x1,
        int y1,
        int x2,
        int y2);


    QuadNode search(String name);


    // Check if the node is a leaf
    boolean isLeaf();


    // Report duplicates, returning a list of duplicate points or modifying
    // method signature as needed
    List<Point> duplicates();
    
    
    //Function to display all children of tree in preorder 
    public void dump(int level);


    int[] getBounds(); // Returns an array of bounds [x1, y1, x2, y2]

}
