import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    public QuadNode insert(
        String name,
        int pointX,
        int pointY,
        ArrayList<Integer> param) {
        // Determine the quadrant for the new point
        List<Point> points = new ArrayList<>();
        int halfSize = size / 2;
        int midX = x + halfSize;
        int midY = y + halfSize;
        ArrayList<Integer> param2 = new ArrayList<>();

        // Determine in which quadrant (child) the point belongs
        if (pointX < midX) {
            if (pointY < midY) {
                // Point belongs to the NW quadrant
                if (nw instanceof EmptyNode) {
                    Point point = new Point(name, pointX, pointY);
                    points.add(point);
                    nw = new LeafNode(x, y, halfSize, points);
                }
                else {
                    param.add(x);
                    param.add(y);
                    param.add(halfSize);
                    nw = nw.insert(name, pointX, pointY, param2);
                }
            }
            else {
                // Point belongs to the SW quadrant
                if (sw instanceof EmptyNode) {
                    Point point = new Point(name, pointX, pointY);
                    points.add(point);
                    sw = new LeafNode(x, midY, halfSize, points);
                }
                else {
                    param.add(x);
                    param.add(midY);
                    param.add(halfSize);
                    sw = sw.insert(name, pointX, pointY, param2);
                }
            }
        }
        else {
            if (pointY < midY) {
                // Point belongs to the NE quadrant
                if (ne instanceof EmptyNode) {
                    Point point = new Point(name, pointX, pointY);
                    points.add(point);
                    ne = new LeafNode(midX, y, halfSize, points);
                }
                else {
                    param.add(midX);
                    param.add(y);
                    param.add(halfSize);
                    ne = ne.insert(name, pointX, pointY, param2);
                }
            }
            else {
                // Point belongs to the SE quadrant
                if (se instanceof EmptyNode) {
                    Point point = new Point(name, pointX, pointY);
                    points.add(point);
                    se = new LeafNode(midX, midY, halfSize, points);
                }
                else {
                    param.add(midX);
                    param.add(midY);
                    param.add(halfSize);
                    se = se.insert(name, pointX, pointY, param2);
                }
            }
        }
        return this; // Return the current internal node after insertion
    }


    @Override
    public QuadNode remove(String name, int size) {
        // TODO: Implement:
        QuadNode result;
        result = nw.remove(name, size);
        if (result != null) {
            return result;
        }

        result = ne.remove(name, size);
        if (result != null) {
            return result;
        }

        result = sw.remove(name, size);
        if (result != null) {
            return result;
        }

        result = se.remove(name, size);
        if (result != null) {
            return result;
        }

        // If we get here, the point was not found in any of the children
        return null;
    }


    @Override
    public QuadNode remove(int x, int y, int size) {
        int halfSize = this.size / 2;
        int midX = this.x + halfSize;
        int midY = this.y + halfSize;

        // Determine in which quadrant the point lies and try to remove it
        if (x < midX) {
            if (y < midY) {
                nw = nw.remove(x, y, halfSize);
            }
            else {
                sw = sw.remove(x, y, halfSize);
            }
        }
        else {
            if (y < midY) {
                ne = ne.remove(x, y, halfSize);
            }
            else {
                se = se.remove(x, y, halfSize);
            }
        }

        // Check if we can merge nodes after removal
        return checkAndMerge();
    }


    private QuadNode checkAndMerge() {
        List<Point> combinedPoints = new ArrayList<>();

        // Attempt to collect points from all child nodes if they are LeafNodes
        for (QuadNode child : Arrays.asList(nw, ne, sw, se)) {
            if (child instanceof LeafNode) {
                combinedPoints.addAll(((LeafNode)child).getPoints());
            }
            else {
                // If any child is not a LeafNode, merging is not possible
                return this;
            }
        }

        // Check if the combined points meet the conditions for merging
        if (combinedPoints.size() <= 3 && pointsAreSameOrEmpty(
            combinedPoints)) {
            // Create a new LeafNode with combined points
            return new LeafNode(x, y, size, combinedPoints);
        }

        // If cannot merge, return this internal node
        return this;
    }


    private boolean pointsAreSameOrEmpty(List<Point> points) {
        // Implement logic to check if all points have the same coordinates or
        // if list is empty
        if (points.isEmpty() || points.size() == 1) {
            return true;
        }
        Point firstPoint = points.get(0);
        for (Point point : points) {
            if (!point.equals(firstPoint)) {
                return false; // Found points with different coordinates
            }
        }
        return true; // All points have the same coordinates or list is empty
    }


    @Override
    public List<Point> regionsearch(
        int searchX,
        int searchY,
        int width,
        int height,
        int size) {
        
        
        QuadTree.incRegionSearchNC();

        List<Point> pointsInRegion = new ArrayList<>();
        int halfSize = this.size / 2;
        int midX = this.x + halfSize;
        int midY = this.y + halfSize;

        // Increase node count here or handle it outside
        // If printing or counting is handled in QuadTree,pass a
        // counter object.

        // Check each quadrant for intersection with the search region and
        // collect points
        
        //NorthWest Quad
        if (intersects(searchX, searchY, width, height, this.x, this.y, midX,
            midY)) {
            pointsInRegion.addAll(nw.regionsearch(searchX, searchY, width,
                height, halfSize));
        }
        
        //NorthEast Quad
        if (intersects(searchX, searchY, width, height, midX, this.y, this.x
            + size, midY)) {
            pointsInRegion.addAll(ne.regionsearch(searchX, searchY, width,
                height, halfSize));
        }
        
        //SouthWest Quad
        if (intersects(searchX, searchY, width, height, this.x, midY, midX,
            this.y + size)) {
            pointsInRegion.addAll(sw.regionsearch(searchX, searchY, width,
                height, halfSize));
        }
        
        //SouthEast Quad
        if (intersects(searchX, searchY, width, height, midX, midY, this.x
            + size, this.y + size)) {
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
        // Check if there is any overlap between the search area and this
        // quadrant
        boolean overlapInX = (searchX < x2) && (searchX + width > x1);
        boolean overlapInY = (searchY < y2) && (searchY + height > y1);
        return overlapInX && overlapInY;
    }

//
// private boolean intersects(
// int searchX,
// int searchY,
// int width,
// int height,
// int x1,
// int y1,
// int x2,
// int y2) {
// // Check if search region intersects with this quadrant's region
// return !(searchX + width < x1 || searchX > x2 || searchY + height < y1
// || searchY > y2);
// }


    @Override
    public boolean isLeaf() {
        // TODO: Implement:
        return false;
    }


    @Override
    public int dump(int level, int x, int y, int size) {
        int nodeCount = 1; // Start with 1 for the current node
        printWithIndentation("Node at " + x + ", " + y + ", " + size
            + ": Internal", level);

        // Recursively dump children, if they are not empty, and accumulate
        // their counts
        nodeCount += nw.dump(level + 1, x, y, size / 2);
        nodeCount += ne.dump(level + 1, (x + size) / 2, y, size / 2);
        nodeCount += sw.dump(level + 1, x, (y + size) / 2, size / 2);
        nodeCount += se.dump(level + 1, (x + size) / 2, (y + size) / 2, size
            / 2);

        return nodeCount;
    }


    private void printWithIndentation(String text, int level) {
        // Create indentation based on the level
        String indentation = "  ".repeat(level);
        System.out.println(indentation + text);
    }

}
