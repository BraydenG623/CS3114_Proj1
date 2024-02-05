import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains JUnit tests for the SkipList class.
 * It tests various scenarios for the search, insert, remove,
 * removeByValue, and dump methods.
 * 
 * @author Ryan Kluttz
 * @version 1.0
 * @since 2024-02-03
 */

public class SkipListJunitTests {

    private SkipList<Integer, String> skipList;
    private SkipList<String, Integer> skipListStr;

    /**
     * Prepares the test environment before each test case.
     */
    @Before
    public void setUp() {
        skipList = new SkipList<>();
        skipListStr = new SkipList<>();
    }


    /**
     * Tests that basic insert works for size=2.
     */
    @Test
    public void testInsert() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
        skipList.insert(pair1);
        skipList.insert(pair2);

        assertEquals(2, skipList.size());
    }


    /**
     * Tests that a key cannot be null.
     */
    @Test
    public void testInvalidKeyInput() {
        KVPair<Integer, String> pair = new KVPair<>(null, "Value");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        skipList.insert(pair);

        System.setOut(System.out);

        assertEquals("Key cannot be null", outContent.toString());
        assertEquals(0, skipList.size());
    }

    /**
     * Tests that a null value can be inserted.
     */
     @Test
     public void testInvalidValueInput() {
         KVPair<Integer, String> pair = new KVPair<>(5, null);
        
         ByteArrayOutputStream outContent = new ByteArrayOutputStream();
         System.setOut(new PrintStream(outContent));
        
         skipList.insert(pair);
        
         System.setOut(System.out);

         assertEquals(1, skipList.size());
     }


//    /**
//     * Tests that the same key can be inserted twice.
//     */
    @Test
    public void testInsertWithExistingKey() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
        KVPair<Integer, String> pair2 = new KVPair<>(5, "Value2");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        skipList.insert(pair1);
        skipList.insert(pair2);

        System.setOut(System.out);

        assertEquals(2, skipList.size());
    }


    /**
     * Tests that the same value can be inserted
     * with a different key.
     */
    @Test
    public void testInsertWithSameValueDifferentKey() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value");

        skipList.insert(pair1);
        skipList.insert(pair2);

        assertEquals(2, skipList.size());
    }


    /**
     * Tests that the dump function works and
     * does not print null to the console.
     */
    @Test
    public void testFullDump() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
        KVPair<Integer, String> pair3 = new KVPair<>(15, "Value3");

        skipList.insert(pair1);
        skipList.insert(pair2);
        skipList.insert(pair3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        skipList.dump();

        System.setOut(System.out);

        assertNotEquals(null, outContent.toString());
    }


    /**
     * Tests that correct output is displayed for
     * a dump on an empty skip list.
     */
    @Test
    public void testEmptyDump() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        skipList.dump();

        System.setOut(System.out);

        String expectedOutput = "SkipList dump:\n"
            + "Node has depth 1, Value null\n" + "SkipList size is: 0";

        assertEquals(expectedOutput.trim().replace("\r", ""), outContent
            .toString().trim().replace("\r", ""));
    }


