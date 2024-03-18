import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import student.TestableRandom;

/**
 * Represents a point in a two-dimensional space with a name.
 * 
 * @author Brayden Gardner, Ryan Kluttz
 * @version 1.0
 * @since 2024-02-25
 */

@SuppressWarnings("unused")
public class PointInt {
    private int x, y;

    /**
     * 
     * @param x
     * @param y
     */
    public PointInt(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * 
     * @return
     */
    public int getX() {
        return x;
    }


    /**
     * 
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }


    /**
     * 
     * @return
     */
    public int getY() {
        return y;
    }


    /**
     * 
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * @return
     */
    public String toString() {
        return (this.x + ", " + this.y);
    }


    /**
     * @param Object
     */
    @Override
    public boolean equals(Object other) {
        PointInt point = (PointInt)other;
        return ((this.x == point.getX()) && (this.y == point.getY()));
    }
}
