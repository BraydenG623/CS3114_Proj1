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

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
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
        assertEquals(expectedOutput.trim().replace("\r", "").trim().replace("\r", ""), outContent.toString().trim().replace("\r", "").trim().replace("\r", ""));
    }

    @Test
    public void testInsertInvalidPoint() {
        database.insert(new KVPair<>("r1", new PointInt(-10, -10)));
        String expectedOutput = "Point rejected: (r1, -10, -10)\n";
        assertEquals(expectedOutput.trim().replace("\r", "").trim().replace("\r", ""), outContent.toString().trim().replace("\r", "").trim().replace("\r", ""));
    }

    @Test
    public void testRemoveByName() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.remove("r1");
        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Point removed: (r1, 10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    @Test
    public void testRemoveByCoordinates() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.insert(new KVPair<>("r2", new PointInt(5, 6)));
        database.remove(10, 10);
        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Point inserted: (r2, 5, 6)\n" +
            "Point removed: (r1, 10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    @Test
    public void testRegionSearch() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.regionsearch(5, 5, 10, 10);
        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Points intersecting region (5, 5, 10, 10):\n" +
            "Point found: (r1, 10, 10)\n" +
            "1 quadtree nodes visited\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    @Test
    public void testSearch() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.search("r1");
        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Found\n" +
            "(r1, 10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }

    @Test
    public void testDuplicates() {
        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
        database.insert(new KVPair<>("r2", new PointInt(10, 10)));
        database.duplicates();
        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Point inserted: (r2, 10, 10)\n" +
            "Duplicate points:\n" +
            "(10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }
    
//    @Test
//    public void testDump() {
//        database.insert(new KVPair<>("r1", new PointInt(10, 10)));
//        database.insert(new KVPair<>("r2", new PointInt(15, 15)));
//        database.insert(new KVPair<>("r3", new PointInt(7, 7)));
//        database.insert(new KVPair<>("r4", new PointInt(20, 25)));
//        database.insert(new KVPair<>("r4", new PointInt(20, 12)));
//        database.insert(new KVPair<>("r5", new PointInt(6, 7)));
//        database.insert(new KVPair<>("r12", new PointInt(108, 136)));
//        database.insert(new KVPair<>("r14", new PointInt(120, 117)));
//        database.insert(new KVPair<>("r15", new PointInt(120, 117)));
//        database.dump();
//        String expectedOutput = 
//            "Point inserted: (r1, 10, 10)\n" +
//            "Point inserted: (r2, 15, 15)\n" +
//            "Point inserted: (r3, 7, 7)\n" +
//            "Point inserted: (r4, 20, 25)\n" +
//            "Point inserted: (r4, 20, 12)\n" +
//            "Point inserted: (r5, 6, 7)\n" +
//            "Point inserted: (r12, 108, 136)\n" +
//            "Point inserted: (r14, 120, 117)\n" +
//            "Point inserted: (r15, 120, 117)\n" +
//            "SkipList dump:\n" +
//            "Node has depth 3, Value (null)\n" +
//            "Node has depth 2, Value (r1, 10, 10)\n" +
//            "Node has depth 3, Value (r12, 108, 136)\n" +
//            "Node has depth 3, Value (r14, 120, 117)\n" +
//            "Node has depth 1, Value (r15, 120, 117)\n" +
//            "Node has depth 2, Value (r2, 15, 15)\n" +
//            "Node has depth 2, Value (r3, 7, 7)\n" +
//            "Node has depth 2, Value (r4, 20, 12)\n" +
//            "Node has depth 3, Value (r4, 20, 25)\n" +
//            "Node has depth 2, Value (r5, 6, 7)\n" +
//            "SkipList size is: 9\n" +
//            "QuadTree dump:\n" +
//            "Node at 0, 0, 1024: Internal\n" +
//            "  Node at 0, 0, 512: Internal\n" +
//            "    Node at 0, 0, 256: Internal\n" +
//            "      Node at 0, 0, 128: Internal\n" +
//            "        Node at 0, 0, 64: Internal\n" +
//            "          Node at 0, 0, 32: Internal\n" +
//            "            Node at 0, 0, 16: Internal\n" +
//            "              Node at 0, 0, 8:\n" +
//            "              (r3, 7, 7)\n" +
//            "              (r5, 6, 7)\n" +
//            "              Node at 8, 0, 8: Empty\n" +
//            "              Node at 0, 8, 8: Empty\n" +
//            "              Node at 8, 8, 8:\n" +
//            "              (r1, 10, 10)\n" +
//            "              (r2, 15, 15)\n" +
//            "            Node at 16, 0, 16:\n" +
//            "            (r4, 20, 12)\n" +
//            "            Node at 0, 16, 16: Empty\n" +
//            "            Node at 16, 16, 16:\n" +
//            "            (r4, 20, 25)\n" +
//            "          Node at 32, 0, 32: Empty\n" +
//            "          Node at 0, 32, 32: Empty\n" +
//            "          Node at 32, 32, 32: Empty\n" +
//            "        Node at 64, 0, 64: Empty\n" +
//            "        Node at 0, 64, 64: Empty\n" +
//            "        Node at 64, 64, 64:\n" +
//            "        (r14, 120, 117)\n" +
//            "        (r15, 120, 117)\n" +
//            "      Node at 128, 0, 128: Empty\n" +
//            "      Node at 0, 128, 128:\n" +
//            "      (r12, 108, 136)\n" +
//            "      Node at 128, 128, 128: Empty\n" +
//            "    Node at 256, 0, 256: Empty\n" +
//            "    Node at 0, 256, 256: Empty\n" +
//            "    Node at 256, 256, 256: Empty\n" +
//            "  Node at 512, 0, 512: Empty\n" +
//            "  Node at 0, 512, 512: Empty\n" +
//            "  Node at 512, 512, 512: Empty\n" +
//            "29 quadtree nodes printed";
//        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
//    }

}