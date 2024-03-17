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
public class Point {
    String name;
    int x, y;

    public Point(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    

    
//    public void get(Object other) {
//        Point point = (Point)other;
//        this.x = point.getX();
//        this.y = point.getY();
//        this.name = point.getName();
//    }
   //TODO: Implement the getters/setters later
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    
    @Override
    public boolean equals(Object other) {
        Point point = (Point)other; 
        if((this.x == point.getX()) && (this.y == point.getY())) {
            return true;
        }
        return false;
    }
    
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//        Point other = (Point) obj;
//        return this.x == other.x && this.y == other.y;
//    }
}