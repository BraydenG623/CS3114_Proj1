//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import student.TestableRandom;
//
///**
// * This class provides the abstract interface for the QuadNode
// * to be implmented by the subclass (Internal, Leaf, and Empty node)
// * 
// * @author Brayden Gardner, Ryan Kluttz
// * 
// * @version 2024-25-02
// */
//@SuppressWarnings("unused")
//public interface QuadNode {
//    // Insert a point into the quadtree, might adjust to only spatial
//    // coordinates if managed externally
//    QuadNode insert(String name, int x, int y, int x1, int y1, int x2, int y2);
//    
//    
//
//    // Remove a point from the quadtree, might adjust for spatial identification
//    QuadNode remove(String name, int x1, int y1, int x2, int y2);
//
//
//    // Remove a point at the point x and y
//    QuadNode remove(int x, int y, int x1, int y1, int x2, int y2);
//
//
//    // Perform a region search, returning a list of points within a given region
//    List<Point> regionsearch(
//        int x,
//        int y,
//        int width,
//        int height,
//        int x1,
//        int y1,
//        int x2,
//        int y2);
//
//
//    QuadNode search(String name);
//
//
//    // Check if the node is a leaf
//    boolean isLeaf();
//
//
//    // Report duplicates, returning a list of duplicate points or modifying
//    // method signature as needed
//    List<Point> duplicates();
//    
//    
//    //Function to display all children of tree in preorder 
//    public void dump(int level);
//
//
//    int[] getBounds(); // Returns an array of bounds [x1, y1, x2, y2]
//
//}
//

import java.util.ArrayList;
import java.util.List;

/**
 * This interface provides the abstract structure for QuadNode implementations
 * (InternalNode, LeafNode, and EmptyNode) within a QuadTree.
 * It includes operations for inserting, removing, searching points, region searching,
 * identifying duplicates, and dumping the tree structure.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 2024-25-02
 */
public interface QuadNode {
    /**
     * Inserts a point into the QuadTree.
     * 
     * @param name The name of the point.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param size The size of the current node's region.
     * @return The node after insertion.
     */
    QuadNode insert(String name, int x, int y, ArrayList<Integer> param);

    /**
     * Removes a point by name from the QuadTree.
     * 
     * @param name The name of the point to remove.
     * @param size The size of the current node's region.
     * @return The node after removal.
     */
    QuadNode remove(String name, int size);

    /**
     * Removes a point by coordinates from the QuadTree.
     * 
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param size The size of the current node's region.
     * @return The node after removal.
     */
    QuadNode remove(int x, int y, int size);

    /**
     * Performs a region search within the QuadTree.
     * 
     * @param x The x-coordinate of the bottom-left corner of the search area.
     * @param y The y-coordinate of the bottom-left corner of the search area.
     * @param width The width of the search area.
     * @param height The height of the search area.
     * @param size The size of the current node's region.
     * @return A list of points found within the specified region.
     */
    List<Point> regionsearch(int x, int y, int width, int height, int size);

    /**
     * Searches for a point by name within the QuadTree.
     * 
     * @param name The name of the point to search for.
     * @return The node containing the point, or null if not found.
     */
   // List<QuadNode> search(String name, int x, int y, int size);
    //QuadNode search(String name, int x, int y, int size);

    /**
     * Checks if the current node is a leaf node.
     * 
     * @return true if the node is a leaf, false otherwise.
     */
    boolean isLeaf();

//    /**
//     * Identifies and returns a list of duplicate points within the QuadTree.
//     * 
//     * @return A list of duplicate points.
//     */
//    List<Point> duplicates(int x, int y);

    /**
     * Dumps the structure of the QuadTree starting from the current node.
     * 
     * @param level The current depth or level in the tree, used for formatting output.
     * @return The count of nodes processed during the dump.
     */
    int dump(int level, int x, int y, int size);
    
}
