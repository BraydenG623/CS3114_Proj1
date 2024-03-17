//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import student.TestableRandom;
//
///**
// * Represents an internal node in a QuadTree structure,
// * which can have four children.
// * 
// * @author Brayden Gardner, Ryan Kluttz
// * @version 1.0
// * @since 2024-02-25
// */
//@SuppressWarnings("unused")
//public class InternalNode implements QuadNode {
//
//    // There are the 4 quadrants for the 2Dspace
//    QuadNode nw, ne, sw, se;
//
//    // Bounds of this node's region
//    private final int x1, y1, x2, y2;
//
//    // Additional property to track the level of this node.
//    // It's used to determine the indentation when printing.
////    private int level;
//
//    @Override
//    public int[] getBounds() {
//        return new int[] { x1, y1, x2, y2 };
//    }
//
//
//    /**
//     * Constructor for InternalNode. Initializes children to the flyweight
//     * instance
//     * to represent empty leaf nodes.
//     */
//    public InternalNode(int x1, int y1, int x2, int y2) {
//        this.x1 = x1;
//        this.y1 = y1;
//        this.x2 = x2;
//        this.y2 = y2;
//        // Initialize children to the flyweight instance for empty nodes
//        nw = EmptyNode.getInstance();
//        ne = EmptyNode.getInstance();
//        sw = EmptyNode.getInstance();
//        se = EmptyNode.getInstance();
//    }
//
//
//
//    @Override
//    public QuadNode insert(String name, int x, int y, int x1, int x2, int y1, int y2) {
//     //   System.out.println("Inserting into InternalNode at bounds: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
//        int midX = (x1 + x2) / 2;
//        int midY = (y1 + y2) / 2;
//
//        if (x < midX) {
//            if (y < midY) {
//                nw = insertIntoChild(nw, name, x, y, x1, y1, midX, midY);
//            } else {
//                sw = insertIntoChild(sw, name, x, y, x1, midY, midX, y2);
//            }
//        } else {
//            if (y < midY) {
//                ne = insertIntoChild(ne, name, x, y, midX, y1, x2, midY);
//            } else {
//                se = insertIntoChild(se, name, x, y, midX, midY, x2, y2);
//            }
//        }
//        return this;
//    }
//
//    private QuadNode insertIntoChild(QuadNode child, String name, int x, int y, int x1, int y1, int x2, int y2) {
//      //  System.out.println("Inserting into child node of InternalNode at bounds: " + x1 + ", " + y1 + ", " + x2 + ", " + y2);
//        if (child instanceof EmptyNode) {
//            LeafNode newLeaf = new LeafNode(x1, y1, x2, y2);
//            return newLeaf.insert(name, x, y, x1, y1, x2, y2);
//        } else {
//            return child.insert(name, x, y, x1, y1, x2, y2);
//        }
//    }
//
//
//    @Override
//    public QuadNode remove(String name, int x1, int x2, int y1, int y2) {
//        // TODO: Implement:
//        return this;
//    }
//
//
//    @Override
//    public QuadNode remove(int x, int y, int x1, int x2, int y1, int y2) {
//
//        return this;
//    }
//
//
//    @Override
//    public List<Point> regionsearch(
//        int searchX,
//        int searchY,
//        int width,
//        int height,
//        int x1,
//        int x2,
//        int y1,
//        int y2) {
//        List<Point> pointsInRegion = new ArrayList<>();
//
//        // Calculate midpoints to partition the current node's area into four
//        // quadrants.
//        int midX = x1 + (x2 - x1) / 2; // Midpoint along X-axis
//        int midY = y1 + (y2 - y1) / 2; // Midpoint along Y-axis
//
//        // Check intersection with NW quadrant
//        // Uses the original upper left bounds (x1,y1) and the midpoints to
//        // define the NW quadrant's bounds.
//        if (intersects(searchX, searchY, width, height, x1, y1, midX, midY)) {
//            // If the search area intersects with this quadrant, recurse into
//            // the NW node with its bounds.
//            pointsInRegion.addAll(nw.regionsearch(searchX, searchY, width,
//                height, x1, midX, y1, midY));
//        }
//
//        // Check intersection with NE quadrant
//        // For NE, the x range starts from midX to the original right bound x2,
//        // keeping the y range from original up to midY.
//        if (intersects(searchX, searchY, width, height, midX, y1, x2, midY)) {
//            // Similar to NW, but for the NE quadrant.
//            pointsInRegion.addAll(ne.regionsearch(searchX, searchY, width,
//                height, midX, x2, y1, midY));
//        }
//
//        // Check intersection with SW quadrant
//        // SW quadrant's x bounds start from the original left to midX, but y
//        // range is from midY to original bottom y2.
//        if (intersects(searchX, searchY, width, height, x1, midY, midX, y2)) {
//            // Similar process for SW quadrant.
//            pointsInRegion.addAll(sw.regionsearch(searchX, searchY, width,
//                height, x1, midX, midY, y2));
//        }
//
//        // Check intersection with SE quadrant
//        // SE quadrant fully utilizes the midpoints as its upper left bounds and
//        // the original bottom right as its lower bounds.
//        if (intersects(searchX, searchY, width, height, midX, midY, x2, y2)) {
//            // SE quadrant checks, covering the bottom right portion.
//            pointsInRegion.addAll(se.regionsearch(searchX, searchY, width,
//                height, midX, x2, midY, y2));
//        }
//
//        return pointsInRegion;
//    }
//
//
//    private boolean intersects(
//        int searchX,
//        int searchY,
//        int width,
//        int height,
//        int x1,
//        int y1,
//        int x2,
//        int y2) {
//        // Check if there is any overlap between the search area and this
//        // quadrant
//        boolean overlapInX = (searchX < x2) && (searchX + width > x1);
//        boolean overlapInY = (searchY < y2) && (searchY + height > y1);
//        return overlapInX && overlapInY;
//    }
//
//
//    @Override
//    public QuadNode search(String name) {
//        // Internal nodes don't contain points, so we need to search in the
//        // children.
//        // We'll search recursively in all non-empty children nodes.
//        // If any child node returns a non-null result, that's the node we're
//        // looking for.
//
//        QuadNode result;
//
//        // Recursively search the northwest child node
//        result = nw.search(name);
//        if (result != null) {
//            return result; // Found in the northwest quadrant
//        }
//
//        // Recursively search the northeast child node
//        result = ne.search(name);
//        if (result != null) {
//            return result; // Found in the northeast quadrant
//        }
//
//        // Recursively search the southwest child node
//        result = sw.search(name);
//        if (result != null) {
//            return result; // Found in the southwest quadrant
//        }
//
//        // Recursively search the southeast child node
//        result = se.search(name);
//        if (result != null) {
//            return result; // Found in the southeast quadrant
//        }
//
//        // If we get here, the point was not found in any of the children
//        return null;
//    }
//
//
//    @Override
//    public boolean isLeaf() {
//        // TODO: Implement:
//        return false;
//    }
//
//
//    @Override
//    public List<Point> duplicates() {
//        // TODO: Implement:
//        return null;
//    }
//
//
//    @Override
//    public void dump(int level) {
//        
//        QuadTree.incrementNodeCount(); // Increment for the internal node itself.
//        printWithIndentation("Node at " + x1 + ", " + y1 + ", " + (x2 - x1) + ": Internal", level);
//        
//        
//        dumpChild(nw, level + 1, x1, y1, (x1 + x2) / 2, (y1 + y2) / 2);
//        dumpChild(ne, level + 1, (x1 + x2) / 2, y1, x2, (y1 + y2) / 2);
//        dumpChild(se, level + 1, (x1 + x2) / 2, (y1 + y2) / 2, x2, y2);
//        dumpChild(sw, level + 1, x1, (y1 + y2) / 2, (x1 + x2) / 2, y2);
//        
//    }
//
//    private void dumpChild(QuadNode child, int level, int x1, int y1, int x2, int y2) {
//        if (child instanceof EmptyNode) {
//            QuadTree.incrementNodeCount();
//            printWithIndentation("Node at " + x1 + ", " + y1 + ", " + (x2 - x1) + ": Empty", level);
//        } else {
//            child.dump(level);
//        }
//    }
//
//    private void printWithIndentation(String text, int level) {
//        for (int i = 0; i < level; i++) {
//            System.out.print("     ");
//        }
//        System.out.println(text);
//    }
//}


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
    public QuadNode insert(String name, int pointX, int pointY, ArrayList<Integer> param) {
        // Determine the quadrant for the new point
        if (pointX < x || pointX >= x + size || pointY < y || pointY >= y + size) {
            return this; // Point is outside the bounds, so no insertion is performed
        }
        
        List<Point> points = new ArrayList<>();
//        int halfSize = size / 2;
//        int midX = x + halfSize;
//        int midY = y + halfSize;
        int halfSize = param.get(2) / 2;
        int midX = param.get(0) + halfSize;
        int midY = param.get(1) + halfSize;
        ArrayList<Integer> nwParam = new ArrayList<>(Arrays.asList(param.get(0), param.get(1), halfSize));
        ArrayList<Integer> neParam = new ArrayList<>(Arrays.asList(midX, param.get(1), halfSize));
        ArrayList<Integer> swParam = new ArrayList<>(Arrays.asList(param.get(0), midY, halfSize));
        ArrayList<Integer> seParam = new ArrayList<>(Arrays.asList(midX, midY, halfSize));

        // Determine in which quadrant (child) the point belongs and insert recursively
        if (pointX < midX) {
            if (pointY < midY) {
                nw = nw.insert(name, pointX, pointY, nwParam);
            } else {
                sw = sw.insert(name, pointX, pointY, swParam);
            }
        } else {
            if (pointY < midY) {
                ne = ne.insert(name, pointX, pointY, neParam);
            } else {
                se = se.insert(name, pointX, pointY, seParam);
            }
        }
        return this; // Return the current internal node after insertion
    }

    @Override
    public QuadNode search(String name, int size) {
        // Internal nodes don't contain points, so we need to search in the
        // children.
        // We'll search recursively in all non-empty children nodes.
        // If any child node returns a non-null result, that's the node we're
        // looking for.

        QuadNode result;

        // Recursively search the northwest child node
        result = nw.search(name,size);
        if (result != null) {
            return result; // Found in the northwest quadrant
        }

        // Recursively search the northeast child node
        result = ne.search(name,size);
        if (result != null) {
            return result; // Found in the northeast quadrant
        }

        // Recursively search the southwest child node
        result = sw.search(name,size);
        if (result != null) {
            return result; // Found in the southwest quadrant
        }

        // Recursively search the southeast child node
        result = se.search(name,size);
        if (result != null) {
            return result; // Found in the southeast quadrant
        }

        // If we get here, the point was not found in any of the children
        return null;
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
        return this;
        //return this;
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
                return checkAndMerge(this);
            } else {
                sw = sw.remove(x, y, halfSize);
                return checkAndMerge(this);
            }
        } else {
            if (y < midY) {
                ne = ne.remove(x, y, halfSize);
                return checkAndMerge(this);
            } else {
                se = se.remove(x, y, halfSize);
                return checkAndMerge(this);
            }
        }
        
        
        // Check if we can merge nodes after removal
       // return this;
    }
    
    public boolean isInternal() {
        return true;
    }
    
    private QuadNode checkAndMerge(InternalNode node) {
        List<Point> combinedPoints = new ArrayList<>();
        
        //first check if all children are either empty 
        //or leaf nodes, we cannot merge an internal node
        if(node.nw.isInternal() || node.nw.isInternal() || node.se.isInternal() || node.sw.isInternal()) {
            //return this, we cannot merge when an internal node has an internal
            //node as a child
            return this;
        }
        
        // Collect the points of all Leaf/Empty Nodes
        combinedPoints.addAll(node.nw.collectPoints());
        combinedPoints.addAll(node.ne.collectPoints());
        combinedPoints.addAll(node.sw.collectPoints());
        combinedPoints.addAll(node.se.collectPoints());
        
        // Check if the combined points meet the conditions for merging
        if (combinedPoints.size() <= 3 || pointsAreSameOrEmpty(combinedPoints)) {
            // Create a new LeafNode with combined points
            // this (internal node) should become a leaf node with 
            // same x,y,size as current internal node
            // all of its 4 children should be cleared
            return new LeafNode(x, y, size, combinedPoints);
        }
        
        // If cannot merge, return this internal node
        return node;
    }
    
    private boolean pointsAreSameOrEmpty(List<Point> points) {
        // Implement logic to check if all points have the same coordinates or if list is empty
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


    @Override
    public boolean isLeaf() {
        // TODO: Implement:
        return false;
    }


    @Override
    public int dump(int level, int x_empty, int y_empty, int size_empty) {
        int nodeCount = 1; // Start with 1 for the current node
        printWithIndentation("Node at " + x + ", " + y + ", " + size
            + ": Internal", level);

        // Recursively dump children, if they are not empty, and accumulate
        // their counts
        int x_nw=x;
        int y_nw=y;
        int x_ne=x+(size/2);
        int y_ne=y;
        int x_sw=x;
        int y_sw=y+(size/2);
        int x_se=x+(size/2);
        int y_se=y+(size/2);
        int size_val=size/2;
        nodeCount += nw.dump(level + 1, x_nw, y_nw, size_val);      
        nodeCount += ne.dump(level + 1, x_ne, y_ne, size_val);    
        nodeCount += sw.dump(level + 1, x_sw, y_sw, size_val);          
        nodeCount += se.dump(level + 1, x_se, y_se, size_val); 
        return nodeCount;
    }
    


    private void printWithIndentation(String text, int level) {
        // Create indentation based on the level
        String indentation = "  ".repeat(level);
        System.out.println(indentation + text);
    }
    

    @Override
    public List<Point> collectPoints() {
        List<Point> points = new ArrayList<>();
        points.addAll(nw.collectPoints());
        points.addAll(ne.collectPoints());
        points.addAll(sw.collectPoints());
        points.addAll(se.collectPoints());
        return points;
    }


    @Override
    public List<Integer> pointForRemoval(String name) {
        // TODO Auto-generated method stub
        List<Integer> result = new ArrayList<>();
        result.addAll(nw.pointForRemoval(name));
        if (result != null) {
            return result; 
        }

        result.addAll(ne.pointForRemoval(name));
        if (result != null) {
            return result; 
        }

        result.addAll(sw.pointForRemoval(name));
        if (result != null) {
            return result; 
        }

        result.addAll(se.pointForRemoval(name));
        if (result != null) {
            return result; 
        }

        // If we get here, the point was not found in any of the children
        return null;
    }


    @Override
    public String nameForRemoval(int x, int y, int worldSize) {
        // TODO Auto-generated method stub
        int halfSize = this.size / 2;
        int midX = this.x + halfSize;
        int midY = this.y + halfSize;
        String result;
        // Determine in which quadrant the point lies and try to remove it
        if (x < midX) {
            if (y < midY) {
                result = nw.nameForRemoval(x, y, halfSize);
                return result;
            } else {
                result = sw.nameForRemoval(x, y, halfSize);
                return result;
            }
        } else {
            if (y < midY) {
                result = ne.nameForRemoval(x, y, halfSize);
                return result;
            } else {
                result = se.nameForRemoval(x, y, halfSize);
                return result;
            }
        }
    }



}
