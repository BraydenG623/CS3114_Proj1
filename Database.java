import java.util.ArrayList;
import java.util.Iterator;

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
    private SkipList<String, Rectangle> list;

    // This is an Iterator object over the SkipList to loop through it from
    // outside the class.
    // You will need to define an extra Iterator for the intersections method.
    private Iterator<KVPair<String, Rectangle>> itr1;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
        itr1 = list.iterator(); // Initialize itr1
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
    public void insert(KVPair<String, Rectangle> pair) {
        // Delegates the decision mostly to SkipList, only
        // writing the correct message to the console from
        // that
        Rectangle rectangle = pair.getValue();
        
        if (rectangle.getWidth() < 0 || rectangle.getHeight() < 0) {
           // System.out.println("Invalid rectangle with negative coordinates. Insertion failed.");
            return;
        }

        if (!rectangle.isInvalid()) {
            list.insert(pair);
           // System.out.println("Inserted: " + rectangle.toString());
            return;
        }
        else {
           // System.out.println("Invalid rectangle. Insertion failed.");
            return;
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
        KVPair<String, Rectangle> removedPair = list.remove(name);

        if (removedPair != null) {
            System.out.println("Removed: " + name);
        }
        else {
            System.out.println("Rectangle with name '" + name + "' not found.");
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
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        Rectangle searchRectangle = new Rectangle(x, y, w, h);
        KVPair<String, Rectangle> removedPair = list.removeByValue(
            searchRectangle);

        if (removedPair != null) {
            System.out.println("Removed: " + removedPair.getValue().toString());
        }
        else {
            System.out.println("Rectangle with coordinates (" + x + ", " + y
                + ", " + w + ", " + h + ") not found.");
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
        System.out.println("Rectangles in the region (" + x + ", " + y + ", "
            + w + ", " + h + "):");

        for (KVPair<String, Rectangle> pair : list) {
            Rectangle rectangle = pair.getValue();
            if (rectangle.intersect(new Rectangle(x, y, w, h))) {
                System.out.println(rectangle.toString());
            }
        }
    }


    /**
     * Prints out all the rectangles that intersect each other. Note that
     * it is better not to implement an intersections method in the SkipList
     * class
     * as the SkipList needs to be agnostic about the fact that it is storing
     * Rectangles.
     */
    public void intersections() {
        System.out.println("Intersecting rectangles:");

        // Use the iterator directly instead of itr1
        Iterator<KVPair<String, Rectangle>> iterator = list.iterator();

        while (iterator.hasNext()) {
            KVPair<String, Rectangle> pair1 = iterator.next();

            // Use a separate iterator for the second loop
            Iterator<KVPair<String, Rectangle>> iterator2 = list.iterator();

            while (iterator2.hasNext()) {
                KVPair<String, Rectangle> pair2 = iterator2.next();

                if (!pair1.getKey().equals(pair2.getKey())) {
                    if (pair1.getValue().intersect(pair2.getValue())) {
                        System.out.println(pair1.getValue().toString()
                                + " intersects with " + pair2.getValue()
                                .toString());
                    }
                    //(3, 3, 5, 5) intersects with (0, 0, 5, 5)
                }
            }
        }
    }



    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
        ArrayList<KVPair<String, Rectangle>> result = list.search(name);

        if (result != null) {
            System.out.println("Rectangles with name '" + name + "':");
            for (KVPair<String, Rectangle> pair : result) {
                System.out.println(pair.getValue().toString());
            }
        }
        else {
            System.out.println("No rectangles with name '" + name + "' found.");
        }
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
    }

}
