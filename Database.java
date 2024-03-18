import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * Also note that the Database class will have a clearer role in Project2,
 * where we will have two data structures. The Database class will then
 * determine
 * which command should be directed to which data structure.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information

    private SkipList<String, PointInt> list;
    private QuadTree quadTree;

    // This is an Iterator object over the SkipList to loop through it from
    // outside the class.
    // You will need to define an extra Iterator for the intersections method.
    @SuppressWarnings("unused")
    private Iterator<KVPair<String, PointInt>> itr1;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, PointInt>();
        quadTree = new QuadTree();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * add the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, PointInt> pair) {
        PointInt point = new PointInt(pair.getValue().getX(), pair.getValue()
            .getY());
        String name = pair.getKey();
        if (point.getX() < 0 || point.getY() < 0 || point.getX() > 1024 || point
            .getY() > 1024) {
            System.out.println("Point rejected: (" + name + ", " + point.getX()
                + ", " + point.getY() + ")");
        }
        else {
            list.insert(pair);
            quadTree.insert(name, point.getX(), point.getY());
        }
    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
        KVPair<String, PointInt> removed = list.remove(name);
        if (removed == null) {
            System.out.println("Point not removed: " + name);
            return;
        }
        else {
            quadTree.remove(removed.getKey(), removed.getValue().getX(), removed
                .getValue().getY());
            System.out.println("Point removed: (" + name + ", " + removed
                .getValue().getX() + ", " + removed.getValue().getY() + ")");
        }
    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     */
    public void remove(int x, int y) {
        // quadtree first
        if (x < 0 || y < 0 || x > 1024 || y > 1024) {
            System.out.println("Point rejected: (" + x + ", " + y + ")");
            return;
        }
        String name = quadTree.remove("1", x, y);
        if (name.equals("1")) {
            System.out.println("Point not found: (" + x + ", " + y + ")");
            return;
        }
        else {
            PointInt point = new PointInt(x, y);
            list.removeByValue(point, name);
            System.out.println("Point removed: (" + name + ", " + x + ", " + y
                + ")");
        }
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region.
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
        // Create a query rectangle with the given region.
        if (w <= 0 || h <= 0) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", " + w
                + ", " + h + ")");
            return;
        }
        quadTree.regionsearch(x, y, w, h);
    }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
        // Use the skip list's search method to find rectangles with the given
        // name.
        ArrayList<KVPair<String, PointInt>> found = list.search(name);

        if (found != null && !found.isEmpty()) {
            // Rectangles with the specified name are found, print them.
            for (KVPair<String, PointInt> pair : found) {
                PointInt point = new PointInt(pair.getValue().getX(), pair
                    .getValue().getY());
                // Print the rectangle's name and its details using the getter
                // methods.
                System.out.println("Found (" + pair.getKey() + ", " + point
                    .getX() + ", " + point.getY() + ")");
            }
        }
        else {
            // No rectangles found with the specified name.
            System.out.println("Point not found: " + name);
        }
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
        quadTree.dump();
    }


    /*
     * Duplicates function
     */
    public void duplicates() {
        quadTree.duplicates();
    }

}
