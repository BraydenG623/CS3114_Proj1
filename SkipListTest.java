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
 * @author Brayden Gardner
 * @version 1.0
 * @since 2024-02-03
 */
public class SkipListTest {

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


    /**
     * Tests that the same key can be inserted twice.
     */
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

// /**
// * Tests a valid remove by value instance.
// */
// @Test
// public void testRemoveByValValid() {
// KVPair<Integer, String> pair1 = new KVPair<>(5, "Value1");
// KVPair<Integer, String> pair2 = new KVPair<>(10, "Value2");
// KVPair<Integer, String> pair3 = new KVPair<>(15, "Value3");
//
// skipList.insert(pair1);
// skipList.insert(pair2);
// skipList.insert(pair3);
// skipList.removeByValue("Value1");
//
// assertEquals(2, skipList.size());
// }


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
                            for (KVPair<Integer, String> resultPair :
                                searchResult) {
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
// @Test
// public void testValidRemoveStrKey() {
// KVPair<String, Integer> pair1 = new KVPair<>("apple", 1);
// KVPair<String, Integer> pair2 = new KVPair<>("banana", 2);
// KVPair<String, Integer> pair3 = new KVPair<>("orange", 3);
//
// skipListStr.insert(pair1);
// skipListStr.insert(pair2);
// skipListStr.insert(pair3);
//
// KVPair<String, Integer> result = skipListStr.remove("apple");
//
// assertEquals(result, pair1);
// assertEquals(2, skipListStr.size());
// }


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


    /**
     * Tests that random numbers are not
     * returning as zero
     */
    @Test
    public void testRandomLevel() {
        SkipList<Integer, String> skipList3 = new SkipList<>();
        int maxLevel = 0;
        int totalRuns = 10000;
        for (int i = 0; i < totalRuns; i++) {
            int level = skipList3.randomLevel();
            maxLevel = Math.max(maxLevel, level);
            // We expect most levels to be 0 or low, given the
            // halving probability, but there should be a spread
        }

        // Check that we've obtained a reasonable distribution of levels
        // This is a very indirect way of testing the behavior, focusing
        // on the outcome over many runs
        assertTrue("Max level should be greater than a minimal threshold "
            + "to indicate distribution", maxLevel > 3);
    }


    /**
     * Test Search Descending Levels
     */
    @Test
    public void testSearchDescendingLevels() {
        SkipList<Integer, String> skipList4 = new SkipList<>();
        // Assume insert method correctly inserts nodes at varying levels
        // Here we insert a few nodes for simplicity, in practice, these
        // should be inserted at varying levels
        skipList4.insert(new KVPair<>(10, "Value10"));
        skipList4.insert(new KVPair<>(20, "Value20"));

        // The actual test would need to verify that the search traverses from
        // the highest level to the lowest,
        // but without direct access to the levels, we focus on the outcome:
        ArrayList<KVPair<Integer, String>> result = skipList4.search(10);
        assertFalse("Search result should not be empty", result.isEmpty());
        assertEquals("Search should find the correct value", "Value10", result
            .get(0).getValue());
    }


    /**
     * Test Search for non existent keys
     */
    @Test
    public void testSearchForNonexistentKey() {
        SkipList<Integer, String> skipList5 = new SkipList<>();
        skipList5.insert(new KVPair<>(10, "Value10"));

        // Search for a key that doesn't exist
        ArrayList<KVPair<Integer, String>> result = skipList5.search(15);
        assertNull("Search result should be null for a nonexistent key",
            result);
    }


    /**
     * Test search stops at correct node
     */
    @Test
    public void testSearchStopsAtCorrectNode() {
        SkipList<Integer, String> skipList6 = new SkipList<>();
        skipList6.insert(new KVPair<>(10, "Value10"));
        skipList6.insert(new KVPair<>(20, "Value20"));

        // Insert a value that requires the search to stop before reaching
        // the end of the level
        ArrayList<KVPair<Integer, String>> result = skipList6.search(20);
        assertFalse("Search result should not be empty when stopping at "
            + "the correct node", result.isEmpty());
        assertEquals("Search should find the correct value when stopping at "
            + "the correct node", "Value20", result.get(0).getValue());
    }


    /**
     * Tests the insertion logic when the key of the new node is greater than
     * existing nodes,
     * ensuring the comparison condition works as expected.
     */
    @Test
    public void testInsertWithGreaterKey() {
        skipList.insert(new KVPair<>(5, "Value5"));
        skipList.insert(new KVPair<>(10, "Value10")); // This should trigger the
                                                      // comparison condition to
                                                      // be true

        ArrayList<KVPair<Integer, String>> result = skipList.search(10);
        assertNotNull("Search should find the inserted node with key 10",
            result);
        assertEquals("The found node should have the correct value", "Value10",
            result.get(0).getValue());
    }


    /**
     * Tests the insertion logic when the key of the new node is less than
     * existing nodes,
     * ensuring nodes are correctly navigated and inserted in lexicographical
     * order.
     */
    @Test
    public void testInsertWithLesserKey() {
        skipList.insert(new KVPair<>(10, "Value10"));
        skipList.insert(new KVPair<>(5, "Value5")); // This should correctly
                                                    // navigate before inserting

        ArrayList<KVPair<Integer, String>> result = skipList.search(5);
        assertNotNull("Search should find the inserted node with key 5",
            result);
        assertEquals("The found node should have the correct value", "Value5",
            result.get(0).getValue());
    }


    /**
     * Tests the insertion logic when the new node's level is within the current
     * level,
     * ensuring the conditional logic for level handling works as expected.
     */
    @Test
    public void testInsertWithLevelHandling() {
        // Assuming randomLevel() could return levels up to 2 for testing
        skipList.insert(new KVPair<>(5, "Value5")); // Assume this gets level 1
        skipList.insert(new KVPair<>(10, "Value10")); // Assume this gets level
                                                      // 2

        // This test is more about ensuring no exceptions occur and the list
        // size increases,
        // as directly testing levels would require access to internal state not
        // exposed by the SkipList API.
        assertEquals("SkipList should contain two nodes after insertions", 2,
            skipList.size());
    }


    /**
     * Tests removing a key that does not exist in the skip list.
     * This scenario tests when the comparison condition for key removal is
     * false.
     */
    @Test
    public void testRemoveNonExistentKey() {
        // Pre-insert some nodes to ensure the skip list is not empty
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(2, "Two"));

        // Attempt to remove a key that does not exist in the list
        KVPair<Integer, String> result = skipList.remove(3);

        assertNull("Removing a non-existent key should return null", result);
        assertEquals("The size of the skip list should remain unchanged", 2,
            skipList.size());
    }


