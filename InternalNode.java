import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;

/**
 * Represents an internal node in a QuadTree structure,
 * which can have four children.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-25
 */
@SuppressWarnings("unused")
public class InternalNode implements QuadNode {

    // There are the 4 quadrants for the 2Dspace
    QuadNode nw, ne, sw, se;

    // Bounds of this node's region
    private final int x1, y1, x2, y2;

    // Additional property to track the level of this node.
    // It's used to determine the indentation when printing.
// private int level;

    @Override
    public int[] getBounds() {
        return new int[] { x1, y1, x2, y2 };
    }


    /**
     * Constructor for InternalNode. Initializes children to the flyweight
     * instance
     * to represent empty leaf nodes.
     */
    public InternalNode(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        // Initialize children to the flyweight instance for empty nodes
        nw = EmptyNode.getInstance();
        ne = EmptyNode.getInstance();
        sw = EmptyNode.getInstance();
        se = EmptyNode.getInstance();
    }

    // Helper method to insert a point into a child, handling empty nodes and
    // leaf nodes
// private QuadNode insertIntoChild(
// QuadNode child,
// String name,
// int x,
// int y,
// int x1,
// int y1,
// int x2,
// int y2) {
// if (child instanceof EmptyNode) {
// // If the child is an empty node, replace it with a new leaf node
// LeafNode newLeaf = new LeafNode(x1, y1, x2, y2);
// return newLeaf.insert(name, x, y, x1, x2, y1, y2);
// }
// else {
// // If the child is not empty, continue insertion recursively
// return child.insert(name, x, y, x1, x2, y1, y2);
// }
// }
//
// // Inside the InternalNode class


