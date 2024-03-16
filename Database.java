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
    @SuppressWarnings("unused")
    private Iterator<KVPair<String, Rectangle>> itr1;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
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
        Rectangle rect = pair.getValue();
        if (rect.isInvalid()) {
            System.out.println("Rectangle rejected: (" + pair.getKey() + ", "
                + rect.getxCoordinate() + ", " + rect.getyCoordinate() + ", "
                + rect.getWidth() + ", " + rect.getHeight() + ")");
        }
        else {
            list.insert(pair);
            System.out.println("Rectangle inserted: (" + pair.getKey() + ", "
                + rect.getxCoordinate() + ", " + rect.getyCoordinate() + ", "
                + rect.getWidth() + ", " + rect.getHeight() + ")");
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
        KVPair<String, Rectangle> found = list.remove(name);
        if (found == null) {
            System.out.println("Rectangle not removed: " + name);
            return;
        }
        else {
            Rectangle rect = found.getValue();
            System.out.println("Rectangle removed: (" + found.getKey() + ", "
                + rect.getxCoordinate() + ", " + rect.getyCoordinate() + ", "
                + rect.getWidth() + ", " + rect.getHeight() + ")");
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
        Rectangle searchRect = new Rectangle(x, y, w, h);
        if (searchRect.isInvalid()) {
            System.out.println("Rectangle rejected: (" + searchRect
                .getxCoordinate() + ", " + searchRect.getyCoordinate() + ", "
                + searchRect.getWidth() + ", " + searchRect.getHeight() + ")");
            return;
        }
        KVPair<String, Rectangle> removed = list.removeByValue(searchRect);
        if (removed != null) {
            System.out.println("Rectangle removed: (" + removed.getKey() + ", "
                + x + ", " + y + ", " + w + ", " + h + ")");
        }
        else if (removed == null) {
            System.out.println("Rectangle not found: (" + x + ", " + y + ", "
                + w + ", " + h + ")");
            return;
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
        Rectangle searchRect = new Rectangle(x, y, w, h);

        // Check if the search rectangle is valid.
        if (w <= 0 || h <= 0) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", " + w
                + ", " + h + ")");
            return;
        }

        System.out.println("Rectangles intersecting region (" + x + ", " + y
            + ", " + w + ", " + h + "):");
        // boolean found = false;

        // Iterate through all rectangles in the skip list.
        for (KVPair<String, Rectangle> pair : list) {
            Rectangle rect = pair.getValue();
            // Check if the current rectangle intersects with the search
            // rectangle.
            if (rect.intersect(searchRect)) {
                // Print the rectangle that intersects with the search region.
                System.out.println("(" + pair.getKey() + ", " + rect
                    .getxCoordinate() + ", " + rect.getyCoordinate() + ", "
                    + rect.getWidth() + ", " + rect.getHeight() + ")");
                // found = true;
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
        System.out.println("Intersection pairs:");

        // boolean foundIntersection = false;
        // Initialize the first iterator (itr1) to traverse the skip list.
        Iterator<KVPair<String, Rectangle>> itr1New = list.iterator();

        while (itr1New.hasNext()) {
            KVPair<String, Rectangle> pair1 = itr1New.next();
            Rectangle rect1 = pair1.getValue();

            // Create a new iterator for the nested loop to ensure every pair is
            // compared.
            Iterator<KVPair<String, Rectangle>> itr2 = list.iterator();

            while (itr2.hasNext()) {
                KVPair<String, Rectangle> pair2 = itr2.next();

                // Avoid comparing the same rectangle with itself.
                if (pair1 != pair2) {
                    Rectangle rect2 = pair2.getValue();

                    // Check if rectangles intersect using the intersect method.
                    if (rect1.intersect(rect2)) {
                        // foundIntersection = true;
                        // Print the intersecting pair with rectangle names and
                        // dimensions.
                        System.out.println("(" + pair1.getKey() + ", " + rect1
                            .getxCoordinate() + ", " + rect1.getyCoordinate()
                            + ", " + rect1.getWidth() + ", " + rect1.getHeight()
                            + " | " + pair2.getKey() + ", " + rect2
                                .getxCoordinate() + ", " + rect2
                                    .getyCoordinate() + ", " + rect2.getWidth()
                            + ", " + rect2.getHeight() + ")");
                    }
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
        // Use the skip list's search method to find rectangles with the given
        // name.
        ArrayList<KVPair<String, Rectangle>> foundRectangles = list.search(
            name);

        if (foundRectangles != null && !foundRectangles.isEmpty()) {
            // Rectangles with the specified name are found, print them.
            System.out.println("Rectangles found:");
            for (KVPair<String, Rectangle> pair : foundRectangles) {
                Rectangle rectangle = pair.getValue();
                // Print the rectangle's name and its details using the getter
                // methods.
                System.out.println("(" + pair.getKey() + ", " + rectangle
                    .getxCoordinate() + ", " + rectangle.getyCoordinate() + ", "
                    + rectangle.getWidth() + ", " + rectangle.getHeight()
                    + ")");
            }
        }
        else {
            // No rectangles found with the specified name.
            System.out.println("Rectangle not found: " + "(" + name + ")");
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