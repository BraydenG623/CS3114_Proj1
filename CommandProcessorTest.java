import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; // Make sure this line is included




public class CommandProcessorTest {
    private final ByteArrayOutputStream outContent =
        new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private CommandProcessor processor;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        // Assuming CommandProcessor can be instantiated without arguments
        // or you can pass a real or mock Database object if needed
        processor = new CommandProcessor();
    }


    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    public void testInsertWithValidInput() {
        String commandLine = "insert rect1 10 20 30 40";
        processor.processor(commandLine);
        String actual = outContent.toString().replace("\r\n", "\n").replace(
            "\r", "\n");
        assertEquals("Expected console output for valid insert command",
            "Rectangle inserted: (rect1, 10, 20, 30, 40)\n", actual);

    }


    @Test
    public void testInsertWithInvalidInput() {
        String commandLine = "insert rect1 -10 -20 0 40"; // Invalid due to
                                                           // negative
                                                           // coordinates
        processor.processor(commandLine);

        // Normalize newlines in the actual output
        String actual = outContent.toString().replace("\r\n", "\n").replace(
            "\r", "\n");

        assertEquals("Expected console output for invalid insert command",
            "Rectangle rejected: (rect1, -10, -20, 0, 40) - Rectangle has invalid properties.\n",
            actual);
    }
    
    @Test
    public void testRemoveByNameValid() {
        String commandLine = "remove rectName";
        processor.processor(commandLine);
        assertOutputEquals("Rectangle removed: (rectName)\n");
    }

    @Test
    public void testRemoveByNameInvalid() {
        String commandLine = "remove unknownName";
        processor.processor(commandLine);
        assertOutputEquals("Rectangle not removed: unknownName\n");
    }

    @Test
    public void testRemoveByCoordinatesValid() {
        String commandLine = "remove 10 20 30 40";
        processor.processor(commandLine);
        assertOutputEquals("Rectangle removed: (10, 20, 30, 40)\n");
    }

    @Test
    public void testRegionSearchValid() {
        String commandLine = "regionsearch 10 20 30 40";
        processor.processor(commandLine);
        assertOutputEquals("Rectangles intersecting region (10, 20, 30, 40):\n");
    }

    @Test
    public void testIntersections() {
        String commandLine = "intersections";
        processor.processor(commandLine);
        assertOutputEquals("Intersection pairs:\n");
    }

    @Test
    public void testSearchValid() {
        String commandLine = "search rectName";
        processor.processor(commandLine);
        assertOutputEquals("Rectangles found:\n");
    }

    @Test
    public void testDump() {
        String commandLine = "dump";
        processor.processor(commandLine);
        assertOutputStartsWith("SkipList dump:\n");
    }

    private void assertOutputEquals(String expected) {
        assertEquals(normalizeNewlines(expected), normalizeNewlines(outContent.toString()));
        outContent.reset();
    }

    private void assertOutputStartsWith(String expectedStart) {
        String actual = normalizeNewlines(outContent.toString());
        assertTrue("Expected to start with: " + expectedStart + ", but was: " + actual, actual.startsWith(normalizeNewlines(expectedStart)));
        outContent.reset();
    }

    private String normalizeNewlines(String input) {
        return input.replace("\r\n", "\n").replace("\r", "\n");
    }




}
