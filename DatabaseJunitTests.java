import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
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

    private final ByteArrayOutputStream outContent =
        new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Database database;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        database = new Database();
    }


    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    public void testInsertValidPoint() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        String expectedOutput = "Point inserted: (r1, 10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", "").trim().replace(
            "\r", ""), outContent.toString().trim().replace("\r", "").trim()
                .replace("\r", ""));
    }


    @Test
    public void testInsertInvalidPoint() {
        database.insert(new KVPair<>("r1", new PointInt(-10, -10)));
        String expectedOutput = "Point rejected: (r1, -10, -10)\n";
        assertEquals(expectedOutput.trim().replace("\r", "").trim().replace(
            "\r", ""), outContent.toString().trim().replace("\r", "").trim()
                .replace("\r", ""));
    }


    @Test
    public void testRemoveByName() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.remove("r1");
        String expectedOutput = "Point inserted: (r1, 10, 10)\n"
            + "Point removed: (r1, 10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }


    @Test
    public void testRemoveByCoordinates() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.insert(new KVPair<>("r2", new PointInt(5, 6)));
        database.remove(10, 10);
        String expectedOutput = "Point inserted: (r1, 10, 10)\n"
            + "Point inserted: (r2, 5, 6)\n" + "Point removed: (r1, 10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }


    @Test
    public void testRegionSearch() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.regionsearch(5, 5, 10, 10);
        String expectedOutput = "Point inserted: (r1, 10, 10)\n"
            + "Points intersecting region (5, 5, 10, 10):\n"
            + "Point found: (r1, 10, 10)\n" + "1 quadtree nodes visited\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }


    @Test
    public void testSearch() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.search("r1");
        String expectedOutput = "Point inserted: (r1, 10, 10)\n" + "Found\n"
            + "(r1, 10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }


    @Test
    public void testDuplicates() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.insert(new KVPair<>("r2", new PointInt(10, 10)));
        database.duplicates();
        String expectedOutput = "Point inserted: (r1, 10, 10)\n"
            + "Point inserted: (r2, 10, 10)\n" + "Duplicate points:\n"
            + "(10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }

}
