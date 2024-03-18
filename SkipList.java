import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import student.TestableRandom;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V>
    implements Iterable<KVPair<K, V>> {

    private SkipNode head; // First element (Sentinel Node)
    private int size; // number of entries in the Skip List

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 1);
        size = 0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     * 
     * @return a random level number
     */
    // New TesttableRandom
    int randomLevel() {
        Random random = new TestableRandom();
        return random.nextInt(64); // returns a random level
    }

    // Old Function
// int randomLevel() {
// int lev;
// Random value = new Random();
// for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
// // Do nothing
// }
// return lev; // returns a random level
// }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * @param key
     *            key to be searched for
     * 
     * @return ArrayList<KVPair<K, V,>>
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        ArrayList<KVPair<K, V>> found = new ArrayList<>();
        SkipNode current = head;
        // Start from the highest level of head and move down
        // This effectively uses the skip list's property of jumping over nodes
        for (int i = head.level; i >= 0; i--) {
            // Traverse forward at the current level while the next node's key
            // is less than the search key
            while (current.forward[i] != null && current.forward[i].pair
                .getKey().compareTo(key) < 0) {
                current = current.forward[i];
            }
        }
        // Move to the next node, which might have the matching key
        current = current.forward[0];
        // Collect all nodes at the base level with a matching key
        while (current != null && current.pair.getKey().compareTo(key) == 0) {
            found.add(current.pair);
            current = current.forward[0];
        }
        // Return the collected nodes or null if none found
        return found.isEmpty() ? null : found;
    }


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     * 
     * @param it
     *            the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
        // Check if Key is null
        if (it.getKey() == null) {
            System.out.print("Key cannot be null");
            return;
        }

        // Generate a random level for the new node.
        int newLevel = randomLevel();

        // If the new node's level is higher than the head's current level,
        // adjust the head to have more levels. This ensures that the head node
        // always has forward pointers at all levels used in the skip list.
        if (newLevel > head.level) {
            adjustHead(newLevel);
        }

        // Create an array to keep track of the nodes that will need to be
        // updated
        // to point to the new node. This array stores the rightmost nodes at
        // each level
        // that we encounter before the insertion point.
        // Track end of level
        SkipNode[] update = (SkipNode[])Array.newInstance(SkipNode.class,
            head.level + 1);

        // Start traversal from the head node.
        SkipNode current = head;

        // Traverse down from the highest level of the skip list to find the
        // insertion points.
        for (int i = head.level; i >= 0; i--) {
            // Move right at the current level until we find a node whose key is
            // greater than
            // the key of the node to be inserted. This finds the rightmost node
            // at the current
            // level before the insertion point.
            while (current.forward[i] != null && current.forward[i].pair
                .getKey().compareTo(it.getKey()) < 0) {
                current = current.forward[i];
            }

            // If the current level is within the new node's level, remember
            // this node.
            // It will be used later to update pointers.
            if (i <= newLevel) {
                update[i] = current;
            }
        }

        // Create the new node with the given key-value pair and level.
        SkipNode newNode = new SkipNode(it, newLevel);

        // Insert the new node into the list by updating pointers.
        for (int i = 0; i <= newLevel; i++) {
            // The new node's forward pointers at this level should point to
            // where
            // the previous node's pointers (at this level) were pointing.
            newNode.forward[i] = update[i].forward[i];

            // Update the previous node's forward pointer at this level to point
            // to the new node.
            update[i].forward[i] = newNode;
        }

        // Increment the size of the skip list since we've added a new node.
        size++;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    public void adjustHead(int newLevel) {
        SkipNode newHead = new SkipNode(null, newLevel);
        System.arraycopy(head.forward, 0, newHead.forward, 0,
            head.forward.length);
        head = newHead;
    }

    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     * 
     * @param key
     *            the KVPair to be removed
     * @return returns the removed pair if the pair was valid and null if not
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        // Create an array to hold the update path, which will be the nodes that
        // need
        // their pointers updated after removal.
        SkipNode[] update = (SkipNode[])Array.newInstance(SkipNode.class,
            head.level + 1);

        SkipNode current = head; // Start from the head of the skip list.

        // Traverse the list from top level down to the bottom level to find the
        // highest-level node for each level that precedes the node to be
        // removed.
        for (int i = head.level; i >= 0; i--) {
            // Continue moving forward at the current level while the next node
            // exists
            // and its key is less than the key to be removed.
            while (current.forward[i] != null && current.forward[i].pair
                .getKey().compareTo(key) < 0) {
                current = current.forward[i]; // Move to the next node at the
                                              // current level.
            }
            // Store the node in the update path for the current level.
            update[i] = current;
        }

        // Move to the next node which is the candidate for removal.
        current = current.forward[0];

        // Check if the current node is the node to be removed.
        if (current != null && current.pair.getKey().compareTo(key) == 0) {
            // For each level where the current node is in the update path,
            // update the forward pointers to skip over the current node.
            for (int i = 0; i <= head.level; i++) {
                // If the current level's forward pointer in the update path
                // doesn't point
                // to the current node, we've finished updating all relevant
                // levels.
                if (update[i].forward[i] != current)
                    break;

                // Update the forward pointer to skip the current node.
                update[i].forward[i] = current.forward[i];
            }
            // Decrease the size of the skip list because a node has been
            // removed.
            size--;

            // Return the pair held by the removed node.
            return current.pair;
        }

        // If no node with the given key was found, return null.
        return null;
    }