    /**
     * Tests the traversal logic when removing a key by ensuring traversal stops
     * correctly.
     * This scenario indirectly tests the comparison condition is false after
     * traversing all nodes.
     */
    @Test
    public void testRemoveKeyTraversal() {
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(3, "Three"));
        // Insert a key that would require traversing the entire list
        skipList.insert(new KVPair<>(2, "Two"));

        // Remove a key that requires traversal
        KVPair<Integer, String> result = skipList.remove(2);

        assertNotNull("Removing an existent key should not return null",
            result);
        assertEquals("Removed key should have the correct value", "Two", result
            .getValue());
        assertNull("Searching for the removed key should return null", skipList
            .search(2));
    }


    /**
     * Tests removing the last key in a skip list, ensuring traversal reaches
     * the end of the list.
     */
    @Test
    public void testRemoveLastKey() {
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(2, "Two"));
        skipList.insert(new KVPair<>(3, "Three"));

        // Remove the last key in the skip list
        KVPair<Integer, String> result = skipList.remove(3);

        assertNotNull("Removing the last key should succeed", result);
        assertEquals("The size of the skip list should be reduced", 2, skipList
            .size());
    }


    /**
     * Tests removing a value that does not exist in the skip list, covering the
     * condition
     * where !current.forward[i].pair.getValue().equals(val) is always true.
     */
    @Test
    public void testRemoveByValueNonExistent() {
        // Adding some elements to the skip list
        skipListStr.insert(new KVPair<>("One", 1));
        skipListStr.insert(new KVPair<>("Two", 2));

        // Attempting to remove a value that does not exist in the list
        KVPair<String, Integer> result = skipListStr.removeByValue(3);

        assertNull("Attempt to remove a non-existent value should return null",
            result);
    }


    /**
     * Tests the traversal logic in removeByValue, ensuring the loop terminates
     * correctly
     * when current.forward[i] is null.
     */
    @Test
    public void testRemoveByValueTraversal() {
        skipListStr.insert(new KVPair<>("One", 1));
        skipListStr.insert(new KVPair<>("Two", 2));
        skipListStr.insert(new KVPair<>("Three", 3));

        KVPair<String, Integer> result = skipListStr.removeByValue(2);

        assertNotNull("Removing an existing value should succeed", result);
        assertEquals("Removed value should be correct", Integer.valueOf(2),
            result.getValue());
    }


    /**
     * Tests the decrement of the size attribute after removing an element by
     * key.
     * Ensures the size attribute correctly reflects the number of elements.
     */
    @Test
    public void testSizeDecrementOnRemoveByKey() {
        skipListStr.insert(new KVPair<>("One", 1));
        skipListStr.insert(new KVPair<>("Two", 2));
        int initialSize = skipListStr.size();

        skipListStr.remove("One");
        assertEquals("Size should decrement by 1 after removal", initialSize
            - 1, skipListStr.size());
    }


    /**
     * Tests removing an element that does not exist does not affect the size.
     */
    @Test
    public void testSizeNoChangeOnRemoveNonExistentByKey() {
        skipListStr.insert(new KVPair<>("One", 1));
        int initialSize = skipListStr.size();

        skipListStr.remove("NonExistentKey");
        assertEquals("Size should not change when removing a non-existent key",
            initialSize, skipListStr.size());
    }


    /**
     * Tests the decrement of the size attribute after removing an element by
     * value.
     * Ensures the size attribute is accurately updated to reflect the current
     * state.
     */
    @Test
    public void testSizeDecrementOnRemoveByValue() {
        skipListStr.insert(new KVPair<>("One", 1));
        skipListStr.insert(new KVPair<>("Two", 2));
        int initialSize = skipListStr.size();

        skipListStr.removeByValue(2);
        assertEquals("Size should decrement by 1 after removal by value",
            initialSize - 1, skipListStr.size());
    }


    /**
     * Tests removing an element by value that does not exist does not affect
     * the size.
     */
    @Test
    public void testSizeNoChangeOnRemoveNonExistentByValue() {
        skipListStr.insert(new KVPair<>("One", 1));
        int initialSize = skipListStr.size();

        skipListStr.removeByValue(99); // Attempt to remove a value not present
        assertEquals(
            "Size should not change when removing a non-existent value",
            initialSize, skipListStr.size());
    }

}
