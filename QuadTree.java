import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;


/**
 * Represents the QuadTree structure for organizing points 
 * in a two-dimensional space.
 * Supports operations such as insert, remove, regionSearch, 
 * and duplicates.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-24
 */
@SuppressWarnings("unused")
public class QuadTree {

    QuadNode root = EmptyNode.getInstance();
   
    //World size
    final int WIDTH = 1024;
    final int HEIGHT = 1024;

    public void insert(Point point) {
        root = root.insert(point);
    }

    public void remove(Point point) {
        root = root.remove(point);
    }
}
