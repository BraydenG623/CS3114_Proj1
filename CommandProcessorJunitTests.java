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
    private final ByteArrayOutputStream outContent =
        new ByteArrayOutputStream();
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
        // Assuming CommandProcessor can be instantiated without arguments
        // or you can pass a real or mock Database object if needed
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
     * Test the insertion of a rectangle
     * with valid parameters.
     * Expected to print the confirmation
     * message for the rectangle insertion.
     */
    @Test
    public void testInsertWithValidInput() {
        String commandLine = "insert rect1 10 20 30 40";

        ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));

        processor.processor(commandLine);

        System.setOut(System.out);

        String expectedOutput = "Rectangle inserted: (rect1, 10, 20, 30, 40)";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent1
            .toString().trim().replace("\r", ""));

    }


    /**
     * Test the insertion of a rectangle
     * with invalid parameters.
     * Expected to print an error message
     * indicating the rejection of the
     * rectangle.
     */
    @Test
    public void testInsertWithInvalidInput() {
        String commandLine = "insert rect1 -10 -20 0 40";

        processor.processor(commandLine);

        // Normalize newlines in the actual output
        String actual = outContent.toString().replace("\r\n", "\n").replace(
            "\r", "\n");

        assertEquals("Expected console output for invalid insert command",
            "Rectangle rejected: (rect1, -10, -20, 0, 40)\n", actual);
    }


    /**
     * Test the removal of a rectangle by
     * its name when the rectangle exists.
     * Expected to print messages
     * confirming the insertion and subsequent
     * removal of the rectangle.
     */
    @Test
    public void testRemoveByNameValid() {
        String commandLine1 = "insert rectName 10 20 30 40";
        processor.processor(commandLine1);
        String commandLine2 = "remove rectName";
        processor.processor(commandLine2);
        assertOutputEquals("Rectangle inserted: (rectName, 10, 20, 30, 40)\n"
            + "Rectangle removed: (rectName, 10, 20, 30, 40)\n");
    }


    /**
     * Test the removal of a rectangle by a name that does not exist in the
     * database.
     * Expected to print an error message indicating that the rectangle was not
     * removed.
     */
    @Test
    public void testRemoveByNameInvalid() {
        String commandLine = "remove unknownName";
        processor.processor(commandLine);
        assertOutputEquals("Rectangle not removed: unknownName\n");
    }


    /**
     * Test the removal of a rectangle by its coordinates when the rectangle
     * exists.
     * Expected to print messages confirming the insertion and subsequent
     * removal of the rectangle.
     */
    @Test
    public void testRemoveByCoordinatesValid() {
        String commandLine1 = "insert rectName 10 20 30 40";
        processor.processor(commandLine1);
        String commandLine2 = "remove 10 20 30 40";
        processor.processor(commandLine2);
        assertOutputEquals("Rectangle inserted:"
            + " (rectName, 10, 20, 30, 40)\nRectangle removed:"
            + " (rectName, 10, 20, 30, 40)\n");
    }


    /**
     * Test the region search for rectangles
     * that intersect with a specified
     * region.
     * Expected to print messages confirming the insertion of the rectangle and
     * the results of the region search.
     */
    @Test
    public void testRegionSearchValid() {
        String commandLine1 = "insert rectName 5 5 10 10";
        processor.processor(commandLine1);
        String commandLine = "regionsearch 5 5 40 40";
        processor.processor(commandLine);
        assertOutputEquals("Rectangle inserted: (rectName, 5, 5, 10, 10)\n"
            + "Rectangles intersecting region (5, 5, 40, 40):\n"
            + "(rectName, 5, 5, 10, 10)\n");
    }


    /**
     * Test the search for rectangles by a valid name.
     * Expected to print messages confirming
     * the insertion of the rectangle and the results of the search.
     */
    @Test
    public void testSearchValid() {
        String commandLine1 = "insert rectName 0 0 5 5";
        processor.processor(commandLine1);
        String commandLine2 = "search rectName";
        processor.processor(commandLine2);
        assertOutputEquals("Rectangle inserted: (rectName, 0, 0, 5, 5)\r\n"
            + "Rectangles found:\r\n" + "(rectName, 0, 0, 5, 5)\n");
    }


    /**
     * Test the dump functionality of the database.
     * Expected to print the state of the database.
     */
    @Test
    public void testDump() {
        String commandLine = "dump";
        processor.processor(commandLine);
        assertOutputStartsWith("SkipList dump:\n");
    }


    /**
     * Test the intersections functionality by checking
     * for intersections between multiple rectangles.
     * Expected to print the pairs of rectangles
     * that intersect with each other.
     */
    @Test
    public void testIntersectionFromEx() {
        String cmd1 = "insert r1 10 10 5 5";
        String cmd2 = "insert r2 15 15 5 5";
        String cmd3 = "insert r3 7 7 10 10";
        String cmd4 = "intersections";

        processor.processor(cmd1);
        processor.processor(cmd2);
        processor.processor(cmd3);

        ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));

        processor.processor(cmd4);

        System.setOut(System.out);

        String expectedOutput = "Intersection pairs:\r\n"
            + "(r1, 10, 10, 5, 5 | r3, 7, 7, 10, 10)\r\n"
            + "(r2, 15, 15, 5, 5 | r3, 7, 7, 10, 10)\r\n"
            + "(r3, 7, 7, 10, 10 | r1, 10, 10, 5, 5)\r\n"
            + "(r3, 7, 7, 10, 10 | r2, 15, 15, 5, 5)";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent1
            .toString().trim().replace("\r", ""));

    }


    /**
     * Asserts that the captured output from the System.out exactly matches the
     * expected string.
     * This method normalizes newline characters before comparison to handle
     * differences
     * between operating systems.
     * 
     * @param expected
     *            The expected string to compare against the captured output.
     */
    private void assertOutputEquals(String expected) {
        assertEquals(normalizeNewlines(expected), normalizeNewlines(outContent
            .toString()));
        outContent.reset();
    }


    /**
     * Asserts that the captured output from the System.out starts with the
     * expected string.
     * This method is useful for testing that the beginning of a message is
     * correct when the
     * entire output is either too long or contains variable content.
     * 
     * @param expectedStart
     *            The expected start string of the captured output.
     */
    private void assertOutputStartsWith(String expectedStart) {
        String actual = normalizeNewlines(outContent.toString());
        assertTrue("Expected to start with: " + expectedStart + ", but was: "
            + actual, actual.startsWith(normalizeNewlines(expectedStart)));
        outContent.reset();
    }


    /**
     * Normalizes newline characters within a
     * given input string to a consistent
     * format (\n).
     * This method ensures consistent
     * assertions across different operating
     * systems
     * that may use \r\n (Windows) or \r
     * (old macOS) as newline characters.
     * 
     * @param input
     *            The input string with
     *            potential mixed newline characters.
     * @return The normalized string with
     *         consistent newline characters (\n).
     */
    private String normalizeNewlines(String input) {
        return input.replace("\r\n", "\n").replace("\r", "\n");
    }


    /**
     * Test the 'dump' command processing.
     * This test simulates the 'dump' command and verifies the expected output,
     * assuming that the database starts empty and the dump command
     * outputs the state of the database accordingly.
     */
    @Test
    public void testDumpCommand() {
        String commandLine = "dump";

        processor.processor(commandLine);

        // Assuming the dump command outputs "SkipList dump:" followed by
        // the state of the database. Adjust the expected output based on
        // your actual dump method's implementation.
        String expectedOutputStart = "SkipList dump:";

        assertTrue("Expected output to start with 'SkipList dump:'", outContent
            .toString().trim().startsWith(expectedOutputStart));
    }


    /**
     * Test processing of a typo in the 'dump' command that results in it being
     * unrecognized.
     * This test simulates a typo or incorrect command similar to 'dump' and
     * verifies that the output matches the expected result for an unrecognized
     * command.
     */
    @Test
    public void testDumpCommandTypo() {
        // Simulate a typo in the 'dump' command
        String typoCommand = "dumpp";

        // Process the typo command
        processor.processor(typoCommand);

        // Verify that the output indicates the command is unrecognized
        String expectedOutput = "Unrecognized command.";
        assertEquals(
            "Expected 'Unrecognized command.' output for typo "
            + "in 'dump' command",
            expectedOutput.trim(), outContent.toString().trim());
    }
    
    
    /**
     * Test arr.length not 6 for insert
     */
    @Test
    public void testInvalidInsert() {
        String command = "insert rect 0 0 10";
        processor.processor(command);
       
        assertTrue("Expected insert failure message", outContent.toString()
            .contains("Incorrect number of arguments for insert command."));
    }
    
    /**
     * Test wrong inputs for remove
     */
    @Test
    public void testInvalidRemove() {
        String command = "insert rect 0 0 10 10";
        String command2 = "remove 0 0 10";
        processor.processor(command);
        processor.processor(command2);
       
        assertTrue("Expected remove failure message", outContent.toString()
            .contains("Incorrect number of arguments for remove command."));
    }
    
    /**
     * Test arr.length not 6 for insert
     */
    @Test
    public void testInvalidRegionSearch() {
        String command = "regionsearch 0 0 10";
        processor.processor(command);
       
        assertTrue("Expected region search failure message", 
            outContent.toString()
            .contains("Invalid command format for regionsearch."));
    }
    
    /**
     * Test arr.length not 6 for insert
     */
    @Test
    public void testInvalidSearch() {
        String command = "insert rect 0 0 10 10";
        String command2 = "search";
        processor.processor(command);
        processor.processor(command2);
        
        assertTrue("Expected search failure message", outContent.toString()
            .contains("Search command requires a name parameter."));
    }
    
    /**
     * Test arr.length not 6 for insert
     */
    @Test
    public void testInvalidDump() {
        String command = "insert rect 0 0 10 10";
        String command2 = "dumpp";
        processor.processor(command);
        processor.processor(command2);
        
        assertTrue("Expected dump failure message", outContent.toString()
            .contains("Unrecognized command."));
    }

    /**
     * Captures and returns console output during the test.
     * This is a placeholder for an actual method to capture console output.
     * 
     * @return The console output as a String.
     */
    @SuppressWarnings("unused")
    private String captureOutput() {
        // Implement capturing of System.out here
        return "Assume this string is captured output from System.out";
    }







}