//    /**
//     * Removes the KVPair that is passed in as a parameter and returns true if
//     * the pair was valid and false if not.
//     * 
//     * @param key
//     *            the KVPair to be removed
//     * @return returns the removed pair if the pair was valid and null if not
//     */
//    @SuppressWarnings("unchecked")
//    public KVPair<K, V> removePair(K key, V val) {
//        // Create an array to hold the update path, which will be the nodes that
//        // need
//        // their pointers updated after removal.
//        SkipNode[] update = (SkipNode[])Array.newInstance(SkipNode.class,
//            head.level + 1);
//
//        SkipNode current = head; // Start from the head of the skip list.
//
//        // Traverse the list from top level down to the bottom level to find the
//        // highest-level node for each level that precedes the node to be
//        // removed.
//        for (int i = head.level; i >= 0; i--) {
//            // Continue moving forward at the current level while the next node
//            // exists
//            // and its key is less than the key to be removed.
//            while (current.forward[i] != null && current.forward[i].pair
//                .getKey().compareTo(key) < 0) {
//                current = current.forward[i]; // Move to the next node at the
//                                              // current level.
//            }
//            // Store the node in the update path for the current level.
//            update[i] = current;
//        }
//
//        // Move to the next node which is the candidate for removal.
//        current = current.forward[0];
//
//        // Check if the current node is the node to be removed.
//        if (current != null && current.pair.getKey().compareTo(key) == 0 
//            && current.pair.getValue().toString().compareTo(val.toString()) == 0) {
//            // For each level where the current node is in the update path,
//            // update the forward pointers to skip over the current node.
//            for (int i = 0; i <= head.level; i++) {
//                // If the current level's forward pointer in the update path
//                // doesn't point
//                // to the current node, we've finished updating all relevant
//                // levels.
//                if (update[i].forward[i] != current)
//                    break;
//
//                // Update the forward pointer to skip the current node.
//                update[i].forward[i] = current.forward[i];
//            }
//            // Decrease the size of the skip list because a node has been
//            // removed.
//            size--;
//
//            // Return the pair held by the removed node.
//            return current.pair;
//        }
//
//        // If no node with the given key was found, return null.
//        return null;
//    }


