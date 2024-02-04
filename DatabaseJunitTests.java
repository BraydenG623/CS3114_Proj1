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
     * Test for inserting valid rectangle
     * with negative coordinates.
     */
    @Test
    public void testInsertNegCordRectangle() {
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

        String expectedOutput = "Removed: Rect3";

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
     * Testing regionsearch with rectangles inside the specified region
     */
    @Test
    public void testRegionSearchInsideRegion() {
        Database database1 = new Database();

        // Insert rectangles inside the specified region
        database1.insert(new KVPair<>("Rect1", new Rectangle(2, 2, 3, 3)));  // Inside
        database1.insert(new KVPair<>("Rect2", new Rectangle(5, 5, 2, 2)));  // Inside

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Perform region search
        database1.regionsearch(1, 1, 6, 6);

        String expectedOutput = "Rectangles in the region (1, 1, 6, 6):\n" +
                                "(2, 2, 3, 3)\n" +
                                "(5, 5, 2, 2)\n";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
                .toString().trim().replace("\r", ""));
    }

    /**
     * Testing regionsearch with rectangles outside the specified region
     */
    @Test
    public void testRegionSearchOutsideRegion() {
        Database database2 = new Database();

        // Insert rectangles outside the specified region
        database2.insert(new KVPair<>("Rect1", new Rectangle(8, 8, 3, 3)));  // Outside
        database2.insert(new KVPair<>("Rect2", new Rectangle(0, 0, 1, 1)));  // Outside

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Perform region search
        database2.regionsearch(1, 1, 6, 6);

        String expectedOutput = "Rectangles in the region (1, 1, 6, 6):\n";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
                .toString().trim().replace("\r", ""));
    }
    
    /**
     * Testing valid intersection
     */
    @Test
    public void testIntersectionValid() { 
        Database database3 = new Database();

        // Create rectangles that intersect
        Rectangle rectangle1 = new Rectangle(0, 0, 5, 5);
        Rectangle rectangle2 = new Rectangle(3, 3, 5, 5);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Insert rectangles into the database
        KVPair<String, Rectangle> pair1 = new KVPair<>("rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("rect2", rectangle2);

        database3.insert(pair1);
        database3.insert(pair2);
        
        database3.intersections();
        
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
        Database database4 = new Database();

        // Create rectangles that intersect
        Rectangle rectangle1 = new Rectangle(0, 0, 5, 5);
        Rectangle rectangle2 = new Rectangle(10, 10, 5, 5);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Insert rectangles into the database
        KVPair<String, Rectangle> pair1 = new KVPair<>("rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("rect2", rectangle2);

        database4.insert(pair1);
        database4.insert(pair2);
        
        database4.intersections();
        
        System.setOut(System.out);
        
        // Define the expected output
        String expectedOutput = "Intersecting rectangles:\n" +
                "(0, 0, 5, 5) intersects with (3, 3, 5, 5)\n" +
                "(3, 3, 5, 5) intersects with (0, 0, 5, 5)\n";

        assertNotEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }
    
    /**
     * Test for removing a non-existent rectangle by name.
     */
    @Test
    public void testRemoveNonExistentByName() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.remove("NonExistentRect");

        System.setOut(System.out);

        String expectedOutput = "Rectangle with name 'NonExistentRect' not found.";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
                .toString().trim().replace("\r", ""));
    }
    
    /**
     * Test for removing a non-existent rectangle by coordinates.
     */
    @Test
    public void testRemoveNonExistentByCoordinates() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.remove(1, 1, 3, 4);

        System.setOut(System.out);

        String expectedOutput = "Rectangle with coordinates (1, 1, 3, 4) not found.";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
                .toString().trim().replace("\r", ""));
    }
    
    /**
     * Test for dumping an empty skip list.
     */
    @Test
    public void testDumpEmptySkipList() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.dump();

        System.setOut(System.out);

        String expectedOutput = "SkipList dump:\n" +
                "Node has depth 1, Value null\n" + "SkipList size is: 0";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
                .toString().trim().replace("\r", ""));
    }
    
    /**
     * Test for dumping a skip list with multiple nodes.
     */
    @Test
    public void testDumpSkipListWithNodes() {
        Rectangle rectangle1 = new Rectangle(1, 1, 3, 4);
        Rectangle rectangle2 = new Rectangle(5, 5, 2, 2);

        KVPair<String, Rectangle> pair1 = new KVPair<>("Rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("Rect2", rectangle2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.insert(pair1);
        database.insert(pair2);

        database.dump();

        System.setOut(System.out);

        assertNotEquals(null,outContent.toString());
    }
    
    /**
     * Test for the case where two rectangles don't intersect.
     */
    @Test
    public void testNonIntersectingRectangles() {
        Database database5 = new Database();

        // Create rectangles that don't intersect
        Rectangle rectangle1 = new Rectangle(0, 0, 2, 2);
        Rectangle rectangle2 = new Rectangle(3, 3, 2, 2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Insert rectangles into the database
        KVPair<String, Rectangle> pair1 = new KVPair<>("rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("rect2", rectangle2);

        database5.insert(pair1);
        database5.insert(pair2);

        database5.intersections();

        System.setOut(System.out);

        // Define the expected output
        String expectedOutput = "Intersecting rectangles:\n";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
                .toString().trim().replace("\r", ""));
    }


}
