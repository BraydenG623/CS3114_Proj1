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
    
    private static int regionSearchNC = 0;
    
    public static void incRegionSearchNC() {
        regionSearchNC++;
    }
    
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
//        if (width <= 0 || height <= 0) {
//            System.out.println("Region search rejected: Width and height must be greater than 0.");
//            return;
//        }
        
        // Reset the node count before starting the search
        regionSearchNC = 0;
        
        // Begin region search
        System.out.println("Points intersecting region (" + x + ", " + y + ", " + width + ", " + height + "):");
        this.pointList = root.regionsearch(x, y, width, height, WORLD_SIZE);
        
        // After the search, print the number of nodes visited
        System.out.println(regionSearchNC + " quadtree nodes visited\n");
    }

}