//    /**
//     * * Removes a KVPair with the specified value.
//     * 
//     * @param val
//     *            the value of the KVPair to be removed
//     * @return returns true if the removal was successful
//     */
//    public KVPair<K, V> removeByValue(K key, V val ) {
//        KVPair<K, V> found = findFirstPairByValueAndKey(key, val);
//        // Check if the current node is the node to be removed.
//        if (found != null) {
//            // Retrieve the key associated with the specified value.
//            K keyR = found.getKey();
//            V valR = found.getValue();
//
//            // Call the remove function with the retrieved key.
//            return removePair(keyR, valR);
//        }
//        else {
//            // If no node with the given value was found, return null.
//            return null;
//        }
//    }
    
    /**
     * * Removes a KVPair with the specified value.
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns true if the removal was successful
     */
    public KVPair<K, V> removeByValue(V val ) {
        KVPair<K, V> found = findFirstPairByValue(val);
        // Check if the current node is the node to be removed.
        if (found != null) {
            // Retrieve the key associated with the specified value.
            K keyR = found.getKey();
           

            // Call the remove function with the retrieved key.
            return remove(keyR);
        }
        else {
            // If no node with the given value was found, return null.
            return null;
        }
    }
    
    /**
     * * Removes a KVPair with the specified value.
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns true if the removal was successful
     */
    public KVPair<K, V> removeByValue(V val, K key) {
     // Create an array to hold the update path, which will be the nodes that
        // need
        // their pointers updated after removal.
        SkipNode[] update = (SkipNode[])Array.newInstance(SkipNode.class,
            head.level + 1);

        SkipNode current = head; // Start from the head of the skip list.

        // Traverse the list from top level down to the bottom level to find the
        // highest-level node for each level that precedes the node to be
        // removed.
        for (int i = head.level; i >= 0; i--) {
            // Continue moving forward at the current level while the next node
            // exists
            // and its key is less than the key to be removed.
            while ((current.forward[i] != null) && (current.forward[i].element()
                .getKey().compareTo(key) < 0 || (current.forward[i].element()
                .getKey().equals(key) && !current.forward[i].element().getValue().equals(val)))) {
                
                current = current.forward[i]; // Move to the next node at the
                                              // current level.
            }
            // Store the node in the update path for the current level.
            update[i] = current;
        }

        // Move to the next node which is the candidate for removal.
        current = current.forward[0];

        // Check if the current node is the node to be removed.
        if (current != null && current.element().getKey().equals(key) &&
            current.pair.getValue().equals(val)) {
            // For each level where the current node is in the update path,
            // update the forward pointers to skip over the current node.
            for (int i = 0; i <= head.level; i++) {
                // If the current level's forward pointer in the update path
                // doesn't point
                // to the current node, we've finished updating all relevant
                // levels.
                if (update[i].forward[i] != null &&
                    update[i].forward[i] == current) {
                    
                    update[i].forward[i] = current.forward[i];
                   // break;
                }
                // Update the forward pointer to skip the current node.
                //update[i].forward[i] = current.forward[i];
            }
            // Decrease the size of the skip list because a node has been
            // removed.
            size--;

            // Return the pair held by the removed node.
            return current.element();
        }

        // If no node with the given key was found, return null.
        return null;
    }


    /**
     * * Finds the first key where V val is found
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return null if not found and pair if found
     */
//    public KVPair<K, V> findFirstPairByValueAndKey(K key, V val) {
//        // Start from the head of the skip list.
//        SkipNode current = head;
//
//        // Iterate through the entire skip list.
//        while (current != null) {
//            // Check if the current node's pair has the specified value.
//            if (current.pair != null && current.pair.getValue().toString().compareTo(val.toString()) == 0
//                && current.pair.getKey().compareTo(key)==0) {
//                // Return the key-value pair.
//                return current.pair;
//            }
//            // Move to the next node.
//            current = current.forward[0];
//        }
//
//        // If no node with the given value was found, return null.
//        return null;
//    }
    
    /**
     * * Finds the first key where V val is found
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return null if not found and pair if found
     */
    public KVPair<K, V> findFirstPairByValue(V val) {
        // Start from the head of the skip list.
        SkipNode current = head;

        // Iterate through the entire skip list.
        while (current != null) {
            // Check if the current node's pair has the specified value.
            if (current.pair != null && current.pair.getValue().toString().compareTo(val.toString()) == 0
                ) {
                // Return the key-value pair.
                return current.pair;
            }
            // Move to the next node.
            current = current.forward[0];
        }

        // If no node with the given value was found, return null.
        return null;
    }


    /**
     * Prints out the SkipList in a human
     * readable format to the console.
     */
    public void dump() {
        System.out.println("SkipList dump:");
        // If the first node after head (at the base level)
        // is null, it means
        // there are no elements in the list.
        if (head.forward[0] == null) {
            System.out.printf("Node has depth %d, Value null\n", head.level);
        }
        else {
            // If there are nodes, start from
            // the head and print each node's
            // details.
            SkipNode current = head;
            while (current != null) {
                String valueString = current.pair == null
                    ? "null"
                    : current.pair.toString();
                System.out.printf("Node with depth %d, Value %s\n",
                    current.level, valueString);
                current = current.forward[0];
            }
        }
        System.out.printf("SkipList size is: %d\n", size);
    }

    /**
     * This class implements a SkipNode for
     * the SkipList data structure.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // An array of pointers to subsequent nodes
        private SkipNode[] forward;
        // the level of the node
        private int level;

        /**
         * Initializes the fields with the
         * required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }


    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;

        public SkipListIterator() {
            current = head;
        }


        @Override
        public boolean hasNext() {
            // Auto-generated method stub
            return current.forward[0] != null;
        }


        @Override
        public KVPair<K, V> next() {
            // Auto-generated method stub
            KVPair<K, V> elem = current.forward[0].element();
            current = current.forward[0];
            return elem;
        }

    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Auto-generated method stub
        return new SkipListIterator();
    }

}
