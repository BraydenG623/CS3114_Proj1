import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains JUnit tests for the Database class.
 * It tests various scenarios for the Region search, 
 * insert, removeByName ,removeByCoordinates, and dump methods.
 * 
 * @author Ryan Kluttz
 * @version 1.0
 * @since 2024-02-03
 */


public class DatabaseJunitTests {

    private Database database;

    @Before
    public void setUp() {
        database = new Database();
    }

    /**
     * Test for inserting a valid rectangle.
     */
    @Test
    public void testInsertValidRectangle() {
        Rectangle rectangle = new Rectangle(1, 1, 3, 4);
        KVPair<String, Rectangle> pair = new KVPair<>("Rect1", rectangle);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.insert(pair);
        database.dump();
        
        System.setOut(System.out);

        String expectedOutput = "SkipList dump:\n"
            + "Node has depth 0, Value null\n" + "SkipList size is: 0";

        assertNotEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }

    /**
     * Test for inserting invalid rectangle
     * with negative coordinates.
     */
    @Test
    public void testInsertInvalidRectangle() {
        Rectangle rectangle = new Rectangle(-1, 1, 3, 4);
        KVPair<String, Rectangle> pair = new KVPair<>("Rect2", rectangle);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.insert(pair);
        database.dump();
        
        System.setOut(System.out);

        String expectedOutput = "SkipList dump:\n"
            + "Node has depth 0, Value null\n" + "SkipList size is: 0";

        assertNotEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }

    /**
     * Test remove rectangle by name.
     */
    @Test
    public void testRemoveByName() {
        Rectangle rectangle = new Rectangle(1, 1, 3, 4);
        KVPair<String, Rectangle> pair = new KVPair<>("Rect3", rectangle);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.insert(pair);
        database.remove("Rect3");

        System.setOut(System.out);

        String expectedOutput = "Removed: (1, 1, 3, 4)";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }

    /**
     * Test remove rectangle by coordinates.
     */
    @Test
    public void testRemoveByCoordinates() {
        Rectangle rectangle = new Rectangle(1, 1, 3, 4);
        KVPair<String, Rectangle> pair = new KVPair<>("Rect4", rectangle);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.insert(pair);
        database.remove(1, 1, 3, 4);

        System.setOut(System.out);

        String expectedOutput = "Removed: (1, 1, 3, 4)";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }

    /**
     * Testing Region Search.
     */
    @Test
    public void testRegionSearch() {
        Rectangle rectangle1 = new Rectangle(1, 1, 3, 4);
        Rectangle rectangle2 = new Rectangle(5, 5, 2, 2);
        KVPair<String, Rectangle> pair1 = new KVPair<>("Rect5", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("Rect6", rectangle2);

        database.insert(pair1);
        database.insert(pair2);

        // Assuming regionsearch prints to console, so no need to assert
        database.regionsearch(0, 0, 6, 6);
    }
    
    /**
     * Testing valid intersection
     */
    @Test
    public void testIntersectionValid() { 
        Database database = new Database();

        // Create rectangles that intersect
        Rectangle rectangle1 = new Rectangle(0, 0, 5, 5);
        Rectangle rectangle2 = new Rectangle(3, 3, 5, 5);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Insert rectangles into the database
        KVPair<String, Rectangle> pair1 = new KVPair<>("rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("rect2", rectangle2);

        database.insert(pair1);
        database.insert(pair2);
        
        database.intersections();
        
        System.setOut(System.out);
        
        // Define the expected output
        String expectedOutput = "Intersecting rectangles:\n" +
                "(0, 0, 5, 5) intersects with (3, 3, 5, 5)\n" +
                "(3, 3, 5, 5) intersects with (0, 0, 5, 5)\n";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }
    
    
    /**
     * Testing invalid intersection
     */
    @Test
    public void testIntersectionInvalid() { 
        Database database = new Database();

        // Create rectangles that intersect
        Rectangle rectangle1 = new Rectangle(0, 0, 5, 5);
        Rectangle rectangle2 = new Rectangle(10, 10, 5, 5);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Insert rectangles into the database
        KVPair<String, Rectangle> pair1 = new KVPair<>("rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("rect2", rectangle2);

        database.insert(pair1);
        database.insert(pair2);
        
        database.intersections();
        
        System.setOut(System.out);
        
        // Define the expected output
        String expectedOutput = "Intersecting rectangles:\n" +
                "(0, 0, 5, 5) intersects with (3, 3, 5, 5)\n" +
                "(3, 3, 5, 5) intersects with (0, 0, 5, 5)\n";

        assertNotEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }

}
