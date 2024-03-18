import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;

/**
 * Implements the Flyweight pattern for empty nodes
 * in a QuadTree structure.
 * Uses a singleton instance to represent all empty nodes.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-25
 */
@SuppressWarnings("unused")
public class EmptyNode implements QuadNode {

    private static EmptyNode flyweight = null;

    /**
     * 
     */
    private EmptyNode() {
        // Private constructor to prevent outside instantiation
    }


    /**
     * 
     * @return
     */
    public static EmptyNode getInstance() {
        if (flyweight == null) {
            flyweight = new EmptyNode();
        }
        return flyweight;
    }


    /**
     * 
     * @param name
     * @param x
     * @param y
     * @param param
     */
    @Override
    public QuadNode insert(
        String name,
        int x,
        int y,
        ArrayList<Integer> param) {
        // Create a new LeafNode covering the same region and insert the point
        List<Point> points = new ArrayList<>();
        LeafNode newLeaf = new LeafNode(param.get(0), param.get(1), param.get(
            2), points);
        return newLeaf.insert(name, x, y, param);
    }


    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param size
     */
    @Override
    public List<Point> regionsearch(
        int x,
        int y,
        int width,
        int height,
        int size) {
        // Empty node has no points, return an empty list
        return Collections.emptyList();
    }


    /**
     * 
     * @param text
     * @param level
     */
    private void printWithIndentation(String text, int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }
        System.out.println(text);
    }


    /**
     * 
     * @param level
     * @param x_empty
     * @param y_empty
     * @param size_empty
     */
    @Override
    public int dump(int level, int x_empty, int y_empty, int size_empty) {
        // Indentation based on the level
        String indentation = "  ".repeat(level);
        System.out.println(indentation + "Node at " + x_empty + ", " + y_empty
            + ", " + size_empty + ": Empty");

        // Returns 1 since it's counting the current node
        return 1;
    }


    /**
     * 
     * @return
     */
    @Override
    public List<Point> collectPoints() {
        return new ArrayList<>();
    }


    /**
     * 
     * @return
     */
    @Override
    public boolean isInternal() {
        // TODO Auto-generated method stub
        return false;
    }


    /**
     * 
     * @param name
     * @param pointX
     * @param pointY
     * @param param
     */
    @Override
    public RemovedObject remove(
        String name,
        int pointX,
        int pointY,
        ArrayList<Integer> param) {

        RemovedObject result = new RemovedObject(this, "1");
        return result;
    }

}