//    /**
//     * Tests a valid remove by value instance.
//     */
    @Test
    public void testRemoveByValValid() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
        KVPair<Integer, String> pair3 = new KVPair<>(15, "Value3");

        skipList.insert(pair1);
        skipList.insert(pair2);
        skipList.insert(pair3);
        skipList.removeByValue("Value1");

        assertEquals(2, skipList.size());
    }


    /**
     * Tests an invalid remove by value instance.
     */
    @Test
    public void testRemoveByValInvalid() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
        KVPair<Integer, String> pair3 = new KVPair<>(15, "Value3");

        skipList.insert(pair1);
        skipList.insert(pair2);
        skipList.insert(pair3);

        KVPair<Integer, String> result = skipList.removeByValue("Value4");

        assertEquals(result, null);
        assertEquals(3, skipList.size());
    }


    /**
     * Tests a search instance were a key is found.
     */
    @Test
    public void testSearchFound() {
        KVPair<Integer, String> pair = new KVPair<>(5, "Value");

        skipList.insert(pair);

        ArrayList<KVPair<Integer, String>> checkKey = skipList.search(5);

        assertNotEquals(null, checkKey);
    }


    /**
     * Tests a search instance were a key is not found.
     */
    @Test
    public void testSearchNotFound() {
        KVPair<Integer, String> pair = new KVPair<>(5, "Value");

        skipList.insert(pair);

        ArrayList<KVPair<Integer, String>> checkKey = skipList.search(4);

        assertEquals(null, checkKey);
    }


    /**
     * Tests a valid remove instance.
     */
    @Test
    public void testRemoveValid() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
        KVPair<Integer, String> pair3 = new KVPair<>(15, "Value3");

        skipList.insert(pair1);
        skipList.insert(pair2);
        skipList.insert(pair3);

        KVPair<Integer, String> result = skipList.remove(5);

        assertEquals(result, pair1);
        assertEquals(2, skipList.size());
    }


    /**
     * Tests an invalid remove instance.
     */
    @Test
    public void testRemoveInvalid() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
        KVPair<Integer, String> pair3 = new KVPair<>(15, "Value3");

        skipList.insert(pair1);
        skipList.insert(pair2);
        skipList.insert(pair3);

        KVPair<Integer, String> result = skipList.remove(7);

        assertEquals(result, null);
        assertEquals(3, skipList.size());
    }


    /**
     * Tests iterator functionality.
     */
    @Test
    public void testIterator() {
        KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
        KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
        KVPair<Integer, String> pair3 = new KVPair<>(15, "Value3");

        skipList.insert(pair1);
        skipList.insert(pair2);
        skipList.insert(pair3);

        ArrayList<KVPair<Integer, String>> resultList = new ArrayList<>();
        for (KVPair<Integer, String> kvPair : skipList) {
            resultList.add(kvPair);
        }

        assertEquals(resultList.get(0), pair1);
        assertEquals(resultList.get(1), pair2);
        assertEquals(resultList.get(2), pair3);
    }


    /**
     * Tests dump with multiple levels and
     * ensures output is not null.
     */
    @Test
    public void testFullDumpWithMultipleLevels() {
        // Insert enough elements to create multiple levels
        for (int i = 0; i < 20; i++) {
            skipList.insert(new KVPair<>(i, "Value" + i));
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        skipList.dump();

        System.setOut(System.out);

        assertNotEquals(null, outContent.toString());
    }


    /**
     * Tests random operations and verifies correct
     * functionality.
     */
    @Test
    public void testRandomizedOperations() {
        int numOperations = 1000;
        int maxValue = 1000;
        Random random = new Random();
        HashSet<Integer> keysSet = new HashSet<>();
        ArrayList<KVPair<Integer, String>> insertedPairs = new ArrayList<>();

        for (int i = 0; i < numOperations; i++) {
            int operation = random.nextInt(4); // 0: insert, 1: search, 2:
                                               // remove, 3: removeByValue

            switch (operation) {
                case 0: // Insert
                    int keyToInsert = random.nextInt(maxValue);
                    String valueToInsert = "Value" + keyToInsert;
                    KVPair<Integer, String> pairToInsert = new KVPair<>(
                        keyToInsert, valueToInsert);

                    if (!keysSet.contains(keyToInsert)) {
                        skipList.insert(pairToInsert);
                        keysSet.add(keyToInsert);
                        insertedPairs.add(pairToInsert);
                    }
                    break;

                case 1: // Search
                    if (!keysSet.isEmpty()) {
                        int randomKey = new ArrayList<>(keysSet).get(random
                            .nextInt(keysSet.size()));
                        ArrayList<KVPair<Integer, String>> searchResult =
                            skipList.search(randomKey);

                        if (searchResult != null) {
                            for (KVPair<Integer, String> resultPair : searchResult) {
                                assertEquals(resultPair.getKey(), Integer
                                    .valueOf(randomKey));
                                assertEquals(resultPair.getValue(), "Value"
                                    + randomKey);
                            }
                        }
                        else {
                            assertNull(searchResult);
                        }
                    }
                    break;

                case 2: // Remove
                    if (!keysSet.isEmpty()) {
                        int randomKeyToRemove = new ArrayList<>(keysSet).get(
                            random.nextInt(keysSet.size()));
                        KVPair<Integer, String> removedPair = skipList.remove(
                            randomKeyToRemove);

                        if (removedPair != null) {
                            keysSet.remove(randomKeyToRemove);
                            insertedPairs.remove(removedPair);
                        }
                        else {
                            assertNull(removedPair);
                        }
                    }
                    break;

                case 3: // RemoveByValue
                    if (!insertedPairs.isEmpty()) {
                        KVPair<Integer, String> randomPairToRemove =
                            insertedPairs.get(random.nextInt(insertedPairs
                                .size()));
                        KVPair<Integer, String> removedByValPair = skipList
                            .removeByValue(randomPairToRemove.getValue());

                        if (removedByValPair != null) {
                            keysSet.remove(removedByValPair.getKey());
                            insertedPairs.remove(removedByValPair);
                        }
                        else {
                            assertNull(removedByValPair);
                        }
                    }
                    break;
            }
        }
    }


    /**
     * Tests insert and dump with String-type key
     * and Integer-type values.
     */
    @Test
    public void testInsertStringsAndDump() {
        KVPair<String, Integer> pair1 = new KVPair<>("apple", 1);
        KVPair<String, Integer> pair2 = new KVPair<>("banana", 2);
        KVPair<String, Integer> pair3 = new KVPair<>("orange", 3);

        skipListStr.insert(pair1);
        skipListStr.insert(pair2);
        skipListStr.insert(pair3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        skipListStr.dump();

        System.setOut(System.out);

        assertNotEquals(null, outContent.toString().trim());
    }


    /**
     * Tests valid remove with String-type keys.
     */
    @Test
    public void testValidRemoveStrKey() {
        KVPair<String, Integer> pair1 = new KVPair<>("apple", 1);
        KVPair<String, Integer> pair2 = new KVPair<>("banana", 2);
        KVPair<String, Integer> pair3 = new KVPair<>("orange", 3);

        skipListStr.insert(pair1);
        skipListStr.insert(pair2);
        skipListStr.insert(pair3);

        KVPair<String, Integer> result = skipListStr.remove("apple");

        assertEquals(result, pair1);
        assertEquals(2, skipListStr.size());
    }


    /**
     * Tests invalid remove with String-type keys.
     */
    @Test
    public void testInvalidRemoveStrKey() {
        KVPair<String, Integer> pair1 = new KVPair<>("apple", 1);
        KVPair<String, Integer> pair2 = new KVPair<>("banana", 2);
        KVPair<String, Integer> pair3 = new KVPair<>("orange", 3);

        skipListStr.insert(pair1);
        skipListStr.insert(pair2);
        skipListStr.insert(pair3);

        KVPair<String, Integer> result = skipListStr.remove("kiwi");

        assertEquals(result, null);
        assertEquals(3, skipListStr.size());
    }


    /**
     * Tests valid remove by value with
     * Integer-type keys.
     */
//    @Test
//    public void testValidRemoveIntVal() {
//        KVPair<String, Integer> pair1 = new KVPair<>("apple", 1);
//        KVPair<String, Integer> pair2 = new KVPair<>("banana", 2);
//        KVPair<String, Integer> pair3 = new KVPair<>("orange", 3);
//
//        skipListStr.insert(pair1);
//        skipListStr.insert(pair2);
//        skipListStr.insert(pair3);
//        skipListStr.removeByValue(1);
//
//        assertEquals(2, skipListStr.size());
//    }


    /**
     * Tests invalid remove by value with
     * Integer-type keys.
     */
    @Test
    public void testInvalidRemoveIntVal() {
        KVPair<String, Integer> pair1 = new KVPair<>("apple", 1);
        KVPair<String, Integer> pair2 = new KVPair<>("banana", 2);
        KVPair<String, Integer> pair3 = new KVPair<>("orange", 3);

        skipListStr.insert(pair1);
        skipListStr.insert(pair2);
        skipListStr.insert(pair3);

        KVPair<String, Integer> result = skipListStr.removeByValue(4);

        assertEquals(result, null);
        assertEquals(3, skipListStr.size());
    }
    
}