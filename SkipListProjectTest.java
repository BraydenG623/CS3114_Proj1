import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the Skiplist
 * project class and does that by uses txt files
 * 
 * @author Brayden Gardner
 * @version 1.0
 * @since 2024-02-15
 */
public class SkipListProjectTest {

    private final InputStream systemInBackup = System.in;
    private final PrintStream systemOutBackup = System.out;
    private ByteArrayOutputStream testOut;

    /**
     * Sets up the testing environment before each test.
     * Redirects the System.out to capture the output for assertion.
     */
    @Before
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }


    /**
     * Restores the System.in and System.out to
     * their original state after each
     * test.
     */
    @After
    public void tearDown() {
        System.setIn(systemInBackup);
        System.setOut(systemOutBackup);
    }


    /**
     * Tests the SkipListProject with an empty
     * input to simulate an empty file.
     * Expects no output from the application in this scenario.
     */
    /**
     * Tests the SkipListProject with an empty
     * input to simulate an empty file.
     * Expects no output from the application in this scenario.
     */
    @Test
    public void testWithEmptyInput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        PointsDatabase.main(new String[] { "file.txt" });
        String output = outContent.toString();
        assertEquals("", output.trim()); // Expecting no output
    }


    /**
     * Tests the SkipListProject with valid
     * commands and empty lines between
     * commands.
     * Simulates a file content with commands
     * separated by empty lines.
     * Assertions are made based on the expected
     * behavior of these commands.
     */
    @Test
    public void testWithValidCommandsAndEmptyLines() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        PointsDatabase.main(new String[] { "file1.txt" });
        String output = outContent.toString();
        assertTrue("Expected 'Rectangle inserted' message", output.contains(
            "Rectangle inserted: (a, 1, 0, 2, 4)"));
        assertTrue("Expected 'Rectangle removed' message", output.contains(
            "Rectangle removed: (a, 1, 0, 2, 4)"));
    }


    /**
     * This tests the case where a .txt file simply 
     * does not exist which will cause system output
     * to print a certain message
     */
    @Test
    public void testFileNotFound() {
        // Simulate an invalid file path as input to the main method
        String[] args = { "nonexistent_file.txt" };
        PointsDatabase.main(args);

        // Capture and assert the output to confirm the "Invalid file" message
        String output = getOutput();
        assertTrue("Expected 'Invalid file' message", output.contains(
            "Invalid file"));

    }


    /**
     * Captures the output from the System.out for assertions.
     *
     * @return The captured output from the System.out as a String.
     */
    private String getOutput() {
        return testOut.toString();
    }
}
