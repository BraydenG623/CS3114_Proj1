import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import student.TestableRandom;

/**
 * Represents a leaf node in a QuadTree structure,
 * which stores points directly.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-25
 */
@SuppressWarnings("unused")
public class LeafNode implements QuadNode {
    List<Point> points = new ArrayList<>();

    private int x1, y1, x2, y2; // Spatial bounds

    // Constructor to set bounds
    public LeafNode(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }


    @Override
    public int[] getBounds() {
        return new int[] { x1, y1, x2, y2 };
    }


    // Helper function to check for decompotion rule on distinct points
    private boolean containsDistinctCoordinates() {
        Set<String> uniqueCoordinates = new HashSet<>();
        for (Point point : points) {
            // Convert coordinates to a unique string representation
            String coord = point.getX() + "," + point.getY();
            uniqueCoordinates.add(coord);
        }
        // If the set size is equal to the list size, all coordinates are unique
        return uniqueCoordinates.size() < points.size();
    }

    // Inside LeafNode class


    private QuadNode splitAndRedistribute() {
        InternalNode internalNode = new InternalNode(x1, y1, x2, y2);
        for (Point point : points) {
            internalNode.insert(point.getName(), point.getX(), point.getY(), x1,
                x2, y1, y2);
        }
        points.clear();
        return internalNode;
    }


    @Override
    public QuadNode insert(
        String name,
        int x,
        int y,
        int x1,
        int x2,
        int y1,
        int y2) {
        Point newPoint = new Point(name, x, y);
        points.add(newPoint);

        if (points.size() > 3 && containsDistinctCoordinates()) {
            return splitAndRedistribute();
        }
        else {
            return this;
        }
    }


    @Override
    public QuadNode remove(String name, int x1, int x2, int y1, int y2) {
        // TODO: Implement:
        return this;
    }


    @Override
    public QuadNode remove(int x, int y, int x1, int x2, int y1, int y2) {

        return this;
    }


    @Override
    public QuadNode search(String name) {
        // LeafNode means we are at the point so we just need to check
        // the 3 possiblw points in this node to see if the name
        // matches what we are looking for

        for (Point point : points) {
            // Check if the point matches
            if (point.getName().equals(name)) {
                // If it does, return this point

                // First create a new leafNode
                LeafNode resultNode = new LeafNode(x1, y1, x2, y2);

                resultNode.points.add(point);
                return resultNode;
            }
        }

        // Return null if the point is not found
        return null;
    }


    @Override
    public List<Point> regionsearch(
        int x,
        int y,
        int width,
        int height,
        int x1,
        int x2,
        int y1,
        int y2) {
        // TODO: Implement:
        List<Point> foundPoints = new ArrayList<>();
        for (Point point : points) {
            if (point.getX() >= x && point.getX() <= x + width && point
                .getY() >= y && point.getY() <= y + height) {
                foundPoints.add(point);
            }
        }
        return foundPoints;
    }


    @Override
    public boolean isLeaf() {
        // TODO: Implement:
        return true;
    }


    @Override
    public List<Point> duplicates() {
        // TODO: Implement:
        return null;
    }


    @Override
    public void dump(int level) {
        QuadTree.incrementNodeCount(); // Increment for the leaf node itself.
        // Print the node information with proper indentation
        System.out.println("Node at " + x1 + ", " + y1 + ", "
            + (x2 - x1) + ":" );

        // Loop through each point in the leaf node and print on separate lines
        // with indentation
        for (Point point : points) {
            System.out.println("(" + point.getName() + ", "
                + point.getX() + ", " + point.getY() + ")");
        }
    }

}
