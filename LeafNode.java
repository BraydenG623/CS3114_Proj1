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
    public LeafNode(int x, int y, int size, List<Point> points) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.points = points;
    }


    // Helper function to check if all points in the leaf node are the same
    private boolean areAllPointsSame() {
        if (points.isEmpty() || points.size() == 1) {
            return true; // An empty node or a node with a single point
                         // implicitly satisfies the condition
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
        ArrayList<Integer> param = new ArrayList<>();
        param.add(x);
        param.add(y);
        param.add(size);
        for (Point point : points) {
            parent.insert(point.getName(), point.getX(), point.getY(), param);
        }
        return parent;
    }


    @Override
    public QuadNode insert(
        String name,
        int pointX,
        int pointY,
        ArrayList<Integer> param) {
        Point newPoint = new Point(name, pointX, pointY);
        points.add(newPoint);
        // Check the conditions for splitting
        if (points.size() > 3 && !areAllPointsSame()) {
            return splitAndRedistribute();
        }
        else {
            return this; // No split needed
        }
    }


    public List<Point> getPoints() {
        return points;
    }


    @Override
    public QuadNode remove(String name, int size) {
        for (Point point : points) {
            if (point.getName().equals(name)) {
                remove(point.getX(), point.getY(), size);
                return this;
            }
        }
        return null;
    }


    @Override
    public QuadNode remove(int x, int y, int size) {
        String name = getName(x, y);
        boolean removed = points.removeIf(p -> p.getX() == x && p.getY() == y);
        if (removed) {
            System.out.println("Point removed: (" + name + ", " + x + ", " + y
                + ")");
        }

        // After removal, this node itself is returned since the leaf node does
        // not decide on merges.
        // The decision to merge is up to the parent internal node after the
        // removal operation.
        return this;
    }


    public String getName(int x, int y) {
        for (Point point : points) {
            if (point.getX() == x && point.getY() == y) {
                return point.getName();
            }
        }
        return null;

    }


    @Override
    public List<Point> regionsearch(
        int searchX,
        int searchY,
        int width,
        int height,
        int size) {
        // Increment node count for visiting this leaf node
        QuadTree.incRegionSearchNC();

        List<Point> foundPoints = new ArrayList<>();
        // Iterate through each point in this leaf node
        for (Point point : points) {
            // Check if the point is within the search region
            if (point.getX() > searchX && point.getX() < searchX + width
                && point.getY() > searchY && point.getY() < searchY
                    + height) {
                // If the point is within the region, add it to the list of
                // found points
                System.out.println("Point found: (" + point.getName() + ", "
                    + point.getX() + ", " + point.getY() + ")");
                foundPoints.add(point);
            }
        }
        // Return the list of points found within the search region
        return foundPoints;
    }


    @Override
    public boolean isLeaf() {
        // TODO: Implement:
        return true;
    }


    @Override
    public int dump(int level, int x, int y, int size) {
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
