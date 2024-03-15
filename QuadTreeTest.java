import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuadTreeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private QuadTree quadTree;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        quadTree = new QuadTree();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testInsertAndDump() {
        quadTree.insert("r1", 10, 10);
        quadTree.insert("r2", 15, 15);
        quadTree.insert("r3", 7, 7);
        quadTree.insert("r4", 20, 25);
        quadTree.insert("r4", 20, 12);
        quadTree.insert("r5", 6, 7);
        quadTree.insert("r12", 108, 136);
        quadTree.insert("r14", 120, 117);
        quadTree.insert("r15", 120, 117);
        quadTree.dump();

        String expectedOutput = 
            "QuadTree dump:\n" +
            "Node at 0, 0, 1024: Internal\n" +
            "     Node at 0, 0, 512: Internal\n" +
            "          Node at 0, 0, 256: Internal\n" +
            "               Node at 0, 0, 128: Internal\n" +
            "                    Node at 0, 0, 64: Internal\n" +
            "                         Node at 0, 0, 32: Internal\n" +
            "                              Node at 0, 0, 16: Internal\n" +
            "                                   Node at 0, 0, 8:\n" +
            "                                   (r3, 7, 7)\n" +
            "                                   (r5, 6, 7)\n" +
            "                                   Node at 8, 0, 8: Empty\n" +
            "                                   Node at 0, 8, 8: Empty\n" +
            "                                   Node at 8, 8, 8:\n" +
            "                                   (r1, 10, 10)\n" +
            "                                   (r2, 15, 15)\n" +
            "                              Node at 16, 0, 16: Empty\n" +
            "                              Node at 0, 16, 16: Empty\n" +
            "                              Node at 16, 16, 16:\n" +
            "                              (r4, 20, 25)\n" +
            "                         Node at 32, 0, 32: Empty\n" +
            "                         Node at 0, 32, 32: Empty\n" +
            "                         Node at 32, 32, 32: Empty\n" +
            "                    Node at 64, 0, 64: Empty\n" +
            "                    Node at 0, 64, 64: Empty\n" +
            "                    Node at 64, 64, 64:\n" +
            "                    (r15, 120, 117)\n" +
            "                    (r14, 120, 117)\n" +
            "               Node at 128, 0, 128: Empty\n" +
            "               Node at 0, 128, 128:\n" +
            "               (r12, 108, 136)\n" +
            "               Node at 128, 128, 128: Empty\n" +
            "          Node at 256, 0, 256: Empty\n" +
            "          Node at 0, 256, 256: Empty\n" +
            "          Node at 256, 256, 256: Empty\n" +
            "     Node at 512, 0, 512: Empty\n" +
            "     Node at 0, 512, 512: Empty\n" +
            "     Node at 512, 512, 512: Empty\n" +
            "29 quadtree nodes printed\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
