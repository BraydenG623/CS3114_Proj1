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

    private final int x, y; // Top-left corner of this node
    private final int size; // Size of the side of the square

    public InternalNode(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        // Initialize children to represent empty leaf nodes
        nw = ne = sw = se = EmptyNode.getInstance();
    }


    @Override
    public QuadNode insert(String name, int pointX, int pointY, int size) {
        // Determine the quadrant for the new point
        int halfSize = size / 2;
        int midX = x + halfSize;
        int midY = y + halfSize;

        // Determine in which quadrant (child) the point belongs
        if (pointX < midX) {
            if (pointY < midY) {
                // Point belongs to the NW quadrant
                if (nw instanceof EmptyNode) {
                    nw = new LeafNode(x, y, halfSize);
                }
                nw = nw.insert(name, pointX, pointY, halfSize);
            }
            else {
                // Point belongs to the SW quadrant
                if (sw instanceof EmptyNode) {
                    sw = new LeafNode(x, midY, halfSize);
                }
                sw = sw.insert(name, pointX, pointY, halfSize);
            }
        }
        else {
            if (pointY < midY) {
                // Point belongs to the NE quadrant
                if (ne instanceof EmptyNode) {
                    ne = new LeafNode(midX, y, halfSize);
                }
                ne = ne.insert(name, pointX, pointY, halfSize);
            }
            else {
                // Point belongs to the SE quadrant
                if (se instanceof EmptyNode) {
                    se = new LeafNode(midX, midY, halfSize);
                }
                se = se.insert(name, pointX, pointY, halfSize);
            }
        }
        return this; // Return the current internal node after insertion
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
    public List<Point> regionsearch(
        int searchX,
        int searchY,
        int width,
        int height,
        int size) {
        
        
        List<Point> pointsInRegion = new ArrayList<>();
        int halfSize = this.size / 2;
        int midX = x + halfSize;
        int midY = y + halfSize;

        // Check each quadrant for intersection with the search region
        // NW quadrant
        if (intersects(searchX, searchY, width, height, x, y, midX, midY)) {
            pointsInRegion.addAll(nw.regionsearch(searchX, searchY, width,
                height, halfSize));
        }
        // NE quadrant
        if (intersects(searchX, searchY, width, height, midX, y, x + size,
            midY)) {
            pointsInRegion.addAll(ne.regionsearch(searchX, searchY, width,
                height, halfSize));
        }
        // SW quadrant
        if (intersects(searchX, searchY, width, height, x, midY, midX, y
            + size)) {
            pointsInRegion.addAll(sw.regionsearch(searchX, searchY, width,
                height, halfSize));
        }
        // SE quadrant
        if (intersects(searchX, searchY, width, height, midX, midY, x + size, y
            + size)) {
            pointsInRegion.addAll(se.regionsearch(searchX, searchY, width,
                height, halfSize));
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
        // Check if search region intersects with this quadrant's region
        return !(searchX + width < x1 || searchX > x2 || searchY + height < y1
            || searchY > y2);
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


    @Override
    public int dump(int level) {
        int nodeCount = 1; // Start with 1 for the current node
        printWithIndentation("Node at " + x + ", " + y + ", " + size
            + ": Internal", level);

        // Recursively dump children, if they are not empty, and accumulate
        // their counts
        nodeCount += nw.dump(level + 1);
        nodeCount += ne.dump(level + 1);
        nodeCount += sw.dump(level + 1);
        nodeCount += se.dump(level + 1);

        return nodeCount;
    }


    private void printWithIndentation(String text, int level) {
        // Create indentation based on the level
        String indentation = "  ".repeat(level);
        System.out.println(indentation + text);
    }

}
