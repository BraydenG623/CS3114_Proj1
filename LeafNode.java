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
    private List<Point> points = new ArrayList<>();

    private int x, y; // Top-left corner of this node
    private int size; // Size of the side of the square

    /**
     * 
     * @param x
     * @param y
     * @param size
     * @param points
     */
    public LeafNode(int x, int y, int size, List<Point> points) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.points = points;
    }


    /**
     * 
     * @return
     */
    private boolean allSame() {
        Point start = points.get(0);
        for (Point point : points) {
            if ((point.getX() != start.getX()) || (point.getY() != start
                .getY())) {
                return false;
            }
        }
        return true;
    }


    /**
     * 
     * @param topX
     * @param topY
     * @param sizeN
     * @return
     */
    private QuadNode splitAndRedistribute(int topX, int topY, int sizeN) {
        InternalNode parent = new InternalNode(x, y, size);
        ArrayList<Integer> param = new ArrayList<>();
        param.add(topX);
        param.add(topY);
        param.add(sizeN);
        for (Point point : points) {
            parent.insert(point.getName(), point.getX(), point.getY(), param);
        }
        return parent;
    }


    /**
     * 
     * @param name
     * @param pointX
     * @param pointY
     * @param param
     * @return
     */
    @Override
    public QuadNode insert(
        String name,
        int pointX,
        int pointY,
        ArrayList<Integer> param) {
        Point newPoint = new Point(name, pointX, pointY);
        points.add(newPoint);
        // Check the conditions for splitting
        if (points.size() > 3 && !allSame()) {
            return splitAndRedistribute(param.get(0), param.get(1), param.get(
                2));
        }
        else {
            return this; // No split needed
        }
    }


    /**
     * 
     * @return
     */
    public List<Point> getPoints() {
        return points;
    }


    /**
     * 
     * @param xP
     * @param yP
     * @return
     */
    public String getName(int xP, int yP) {
        for (Point point : points) {
            if (point.getX() == xP && point.getY() == yP) {
                return point.getName();
            }
        }
        return null;

    }


    /**
     * 
     * @param searchX
     * @param searchY
     * @param width
     * @param height
     * @param sizeT
     * @return
     */
    @Override
    public List<Point> regionsearch(
        int searchX,
        int searchY,
        int width,
        int height,
        int sizeT) {
        // Increment node count for visiting this leaf node
        QuadTree.incRegionSearchNC();

        List<Point> foundPoints = new ArrayList<>();
        // Iterate through each point in this leaf node
        for (Point point : points) {
            // Check if the point is within the search region
            if (point.getX() > searchX && point.getX() < searchX + width
                && point.getY() > searchY && point.getY() < searchY + height) {
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
    public int dump(int level, int x_empty, int y_empty, int size_empty) {
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


    @Override
    public List<Point> collectPoints() {
        return new ArrayList<>(points);
        // return points;
    }


    @Override
    public boolean isInternal() {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public RemovedObject remove(
        String name,
        int pointX,
        int pointY,
        ArrayList<Integer> param) {

        String pointName = "1";
        if (!(name.compareTo("1") == 0)) {
            for (Point point : points) {
                // Check if you have found the exact point
                // by name, x, and y
                if (point.getX() == pointX && point.getY() == pointY && point
                    .getName() == name) {
                    points.remove(point);
                    break;
                }
            }
        }
        else {
            for (Point point : points) {
                // Check if you have found the exact point
                // by name, x, and y
                if (point.getX() == pointX && point.getY() == pointY) {
                    pointName = point.getName();
                    points.remove(point);
                    break;
                }
            }
        }
        if (points.isEmpty()) {
            QuadNode newEmpty = EmptyNode.getInstance();
            RemovedObject result = new RemovedObject(newEmpty, pointName);
            return result;
        }

        RemovedObject result = new RemovedObject(this, pointName);
        return result;

    }
}
