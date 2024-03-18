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
    int x, y;

    public PointInt( int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    

   //TODO: Implement the getters/setters later

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public String toString() {
        return (this.x + ", " + this.y);
    }
    
    @Override
    public boolean equals(Object other) {
        PointInt point = (PointInt)other; 
        if((this.x == point.getX()) && (this.y == point.getY())) {
            return true;
        }
        return false;
    }
}