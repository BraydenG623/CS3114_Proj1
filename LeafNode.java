import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    @Override
    public QuadNode insert(Point point) {
      //TODO: Implement:
        return this;
    }

    @Override
    public QuadNode remove(Point point) {
      //TODO: Implement:
        return this;
    }

    @Override
    public List<Point> regionSearch(int x, int y, int width, int height) {
      //TODO: Implement:
        return points;
    }

    @Override
    public boolean isLeaf() {
      //TODO: Implement:
        return true;
    }

    @Override
    public List<Point> duplicates() {
      //TODO: Implement:
        return null;
    }
}