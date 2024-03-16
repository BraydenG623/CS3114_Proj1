import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the skiplist
 * project class.
 * 
 * @author Ryan Kluttz
 * @author Brayden Gardner
 * @version 1.0
 * @since 2024-02-03
 */

@SuppressWarnings("unused")
public class SkipListProjectJunitTests {

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
//    @Test
//    public void testWithValidCommandsAndEmptyLines() {
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        PointsDatabase.main(new String[] { "file1.txt" });
//        String output = outContent.toString();
//        assertTrue("Expected 'Rectangle inserted' message", output.contains(
//            "Rectangle inserted: (a, 1, 0, 2, 4)"));
//        assertTrue("Expected 'Rectangle removed' message", output.contains(
//            "Rectangle removed: (a, 1, 0, 2, 4)"));
//        assertTrue("Expected 'Rectangle inserted' message", output.contains(
//            "Rectangle inserted: (b, 1, 2, 6, 6)"));
//    }


    /**
     * Tests the SkipListProject with an empty line in the input file.
     * Expects no processing for an empty line.
     */
    @Test
    public void testWithEmptyLine() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Simulate a file with an empty line
        String input = "command1\n\ncommand2";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        PointsDatabase.main(new String[] { "file.txt" });
        String output = outContent.toString();

        assertFalse("No output expected for an empty line", output.contains(
            "processed"));
    }
    
    /**
     * Test invalid file
     */
    @Test
    public void testInvalid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        PointsDatabase.main(new String[] { "" });
        String output = outContent.toString();
        assertEquals("Invalid file", output.trim()); // Expecting no output
    }

}
