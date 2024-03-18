import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuadTreeJunitTests {
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
    
    /*
     * Test insert for quad tree
     */
    @Test
    public void testQuadTreeInsert() {
        quadTree.insert("r1", 10, 10);
        quadTree.insert("r2", 15, 15);
        quadTree.insert("r3", 7, 7);
        quadTree.insert("r4", 20, 25);
        quadTree.insert("r4", 20, 12);
        quadTree.insert("r5", 6, 7);
        quadTree.insert("r12", 108, 136);
        quadTree.insert("r14", 120, 117);
        quadTree.insert("r15", 120, 117);

        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Point inserted: (r2, 15, 15)\n" +
            "Point inserted: (r3, 7, 7)\n" +
            "Point inserted: (r4, 20, 25)\n" +
            "Point inserted: (r4, 20, 12)\n" +
            "Point inserted: (r5, 6, 7)\n" +
            "Point inserted: (r12, 108, 136)\n" +
            "Point inserted: (r14, 120, 117)\n" +
            "Point inserted: (r15, 120, 117)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }
//    
//    
//    /*
//     * Test remove by name for quad tree
//     */
//    @Test
//    public void testQuadTreeRemoveName() {
//        quadTree.insert("r1", 10, 10);
//        quadTree.insert("r2", 15, 15);
//        quadTree.insert("r3", 7, 7);
//        quadTree.insert("r4", 20, 25);
//        quadTree.remove("r1");
//        quadTree.remove("r5");
//        
//        String expectedOutput = 
//            "Point inserted: (r1, 10, 10)\n" +
//            "Point inserted: (r2, 15, 15)\n" +
//            "Point inserted: (r3, 7, 7)\n" +
//            "Point inserted: (r4, 20, 25)\n";
//        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
//        
//    }
//    
//    
//    /*
//     * Test remove by name for quad tree
//     */
//    @Test
//    public void testQuadTreeRemoveValue() {
//        quadTree.insert("r1", 10, 10);
//        quadTree.insert("r2", 15, 15);
//        quadTree.insert("r3", 7, 7);
//        quadTree.insert("r4", 20, 25);
//        //quadTree.remove(10,10);
//        quadTree.remove(20,25);
//        quadTree.remove(20,50);
//        quadTree.dump();
//        
//        String expectedOutput = 
//            "Point inserted: (r1, 10, 10)\n" +
//            "Point inserted: (r2, 15, 15)\n" +
//            "Point inserted: (r3, 7, 7)\n" +
//            "Point inserted: (r4, 20, 25)\n" +
////            "Point removed: (r4, 20, 25)\n" +
////            "Point rejected: (20, 50)\n" +
//            "QuadTree dump:\n" +
//            "Node at 0, 0, 1024:\n" +
//            "(r1, 10, 10)\n" +
//            "(r2, 15, 15)\n" +
//            "(r3, 7, 7)\n" +
//            "1 quadtree nodes printed";
//        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
//    }
    
    
    /*
     * Test region search for quad tree
     */
    @Test
    public void testQuadTreeRegionSearch() {
        quadTree.insert("r1", 10, 10);
        quadTree.insert("r2", 15, 15);
        quadTree.insert("r3", 7, 7);
        quadTree.insert("r4", 20, 25);
        quadTree.regionsearch(-5, -5, 20, 20);
            
            
        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Point inserted: (r2, 15, 15)\n" +
            "Point inserted: (r3, 7, 7)\n" +
            "Point inserted: (r4, 20, 25)\n" +
            "Points intersecting region (-5, -5, 20, 20):\n" +
            "Point found: (r1, 10, 10)\n" +
            "Point found: (r3, 7, 7)\n" +
            "7 quadtree nodes visited\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    
    }
    
    /*
     * Test duplicates for empty node
     */
    @Test
    public void testEmptyNodeDuplicates() {
        quadTree.insert("r1", 10, 10);
        quadTree.insert("r2", 15, 15);
        quadTree.insert("r3", 7, 7);
        quadTree.insert("r4", 20, 25);
        quadTree.insert("big", 10, 10);
        quadTree.duplicates();
            
            
        String expectedOutput = 
            "Point inserted: (r1, 10, 10)\n" +
            "Point inserted: (r2, 15, 15)\n" +
            "Point inserted: (r3, 7, 7)\n" +
            "Point inserted: (r4, 20, 25)\n" +
            "Point inserted: (big, 10, 10)\n" +
            "Duplicate points:\n" +
            "(10, 10)\n";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }
    

    @Test
    public void testQuadTreeDump() {
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
            "Point inserted: (r1, 10, 10)\n" +
            "Point inserted: (r2, 15, 15)\n" +
            "Point inserted: (r3, 7, 7)\n" +
            "Point inserted: (r4, 20, 25)\n" +
            "Point inserted: (r4, 20, 12)\n" +
            "Point inserted: (r5, 6, 7)\n" +
            "Point inserted: (r12, 108, 136)\n" +
            "Point inserted: (r14, 120, 117)\n" +
            "Point inserted: (r15, 120, 117)\n" +
            "QuadTree dump:\n" +
            "Node at 0, 0, 1024: Internal\n" +
            "  Node at 0, 0, 512: Internal\n" +
            "    Node at 0, 0, 256: Internal\n" +
            "      Node at 0, 0, 128: Internal\n" +
            "        Node at 0, 0, 64: Internal\n" +
            "          Node at 0, 0, 32: Internal\n" +
            "            Node at 0, 0, 16: Internal\n" +
            "              Node at 0, 0, 8:\n" +
            "              (r3, 7, 7)\n" +
            "              (r5, 6, 7)\n" +
            "              Node at 8, 0, 8: Empty\n" +
            "              Node at 0, 8, 8: Empty\n" +
            "              Node at 8, 8, 8:\n" +
            "              (r1, 10, 10)\n" +
            "              (r2, 15, 15)\n" +
            "            Node at 16, 0, 16:\n" +
            "            (r4, 20, 12)\n" +
            "            Node at 0, 16, 16: Empty\n" +
            "            Node at 16, 16, 16:\n" +
            "            (r4, 20, 25)\n" +
            "          Node at 32, 0, 32: Empty\n" +
            "          Node at 0, 32, 32: Empty\n" +
            "          Node at 32, 32, 32: Empty\n" +
            "        Node at 64, 0, 64: Empty\n" +
            "        Node at 0, 64, 64: Empty\n" +
            "        Node at 64, 64, 64:\n" +
            "        (r14, 120, 117)\n" +
            "        (r15, 120, 117)\n" +
            "      Node at 128, 0, 128: Empty\n" +
            "      Node at 0, 128, 128:\n" +
            "      (r12, 108, 136)\n" +
            "      Node at 128, 128, 128: Empty\n" +
            "    Node at 256, 0, 256: Empty\n" +
            "    Node at 0, 256, 256: Empty\n" +
            "    Node at 256, 256, 256: Empty\n" +
            "  Node at 512, 0, 512: Empty\n" +
            "  Node at 0, 512, 512: Empty\n" +
            "  Node at 512, 512, 512: Empty\n" +
            "29 quadtree nodes printed";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }
    

    
    /*
     * Test duplicates for empty node
     */
    @Test
    public void testSEInstert() {
        quadTree.insert("p27", 863, 958);
        quadTree.insert("p32", 522, 1003);
        quadTree.insert("p39", 981, 953);
        quadTree.insert("p45", 596, 830);
        quadTree.insert("p51", 769, 726);
        quadTree.insert("p54", 1010, 658);
        quadTree.insert("p67", 954, 651);
        quadTree.insert("p80", 551, 621);
        quadTree.insert("p88", 755, 805);
        quadTree.insert("p89", 831, 765);
        quadTree.insert("p94", 568, 734);
        quadTree.insert("p95", 748, 996);
        quadTree.insert("p96", 887, 685);
        quadTree.insert("p97", 968, 577);
        quadTree.insert("byName", 741, 611);
        quadTree.insert("byName", 670, 698);
        quadTree.insert("byName", 850, 658);
        quadTree.dump();
            
            
        String expectedOutput = 
            "Point inserted: (p27, 863, 958)\n" +
            "Point inserted: (p32, 522, 1003)\n" +
            "Point inserted: (p39, 981, 953)\n" +
            "Point inserted: (p45, 596, 830)\n" +
            "Point inserted: (p51, 769, 726)\n" +
            "Point inserted: (p54, 1010, 658)\n" +
            "Point inserted: (p67, 954, 651)\n" +
            "Point inserted: (p80, 551, 621)\n" +
            "Point inserted: (p88, 755, 805)\n" +
            "Point inserted: (p89, 831, 765)\n" +
            "Point inserted: (p94, 568, 734)\n" +
            "Point inserted: (p95, 748, 996)\n" +
            "Point inserted: (p96, 887, 685)\n" +
            "Point inserted: (p97, 968, 577)\n" +
            "Point inserted: (byName, 741, 611)\n" +
            "Point inserted: (byName, 670, 698)\n" +
            "Point inserted: (byName, 850, 658)\n" +
            "QuadTree dump:\n" +
            "Node at 0, 0, 1024: Internal\n" +
            "  Node at 0, 0, 512: Empty\n" +
            "  Node at 512, 0, 512: Empty\n" +
            "  Node at 0, 512, 512: Empty\n" +
            "  Node at 512, 512, 512: Internal\n" +
            "    Node at 512, 512, 256: Internal\n" +
            "      Node at 512, 512, 128:\n" +
            "      (p80, 551, 621)\n" +
            "      Node at 640, 512, 128:\n" +
            "      (byName, 741, 611)\n" +
            "      Node at 512, 640, 128:\n" +
            "      (p94, 568, 734)\n" +
            "      Node at 640, 640, 128:\n" +
            "      (byName, 670, 698)\n" +
            "    Node at 768, 512, 256: Internal\n" +
            "      Node at 768, 512, 128: Empty\n" +
            "      Node at 896, 512, 128:\n" +
            "      (p97, 968, 577)\n" +
            "      Node at 768, 640, 128: Internal\n" +
            "        Node at 768, 640, 64: Empty\n" +
            "        Node at 832, 640, 64:\n" +
            "        (p96, 887, 685)\n" +
            "        (byName, 850, 658)\n" +
            "        Node at 768, 704, 64:\n" +
            "        (p51, 769, 726)\n" +
            "        (p89, 831, 765)\n" +
            "        Node at 832, 704, 64: Empty\n" +
            "      Node at 896, 640, 128:\n" +
            "      (p54, 1010, 658)\n" +
            "      (p67, 954, 651)\n" +
            "    Node at 512, 768, 256: Internal\n" +
            "      Node at 512, 768, 128:\n" +
            "      (p45, 596, 830)\n" +
            "      Node at 640, 768, 128:\n" +
            "      (p88, 755, 805)\n" +
            "      Node at 512, 896, 128:\n" +
            "      (p32, 522, 1003)\n" +
            "      Node at 640, 896, 128:\n" +
            "      (p95, 748, 996)\n" +
            "    Node at 768, 768, 256:\n" +
            "    (p27, 863, 958)\n" +
            "    (p39, 981, 953)\n" +
            "25 quadtree nodes printed";
        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
    }
    
//    /*
//     * Test insert for quad tree
//     */
//    @Test
//    public void testRemoveAll() {
//        quadTree.insert("r1", 10, 10);
//        quadTree.insert("r2", 15, 15);
//        quadTree.insert("r3", 7, 7);
//        quadTree.insert("r4", 20, 25);
//        quadTree.insert("r4", 20, 12);
//        quadTree.insert("r5", 6, 7);
//        quadTree.insert("r12", 108, 136);
//        quadTree.insert("r14", 120, 117);
//        quadTree.insert("r15", 120, 117);
//        quadTree.remove("r1");
//        quadTree.remove("r2");
//        quadTree.remove("r3");
//        quadTree.remove("r4");
//        quadTree.remove("r4");
//        quadTree.remove("r5");
//        quadTree.remove("r12");
//        quadTree.remove("r14");
//        quadTree.remove("r15");
//        quadTree.remove("r16");
//        quadTree.dump();
//
//        String expectedOutput = 
//            "Point inserted: (r1, 10, 10)\n" +
//            "Point inserted: (r2, 15, 15)\n" +
//            "Point inserted: (r3, 7, 7)\n" +
//            "Point inserted: (r4, 20, 25)\n" +
//            "Point inserted: (r4, 20, 12)\n" +
//            "Point inserted: (r5, 6, 7)\n" +
//            "Point inserted: (r12, 108, 136)\n" +
//            "Point inserted: (r14, 120, 117)\n" +
//            "Point inserted: (r15, 120, 117)\n";
//        assertEquals(expectedOutput.trim().replace("\r", ""), outContent.toString().trim().replace("\r", ""));
//    }
    
    
}
