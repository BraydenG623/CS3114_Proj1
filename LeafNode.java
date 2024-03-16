// import java.lang.reflect.Array;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Map;
// import java.util.HashSet;
// import java.util.Set;
// import java.util.Random;
// import student.TestableRandom;
//
/// **
// * Represents a leaf node in a QuadTree structure,
// * which stores points directly.
// *
// * @author Brayden Gardner, Ryan Kluttz
// * @version 1.0
// * @since 2024-02-25
// */
// @SuppressWarnings("unused")
// public class LeafNode implements QuadNode {
// List<Point> points = new ArrayList<>();
//
// private int x1, y1, x2, y2; // Spatial bounds
//
// // Constructor to set bounds
// public LeafNode(int x1, int y1, int x2, int y2) {
// this.x1 = x1;
// this.y1 = y1;
// this.x2 = x2;
// this.y2 = y2;
// }
//
//
// @Override
// public int[] getBounds() {
// return new int[] { x1, y1, x2, y2 };
// }
//
//
// // Helper function to check if all points in the leaf node are the same
// private boolean areAllPointsSame() {
// if (points.isEmpty()) {
// return true;
// }
// Point firstPoint = points.get(0);
// for (Point point : points) {
// if (!point.equals(firstPoint)) {
// return false;
// }
// }
// return true;
// }
//
// @Override
// public QuadNode insert(String name, int x, int y, int x1, int y1, int x2, int
// y2) {
// // System.out.println("Inserting into LeafNode at bounds: " + x1 + ", " + y1
// + ", " + x2 + ", " + y2);
// Point newPoint = new Point(name, x, y);
//
// if (points.size() >= 3 && !areAllPointsSame()) {
// // System.out.println("Splitting LeafNode at bounds: " + x1 + ", " + y1 + ",
// " + x2 + ", " + y2);
// QuadNode newNode = splitAndRedistribute();
// return newNode.insert(name, x, y, x1, y1, x2, y2);
// } else {
// //System.out.println("Point inserted: (" + name + ", " + x + ", " + y + ")");
// points.add(newPoint);
// return this;
// }
// }
//
// private QuadNode splitAndRedistribute() {
// // System.out.println("Redistributing points in LeafNode at bounds: " + x1 +
// ", " + y1 + ", " + x2 + ", " + y2);
// InternalNode internalNode = new InternalNode(x1, y1, x2, y2);
// for (Point point : points) {
// internalNode.insert(point.getName(), point.getX(), point.getY(), x1, y1, x2,
// y2);
// }
// points.clear();
// return internalNode;
// }
//
//
// @Override
// public QuadNode remove(String name, int x1, int x2, int y1, int y2) {
// // TODO: Implement:
// return this;
// }
//
//
// @Override
// public QuadNode remove(int x, int y, int x1, int x2, int y1, int y2) {
//
// return this;
// }
//
//
// @Override
// public QuadNode search(String name) {
// // LeafNode means we are at the point so we just need to check
// // the 3 possiblw points in this node to see if the name
// // matches what we are looking for
//
// for (Point point : points) {
// // Check if the point matches
// if (point.getName().equals(name)) {
// // If it does, return this point
//
// // First create a new leafNode
// LeafNode resultNode = new LeafNode(x1, y1, x2, y2);
//
// resultNode.points.add(point);
// return resultNode;
// }
// }
//
// // Return null if the point is not found
// return null;
// }
//
//
// @Override
// public List<Point> regionsearch(
// int x,
// int y,
// int width,
// int height,
// int x1,
// int x2,
// int y1,
// int y2) {
// // TODO: Implement:
// List<Point> foundPoints = new ArrayList<>();
// for (Point point : points) {
// if (point.getX() >= x && point.getX() <= x + width && point
// .getY() >= y && point.getY() <= y + height) {
// foundPoints.add(point);
// }
// }
// return foundPoints;
// }
//
//
// @Override
// public boolean isLeaf() {
// // TODO: Implement:
// return true;
// }
//
//
// @Override
// public List<Point> duplicates() {
// // TODO: Implement:
// return null;
// }
//
//
// @Override
// public void dump(int level) {
// QuadTree.incrementNodeCount(); // Increment for the leaf node itself.
// printWithIndentation("Node at " + x1 + ", " + y1 + ", " + (x2 - x1) + ":",
// level);
//
// if (points.isEmpty()) {
// printWithIndentation("Empty", level + 1);
// } else {
// for (Point point : points) {
// printWithIndentation("(" + point.getName() + ", " + point.getX() + ", " +
// point.getY() + ")", level);
// }
// }
// }
//
// private void printWithIndentation(String text, int level) {
// for (int i = 0; i < level; i++) {
// System.out.print(" ");
// }
// System.out.println(text);
// }
//
//
// }

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

//
// // Check if all points in this node have the same coordinates
// private boolean areAllPointsAtSameCoordinates() {
// if (points.size() < 2) {
// return true; // Zero or one point always satisfies the condition
// // trivially
// }
// Point firstPoint = points.get(0);
// for (Point point : points.subList(1, points.size())) {
// if (!point.equals(firstPoint)) {
// return false;
// }
// }
// return true;
// }


    @Override
    public QuadNode insert(String name, int pointX, int pointY, ArrayList<Integer> param) {
        Point newPoint = new Point(name, pointX, pointY);
        points.add(newPoint);
        // Check the conditions for splitting
        if (points.size() > 3 && !areAllPointsSame()) {
            return splitAndRedistribute();
        }
        else {
            //points.add(newPoint);
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
                LeafNode resultNode = new LeafNode(x, y, size, points);

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
