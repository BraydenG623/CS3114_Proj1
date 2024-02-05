import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; // Make sure this line is included




public class CommandProcessorJunitTests {
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
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        processor.processor(commandLine);
        
        System.setOut(System.out);

        String expectedOutput = "Rectangle inserted: (rect1, 10, 20, 30, 40)";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));

    }


    @Test
    public void testInsertWithInvalidInput() {
        String commandLine = "insert rect1 -10 -20 0 40"; 
        
        processor.processor(commandLine);

        // Normalize newlines in the actual output
        String actual = outContent.toString().replace("\r\n", "\n").replace(
            "\r", "\n");
  
        assertEquals("Expected console output for invalid insert command",
            "Rectangle rejected: (rect1, -10, -20, 0, 40)\n",
            actual);
    }
    
    @Test
    public void testRemoveByNameValid() {
        String commandLine1="insert rectName 10 20 30 40";
        processor.processor(commandLine1);
        String commandLine2 = "remove rectName";
        processor.processor(commandLine2);
        assertOutputEquals("Rectangle inserted: (rectName, 10, 20, 30, 40)\nRemoved: rectName\n");
    }

    @Test
    public void testRemoveByNameInvalid() {
        String commandLine = "remove unknownName";
        processor.processor(commandLine);
        assertOutputEquals("Rectangle with name 'unknownName' not found.\n");
    }

    @Test
    public void testRemoveByCoordinatesValid() {
        String commandLine1="insert rectName 10 20 30 40";
        processor.processor(commandLine1);
        String commandLine2 = "remove 10 20 30 40";
        processor.processor(commandLine2);
        assertOutputEquals("Rectangle inserted: (rectName, 10, 20, 30, 40)\nRemoved: (10, 20, 30, 40)\n");
    }

    @Test
    public void testRegionSearchValid() {
        String commandLine1="insert rectName 5 5 10 10";
        processor.processor(commandLine1);
        String commandLine = "regionsearch 5 5 40 40";
        processor.processor(commandLine);
        assertOutputEquals("Rectangle inserted: (rectName, 5, 5, 10, 10)\n"
            + "Rectangles in the region (5, 5, 40, 40):\n"+"(5, 5, 10, 10)\n");
    }


    @Test
    public void testSearchValid() {
        String commandLine1="insert rectName 0 0 5 5";
        processor.processor(commandLine1);
        String commandLine2 = "search rectName";
        processor.processor(commandLine2);
        assertOutputEquals("Rectangle inserted: (rectName, 0, 0, 5, 5)\r\n"
            + "Rectangles with name 'rectName':\r\n"
            + "(0, 0, 5, 5)\n");
    }

    @Test
    public void testDump() {
        String commandLine = "dump";
        processor.processor(commandLine);
        assertOutputStartsWith("SkipList dump:\n");
    }
    
    @Test
    public void testIntersectionFromEx() {
        String cmd1 = "insert r1 10 10 5 5";
        String cmd2 = "insert r2 15 15 5 5";
        String cmd3 = "insert r3 7 7 10 10";
        String cmd4 = "intersections";
       
        processor.processor(cmd1);
        processor.processor(cmd2);
        processor.processor(cmd3);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        processor.processor(cmd4);
        
        System.setOut(System.out);

        String expectedOutput = "Intersection pairs:\r\n"
            + "(r1, 10, 10, 5, 5) | (r3, 7, 7, 10, 10)\r\n"
            + "(r2, 15, 15, 5, 5) | (r3, 7, 7, 10, 10)\r\n"
            + "(r3, 7, 7, 10, 10) | (r1, 10, 10, 5, 5)\r\n"
            + "(r3, 7, 7, 10, 10) | (r2, 15, 15, 5, 5)";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
        
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