import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; // Make sure this line is included

/**
 * Tests for the CommandProcessor class.
 * Ensures that the processor correctly
 * handles various command inputs.
 * 
 * @author Brayden Gardner
 * @version 1.0
 */
public class CommandProcessorJunitTests {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private CommandProcessor processor;

    /**
     * Sets up the test environment before each test.
     * Redirects the system output to capture the console output for
     * verification.
     */
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        processor = new CommandProcessor();
    }

    /**
     * Restores the system output after each test.
     */
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Test the insertion of a point with valid parameters.
     */
    @Test
    public void testInsertWithValidInput() {
        String commandLine = "insert point1 10 20";
        processor.processor(commandLine);
        String expectedOutput = "Point inserted: (point1, 10, 20)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    /**
     * Test the insertion of a point with invalid parameters.
     */
    @Test
    public void testInsertWithInvalidInput() {
        String commandLine = "insert point1 -10 -20";
        processor.processor(commandLine);
        String expectedOutput = "Point rejected: (point1, -10, -20)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    /**
     * Test the removal of a point by its name when the point exists.
     */
    @Test
    public void testRemoveByNameValid() {
        processor.processor("insert point1 10 20");
        processor.processor("remove point1");
        String expectedOutput = "Point inserted: (point1, 10, 20)\nPoint removed: (point1, 10, 20)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    /**
     * Test the removal of a point by a name that does not exist in the database.
     */
    @Test
    public void testRemoveByNameInvalid() {
        processor.processor("remove unknownPoint");
        String expectedOutput = "Point rejected: unknownPoint\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    /**
     * Test the removal of a point by its coordinates when the point exists.
     */
    @Test
    public void testRemoveByCoordinatesValid() {
        processor.processor("insert point1 10 20");
        processor.processor("remove 10 20");
        String expectedOutput = "Point inserted: (point1, 10, 20)\nPoint removed: (point1, 10, 20)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    /**
     * Test the region search for points that intersect with a specified region.
     */
    @Test
    public void testRegionSearchValid() {
        processor.processor("insert point1 10 20");
        processor.processor("regionsearch 5 15 10 10");
        String expectedOutput = "Point inserted: (point1, 10, 20)\nPoints intersecting region (5, 15, 10, 10):"
            + "\nPoint found: (point1, 10, 20)\n1 quadtree nodes visited\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    /**
     * Test the search for points by a valid name.
     */
    @Test
    public void testSearchValid() {
        processor.processor("insert point1 10 20");
        processor.processor("search point1");
        String expectedOutput = "Point inserted: (point1, 10, 20)\nFound (point1, 10, 20)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    /**
     * Test the dump functionality of the database.
     */
    @Test
    public void testDump() {
        processor.processor("dump");
        assertTrue("Expected dump output", outContent.toString().trim().startsWith("SkipList dump:"));
    }

    /**
     * Test the duplicates functionality by checking for duplicate points.
     */
    @Test
    public void testDuplicates() {
        processor.processor("insert point1 10 20");
        processor.processor("insert point2 10 20");
        processor.processor("duplicates");
        String expectedOutput = "Point inserted: (point1, 10, 20)\nPoint inserted: (point2, 10, 20)\nDuplicate points:\n(10, 20)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }
}