    // Helper method to insert a point into a child, handling empty nodes and
    // leaf nodes
    // Inside InternalNode.java
    private QuadNode insertIntoChild(
        QuadNode child,
        String name,
        int x,
        int y,
        int x1,
        int y1,
        int x2,
        int y2) {
        if (child instanceof EmptyNode) {
            // If the child is an empty node, replace it with a new leaf node
            // without passing level
            LeafNode newLeaf = new LeafNode(x1, y1, x2, y2);
            return newLeaf.insert(name, x, y, x1, y1, x2, y2);
        }
        else {
            // If the child is not empty, continue insertion recursively
            return child.insert(name, x, y, x1, y1, x2, y2);
        }
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
        // Determine the correct quadrant for the point
        int midX = (x1 + x2) / 2;
        int midY = (y1 + y2) / 2;

        if (x < midX) {
            if (y < midY) {
                nw = insertIntoChild(nw, name, x, y, x1, y1, midX, midY);
            }
            else {
                sw = insertIntoChild(sw, name, x, y, x1, midY, midX, y2);
            }
        }
        else {
            if (y < midY) {
                ne = insertIntoChild(ne, name, x, y, midX, y1, x2, midY);
            }
            else {
                se = insertIntoChild(se, name, x, y, midX, midY, x2, y2);
            }
        }
        return this;
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
    public List<Point> regionsearch(
        int searchX,
        int searchY,
        int width,
        int height,
        int x1,
        int x2,
        int y1,
        int y2) {
        List<Point> pointsInRegion = new ArrayList<>();

        // Calculate midpoints to partition the current node's area into four
        // quadrants.
        int midX = x1 + (x2 - x1) / 2; // Midpoint along X-axis
        int midY = y1 + (y2 - y1) / 2; // Midpoint along Y-axis

        // Check intersection with NW quadrant
        // Uses the original upper left bounds (x1,y1) and the midpoints to
        // define the NW quadrant's bounds.
        if (intersects(searchX, searchY, width, height, x1, y1, midX, midY)) {
            // If the search area intersects with this quadrant, recurse into
            // the NW node with its bounds.
            pointsInRegion.addAll(nw.regionsearch(searchX, searchY, width,
                height, x1, midX, y1, midY));
        }

        // Check intersection with NE quadrant
        // For NE, the x range starts from midX to the original right bound x2,
        // keeping the y range from original up to midY.
        if (intersects(searchX, searchY, width, height, midX, y1, x2, midY)) {
            // Similar to NW, but for the NE quadrant.
            pointsInRegion.addAll(ne.regionsearch(searchX, searchY, width,
                height, midX, x2, y1, midY));
        }

        // Check intersection with SW quadrant
        // SW quadrant's x bounds start from the original left to midX, but y
        // range is from midY to original bottom y2.
        if (intersects(searchX, searchY, width, height, x1, midY, midX, y2)) {
            // Similar process for SW quadrant.
            pointsInRegion.addAll(sw.regionsearch(searchX, searchY, width,
                height, x1, midX, midY, y2));
        }

        // Check intersection with SE quadrant
        // SE quadrant fully utilizes the midpoints as its upper left bounds and
        // the original bottom right as its lower bounds.
        if (intersects(searchX, searchY, width, height, midX, midY, x2, y2)) {
            // SE quadrant checks, covering the bottom right portion.
            pointsInRegion.addAll(se.regionsearch(searchX, searchY, width,
                height, midX, x2, midY, y2));
        }

        return pointsInRegion;
    }


    private boolean intersects(
        int searchX,
        int searchY,
        int width,
        int height,
        int x1,
        int y1,
        int x2,
        int y2) {
        // Check if there is any overlap between the search area and this
        // quadrant
        boolean overlapInX = (searchX < x2) && (searchX + width > x1);
        boolean overlapInY = (searchY < y2) && (searchY + height > y1);
        return overlapInX && overlapInY;
    }


    @Override
    public QuadNode search(String name) {
        // Internal nodes don't contain points, so we need to search in the
        // children.
        // We'll search recursively in all non-empty children nodes.
        // If any child node returns a non-null result, that's the node we're
        // looking for.

        QuadNode result;

        // Recursively search the northwest child node
        result = nw.search(name);
        if (result != null) {
            return result; // Found in the northwest quadrant
        }

        // Recursively search the northeast child node
        result = ne.search(name);
        if (result != null) {
            return result; // Found in the northeast quadrant
        }

        // Recursively search the southwest child node
        result = sw.search(name);
        if (result != null) {
            return result; // Found in the southwest quadrant
        }

        // Recursively search the southeast child node
        result = se.search(name);
        if (result != null) {
            return result; // Found in the southeast quadrant
        }

        // If we get here, the point was not found in any of the children
        return null;
    }


    @Override
    public boolean isLeaf() {
        // TODO: Implement:
        return false;
    }


    @Override
    public List<Point> duplicates() {
        // TODO: Implement:
        return null;
    }


//    @Override
//    public void dump(int level) {
//        // Calculate midpoints for the current node's bounds
//        int midX = x1 + (x2 - x1) / 2;
//        int midY = y1 + (y2 - y1) / 2;
//
//        printWithIndentation("Node at " + x1 + ", " + y1 + ", " + (x2 - x1)
//            + ": Internal", level);
//
//        // Check each child and print its bounds or "Empty" before calling dump
//        if (nw instanceof EmptyNode) {
//            printWithIndentation("Node at " + x1 + ", " + y1 + ", " + (midX
//                - x1) + ": Empty", level + 1);
//        }
//        else {
//            nw.dump(level + 1);
//        }
//
//        if (ne instanceof EmptyNode) {
//            printWithIndentation("Node at " + midX + ", " + y1 + ", " + (x2
//                - midX) + ": Empty", level + 1);
//        }
//        else {
//            ne.dump(level + 1);
//        }
//
//        if (sw instanceof EmptyNode) {
//            printWithIndentation("Node at " + x1 + ", " + midY + ", " + (midX
//                - x1) + ": Empty", level + 1);
//        }
//        else {
//            sw.dump(level + 1);
//        }
//
//        if (se instanceof EmptyNode) {
//            printWithIndentation("Node at " + midX + ", " + midY + ", " + (x2
//                - midX) + ": Empty", level + 1);
//        }
//        else {
//            se.dump(level + 1);
//        }
//    }
//
//
//    private void printWithIndentation(String text, int level) {
//        for (int i = 0; i < level; i++) {
//            System.out.print("  "); // 2 spaces for each level of indentation
//        }
//        System.out.println(text);
//    }
    
    @Override
    public void dump(int level) {
        QuadTree.incrementNodeCount(); // Increment for the internal node itself.
        printWithIndentation("Node at " + x1 + ", " + y1 + ", " + (x2 - x1) + ": Internal", level);
        nw.dump(level + 1);
        ne.dump(level + 1);
        sw.dump(level + 1);
        se.dump(level + 1);
    }


    private void printWithIndentation(String text, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(text);
    }


}
