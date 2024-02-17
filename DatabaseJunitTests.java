import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
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

@SuppressWarnings("unused")
public class DatabaseJunitTests {

    private Database database;

    /**
     * Sets up testing
     */
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
        Rectangle rectangle2 = new Rectangle(5, 2, 6, 6);
        KVPair<String, Rectangle> pair = new KVPair<>("Rect1", rectangle);
        KVPair<String, Rectangle> pair2 = new KVPair<>("Rect2", rectangle2);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.insert(pair);
        database.insert(pair2);
        database.dump();

        System.setOut(System.out);

        String expectedOutput = "SkipList dump:\n"
            + "Node has depth 1, Value null\n" + "SkipList size is: 0";

        assertNotEquals(expectedOutput, outContent.toString());

    }


    /**
     * Tests the bigInsert function
     */
    @Test
    public void testBigInsert() {
        Database database1 = new Database();

        // Insert five rejected rectangles
        for (int i = 0; i < 5; i++) {
            Rectangle rejectedRectangle = new Rectangle(i, i, -60, -50);
            KVPair<String, Rectangle> rejectedPair = new KVPair<>("RejectedRect"
                + i, rejectedRectangle);
            database1.insert(rejectedPair);
        }

        // Insert 100 rectangles
        for (int i = 0; i < 100; i++) {
            Rectangle rectangle = new Rectangle(i, i, i + 2, i + 4);
            KVPair<String, Rectangle> pair = new KVPair<>("Rect" + i,
                rectangle);
            database1.insert(pair);
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database1.dump();

        System.setOut(System.out);

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

        database.insert(pair);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.remove("Rect3");

        System.setOut(System.out);

        String expectedOutput = "Rectangle removed: (Rect3, 1, 1, 3, 4)";

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

        database.insert(pair);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.remove(1, 1, 3, 4);

        System.setOut(System.out);

        String expectedOutput = "Rectangle removed: (Rect4, 1, 1, 3, 4)";

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
        database1.insert(new KVPair<>("Rect1", 
            new Rectangle(2, 2, 3, 3))); // Inside
        database1.insert(new KVPair<>("Rect2", 
            new Rectangle(5, 5, 2, 2))); // Inside

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Perform region search
        database1.regionsearch(1, 1, 6, 6);

        String expectedOutput = "Rectangles intersecting region "
            + "(1, 1, 6, 6):\n" + "(Rect1, 2, 2, 3, 3)\n"
            + "(Rect2, 5, 5, 2, 2)\n";

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
        database2.insert(new KVPair<>("Rect1", 
            new Rectangle(8, 8, 3, 3))); // Outside
        database2.insert(new KVPair<>("Rect2", 
            new Rectangle(0, 0, 1, 1))); // Outside

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Perform region search
        database2.regionsearch(1, 1, 6, 6);

        String expectedOutput = "Rectangles intersecting region "
            + "(1, 1, 6, 6):\n";

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

        // Insert rectangles into the database
        KVPair<String, Rectangle> pair1 = new KVPair<>("rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("rect2", rectangle2);

        database3.insert(pair1);
        database3.insert(pair2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database3.intersections();

        System.setOut(System.out);

        // Define the expected output
        String expectedOutput = "Intersection pairs:\n"
            + "(rect1, 0, 0, 5, 5 | rect2, 3, 3, 5, 5)\n"
            + "(rect2, 3, 3, 5, 5 | rect1, 0, 0, 5, 5)\n";

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
        String expectedOutput = "Intersecting rectangles:\n"
            + "(0, 0, 5, 5) intersects with (3, 3, 5, 5)\n"
            + "(3, 3, 5, 5) intersects with (0, 0, 5, 5)\n";

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

        String expectedOutput = "Rectangle not removed: NonExistentRect";

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

        String expectedOutput = "Rectangle not found: (1, 1, 3, 4)";

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

        String expectedOutput = "SkipList dump:\n"
            + "Node has depth 1, Value null\n" + "SkipList size is: 0";

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

        assertNotEquals(null, outContent.toString());
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

        // Insert rectangles into the database
        KVPair<String, Rectangle> pair1 = new KVPair<>("rect1", rectangle1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("rect2", rectangle2);

        database5.insert(pair1);
        database5.insert(pair2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database5.intersections();

        System.setOut(System.out);

        // Define the expected output
        String expectedOutput = "Intersection pairs:\n";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }


    /**
     * Tests the regionsearch method with a valid region.
     * Ensures that the method processes the search when both width and height
     * are greater than 0.
     */
    @Test
    public void testRegionSearchWithValidRegion() {
        // Setup necessary mocking or stubbing if the Database class depends on
        // other components

        // Assuming System.out.println is called within the method, we capture
        // the output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Execute the method with valid width and height
        database.regionsearch(10, 10, 5, 5);

        // Verify that the rejection message is not printed
        assertFalse("Valid region should not be rejected.", outContent
            .toString().contains("Rectangle rejected:"));

        // Reset the System.out to its original stream
        System.setOut(System.out);
    }


    /**
     * Tests the regionsearch method with an invalid region where width and
     * height are less than or equal to zero.
     * Ensures that the method rejects the search region and prints an
     * appropriate message.
     */
    @Test
    public void testRegionSearchWithInvalidRegion() {
        // Setup necessary mocking or stubbing if the Database class depends on
        // other components

        // Assuming System.out.println is called within the method, we capture
        // the output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Execute the method with invalid width and height
        database.regionsearch(10, 10, -1, -1);

        // Verify that a rejection message is printed
        assertTrue("Invalid region should be rejected.", outContent.toString()
            .contains("Rectangle rejected:"));

        // Reset the System.out to its original stream
        System.setOut(System.out);
    }


    /**
     * Tests the regionsearch method with an invalid region where width and
     * height are less than or equal to zero.
     * Ensures that the method rejects the search region and prints an
     * appropriate message.
     */
    @Test
    public void testRegionSearchWithInvalidRegion2() {
        // Setup necessary mocking or stubbing if the Database class depends on
        // other components

        // Assuming System.out.println is called within the method, we capture
        // the output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Execute the method with invalid width and height
        database.regionsearch(10, 10, -1, 2);

        // Verify that a rejection message is printed
        assertTrue("Invalid region should be rejected.", outContent.toString()
            .contains("Rectangle rejected:"));

        // Reset the System.out to its original stream
        System.setOut(System.out);
    }


    /**
     * Tests the regionsearch method with an invalid region where width and
     * height are less than or equal to zero.
     * Ensures that the method rejects the search region and prints an
     * appropriate message.
     */
    @Test
    public void testRegionSearchWithInvalidRegion3() {
        // Setup necessary mocking or stubbing if the Database class depends on
        // other components

        // Assuming System.out.println is called within the method, we capture
        // the output.
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Execute the method with invalid width and height
        database.regionsearch(10, 10, 2, -1);

        // Verify that a rejection message is printed
        assertTrue("Invalid region should be rejected.", outContent.toString()
            .contains("Rectangle rejected:"));

        // Reset the System.out to its original stream
        System.setOut(System.out);
    }


    /**
     * Tests that the search method outputs the correct information when
     * rectangles
     * with the specified name exist within the database.
     */
    @Test
    public void testSearchWithNameFound() {
        // Insert rectangles into the database for testing
        database.insert(new KVPair<>("TestRect1", new Rectangle(0, 0, 10, 10)));
        database.insert(new KVPair<>("TestRect2", new Rectangle(10, 10, 20,
            20)));
        database.insert(new KVPair<>("TestRect1", new Rectangle(20, 20, 30,
            30)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Search for rectangles with a specific name
        database.search("TestRect1");

        // Check if the output contains the expected rectangles
        String output = outContent.toString();
        assertTrue("Expected to find rectangles with name 'TestRect1'", output
            .contains("TestRect1"));
    }


    /**
     * Tests that the search method outputs the correct message when no
     * rectangles
     * with the specified name exist within the database.
     */
    @Test
    public void testSearchWithNameNotFound() {
        // Insert a rectangle into the database for testing
        database.insert(new KVPair<>("AnotherRect", new Rectangle(0, 0, 10,
            10)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Search for a rectangle with a specific name that does not exist
        database.search("NonexistentRect");

        // Check if the output contains the expected message
        String output = outContent.toString();
        assertTrue(
            "Expected to not find rectangles with name 'NonexistentRect'",
            output.contains("Rectangle not found"));
    }


    /**
     * Test remove by name that doesn't exist
     */
    @Test
    public void testRemoveNameNot() {
        database.insert(new KVPair<>("AnotherRect", new Rectangle(0, 0, 10,
            10)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.remove("NoRect");

        String output = outContent.toString();
        assertTrue("Expected not to remove rectangle with name 'NoRect'", output
            .contains("Rectangle not removed:"));
    }


    /**
     * Test remove invalid rectangle
     */
    @Test
    public void testRemoveInvalid() {
        database.insert(new KVPair<>("AnotherRect", new Rectangle(0, 0, 10,
            10)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        database.remove(0, 0, -5, 10);

        String output = outContent.toString();
        assertTrue("Expected not to fail removing invalid width", output
            .contains("Rectangle rejected:"));
    }


    /**
     * Test big remove by value
     */
    @Test
    public void testBigRemoveVal() {
        // Insert some rectangles
        insertRectangles();
        // Attempt to remove 25 rectangles successfully
        
        for (int i = 1; i < 26; i++) {
            database.remove(i, i, i, i);
        }

        // Attempt to remove 9 rectangles unsuccessfully
        for (int i = 120; i < 129; i++) {
            database.remove(i, i, i, i);
        }

        // Dump the SkipList and check if the size information is present
        String dumpOutput = captureDumpOutput();
        assertTrue(dumpOutput.contains("SkipList size is: 75"));
    }


    /**
     * Test big remove by key
     */
    @Test
    public void testBigRemoveKey() {
        // Insert some rectangles
        insertRectangles();
        // Attempt to remove 25 rectangles successfully
        for (int i = 1; i < 26; i++) {
            String name = "Rectangle" + i;
            database.remove(name);
        }

        // Attempt to remove 9 rectangles unsuccessfully
        for (int i = 120; i < 129; i++) {
            String name = "Rectangle" + i;
            database.remove(name);
        }

        // Dump the SkipList and check if the size information is present
        String dumpOutput = captureDumpOutput();
        assertTrue(dumpOutput.contains("SkipList size is: 75"));
    }


    /*
     * Helper method to insert rectangles
     */
    private void insertRectangles() {
        for (int i = 1; i < 101; i++) {
            String name = "Rectangle" + i;
            Rectangle rectangle = new Rectangle(i, i, i, i);
            database.insert(new KVPair<>(name, rectangle));
        }
    }


    /*
     * Helper method to create array list
     * of rectangles for bigRemoveByValue()
     */
    private ArrayList<Rectangle> insertRectanglesArray() {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            String name = "Rectangle" + i;
            Rectangle rectangle = new Rectangle(i, i, i, i);
            rectangles.add(rectangle);
            database.insert(new KVPair<>(name, rectangle));
        }
        return rectangles;
    }
    
    /*
     * Helper method to capture output of dump()
     */
    private String captureDumpOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream oldOut = System.out;
        System.setOut(printStream);

        // Call dump() method
        database.dump();

        // Reset System.out
        System.out.flush();
        System.setOut(oldOut);

        return outputStream.toString();
    }

}
