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
    
    public boolean isInternal() {
        return true;
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
    public RemovedObject remove(
        String name,
        int pointX,
        int pointY,
        ArrayList<Integer> param) {
        // TODO Auto-generated method stub
        
        int top_X = param.get(0);
        int top_Y = param.get(1);
        int size_T = param.get(2);
        
        
        String removedName = "";
        
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
                RemovedObject nwRemoved = nw.remove(name, pointX, pointY, nwParam);
                nw = nwRemoved.getChangedNode();
                removedName = nwRemoved.getRemovedName();
            } else {
                RemovedObject swRemoved = sw.remove(name, pointX, pointY, swParam);
                sw = swRemoved.getChangedNode();
                removedName = swRemoved.getRemovedName();
            }
        } else {
            if (pointY < midY) {
                RemovedObject neRemoved = ne.remove(name, pointX, pointY, neParam);
                ne = neRemoved.getChangedNode();
                removedName = neRemoved.getRemovedName();
            } else {
                RemovedObject seRemoved = se.remove(name, pointX, pointY, seParam);
                se = seRemoved.getChangedNode();
                removedName = seRemoved.getRemovedName();
            }
        }
        
        List<Point> childPoints = new ArrayList<>();
        childPoints.addAll(nw.collectPoints());
        childPoints.addAll(ne.collectPoints());
        childPoints.addAll(sw.collectPoints());
        childPoints.addAll(se.collectPoints());
        
        boolean samePoints = true;
        
        if(childPoints.size() > 3) {
            Point first = childPoints.get(0);
            for (Point point : childPoints) {
                if(point.getX() != first.getX() || point.getY() != first.getY()) {
                    samePoints = false;
                    break;
                }
            }
            if(!samePoints) {
                RemovedObject result = new RemovedObject(this, removedName);
                return result;
            }
            
        }
        
        LeafNode newLeaf = new LeafNode(param.get(0), param.get(1), param.get(2), childPoints);
        
        RemovedObject result = new RemovedObject(newLeaf, removedName);
        return result;
    }
}
