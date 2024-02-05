// import org.junit.After;
// import org.junit.Before;
// import org.junit.Test;
// import java.io.*;
// import static org.junit.Assert.*;
//
/// **
// * Tests the SkipListProject's main 
//functionality with simulated input.
// * This approach allows testing without 
//the need for external files,
// * making the tests more portable and suitable for environments like
// * autograders.
// */
// public class SkipListProjectTest {
//
// private final InputStream systemInBackup = System.in;
// private final PrintStream systemOutBackup = System.out;
// private ByteArrayOutputStream testOut;
//
// /**
// * Sets up the testing environment before each test.
// * Redirects the System.out to capture the output for assertion.
// */
// @Before
// public void setUp() {
// testOut = new ByteArrayOutputStream();
// System.setOut(new PrintStream(testOut));
// }
//
//
// /**
// * Restores the System.in and System.out to
//their original state after each
// * test.
// */
// @After
// public void tearDown() {
// System.setIn(systemInBackup);
// System.setOut(systemOutBackup);
// }
//
//
// /**
// * Tests the SkipListProject with an empty 
//input to simulate an empty file.
// * Expects no output from the application in this scenario.
// */
// @Test
// public void testWithEmptyInput() {
// String input = ""; // Simulate an empty file
// System.setIn(new ByteArrayInputStream(input.getBytes()));
//
// String[] args = {}; // No arguments needed
// SkipListProject.main(args);
//
// assertEquals("Expecting no output for empty input", "", getOutput()
// .trim());
// }
//
//
// /**
// * Tests the SkipListProject with valid 
//commands and empty lines between
// * commands.
// * Simulates a file content with commands 
//separated by empty lines.
// * Assertions are made based on the expected
//behavior of these commands.
// */
// @Test
// public void testWithValidCommandsAndEmptyLines() {
// String input = "insert a 1 0 2 4\n\nremove a\n";
// System.setIn(new ByteArrayInputStream(input.getBytes()));
//
// String[] args = {}; // No arguments needed
// SkipListProject.main(args);
//
// String output = getOutput();
// assertTrue("Expected the insert command to succeed", 
//output.contains(
// "Rectangle inserted: (a, 1, 0, 2, 4)"));
// assertTrue("Expected the remove command to succeed", 
//output.contains(
// "Rectangle removed: (a, 1, 0, 2, 4)"));
// }
//
//
// /**
// * Captures the output from the System.out for assertions.
// *
// * @return The captured output from the System.out as a String.
// */
// private String getOutput() {
// return testOut.toString();
// }
// }
