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

    private int x, y; // Top-left corner of this node
    private int size; // Size of the side of the square

    // Adjusted constructor to set bounds using a single size parameter
    public LeafNode(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }


    // Helper function to check if all points in the leaf node are the same
    private boolean areAllPointsSame() {
        if (points.isEmpty() || points.size() == 1) {
            return true; // An empty node or a node with a single point implicitly satisfies the condition
        }
        Point first = points.get(0);
        for (Point point : points) {
            if (!point.equals(first)) {
                return false; // Found points with different coordinates
            }
        }
        return true; // All points have the same coordinates
    }


    private QuadNode splitAndRedistribute() {
        InternalNode parent = new InternalNode(x, y, size);
        for (Point point : points) {
            parent.insert(point.getName(), point.getX(), point.getY(), size);
        }
        return parent;
    }

//
//    // Check if all points in this node have the same coordinates
//    private boolean areAllPointsAtSameCoordinates() {
//        if (points.size() < 2) {
//            return true; // Zero or one point always satisfies the condition
//                         // trivially
//        }
//        Point firstPoint = points.get(0);
//        for (Point point : points.subList(1, points.size())) {
//            if (!point.equals(firstPoint)) {
//                return false;
//            }
//        }
//        return true;
//    }


    @Override
    public QuadNode insert(String name, int pointX, int pointY, int size) {
        Point newPoint = new Point(name, pointX, pointY);
        

        // Check the conditions for splitting
        if (points.size() > 3 && !areAllPointsSame()) {
            return splitAndRedistribute();
        }
        else {
            points.add(newPoint);
            return this; // No split needed
        }
    }


    @Override
    public QuadNode remove(String name, int size) {
        // TODO: Implement:
        return this;
    }


    @Override
    public QuadNode remove(int x, int y, int size) {

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
                LeafNode resultNode = new LeafNode(x, y, size);

                resultNode.points.add(point);
                return resultNode;
            }
        }

        // Return null if the point is not found
        return null;
    }


    @Override
    public List<Point> regionsearch(
        int searchX,
        int searchY,
        int width,
        int height,
        int size) {
        List<Point> foundPoints = new ArrayList<>();
        for (Point point : points) {
            if (point.getX() >= searchX && point.getX() <= searchX + width
                && point.getY() >= searchY && point.getY() <= searchY
                    + height) {
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
    public int dump(int level) {
        // This node itself counts as one
        int nodeCount = 1;
        printWithIndentation("Node at " + x + ", " + y + ", " + size + ":",
            level);
        for (Point point : points) {
            // Points are printed at the same level as the node description
            printWithIndentation("(" + point.getName() + ", " + point.getX()
                + ", " + point.getY() + ")", level);
        }
        return nodeCount; // Return count of this node
    }


    private void printWithIndentation(String message, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  "); // Append two spaces per level for indentation
        }
        System.out.println(sb.toString() + message);
    }

}
