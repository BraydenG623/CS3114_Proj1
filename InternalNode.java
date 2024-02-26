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
public class InternalNode implements QuadNode{

    //There are the 4 quadrants for the 2Dspace
    QuadNode nw, ne, sw, se;
    
    @Override 
    public QuadNode insert(Point point) {
        //TODO: Implement:
        return this;
    }
    
    public QuadNode remove(Point point) {
        //TODO: Implement:
        return this;
    }
    
    @Override
    public List<Point> regionSearch(int x, int y, int width, int height) {
      //TODO: Implement:
        return null;
    }
    
    
    @Override
    public boolean isLeaf() {
      //TODO: Implement:
        return false;
    }
    
    
    @Override
    public List<Point> duplicates() {
      //TODO: Implement:
        return null;
    }
    
}
