import java.util.List;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.HashSet;
import java.util.ArrayList;


/**
 * Your implementation of HashMap.
 * 
 * @author Yuqi Cao
 * @userid ycao344
 * @GTID 903352025
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array
     * violates the max load factor. For example, let's say the array is of
     * length 5 and the current size is 3 (LF = 0.6). For this example, assume
     * that no elements are removed in between steps. If a non-duplicate key is
     * added, the size will increase to 4 (LF = 0.8), but the resize shouldn't
     * occur until the next put operation.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The added key or "
                    + "value cannot be null.");
        }
        //check load factor
        double loadFactor = (double) size / table.length;
        if (loadFactor > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        //initialization
        int index = Math.abs(key.hashCode() % table.length);
        int firstDeleted = -1;
        MapEntry<K, V> needToAdd = new MapEntry<>(key, value);
        if (table[index] == null) {
            table[index] = needToAdd;
            size++;
            return null;
        }

        //iterate through the array
        while (table[index] != null) {
            if (table[index].isRemoved()) {
                if (table[index].getKey().equals(key)) {
                    table[index] = needToAdd;
                    size++;
                    return null;
                } else {
                    if (firstDeleted == -1) {
                        firstDeleted = index;
                    } else {
                        table[firstDeleted] = needToAdd;
                        size++;
                        return null;
                    }
                }
                //index = (index + 1) % table.length;
            }
            if (table[index].equals(needToAdd)) {
                return table[index].getValue();
            } else if (table[index].getKey().equals(key)) {
                V temp = table[index].getValue();
                table[index].setValue(value);
                return temp;
            } else {
                index = (index + 1) % table.length;
            }
        }
        if (firstDeleted != -1) {
            table[firstDeleted] = needToAdd;
        } else {
            table[index] = needToAdd;
        }
        size++;
        return null;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException(
                    "The removed key cannot be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            throw new NoSuchElementException(
                    "The data you want doesn't exist in this HashMap.");
        }
        while (table[index] != null) {
            if (table[index].isRemoved()
                    && table[index].getKey().equals(key)) {
                throw new NoSuchElementException(
                        "The data you want doesn't exist in this HashMap.");
            } else if (!table[index].isRemoved()
                    && table[index].getKey().equals(key)) {
                table[index].setRemoved(true);
                size--;
                return table[index].getValue();
            }
            index = (index + 1) % table.length;
        }
        throw new NoSuchElementException(
                "The data you want doesn't exist in this HashMap.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            throw new NoSuchElementException(
                    "The data you want doesn't exist in this HashMap.");
        }
        while (table[index] != null) {
            if (!table[index].isRemoved()
                    && table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
            if (table[index].isRemoved() && table[index].getKey().equals(key)) {
                throw new NoSuchElementException(
                        "The data you want doesn't exist in this HashMap.");
            }
            index = (index + 1) % table.length;
        }
        throw new NoSuchElementException(
                "The data you want doesn't exist in this HashMap.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        if (table[index] == null) {
            return false;
        }
        while (table[index] != null) {
            if (!table[index].isRemoved()
                    && table[index].getKey().equals(key)) {
                return true;
            }
            if (table[index].isRemoved() && table[index].getKey().equals(key)) {
                return false;
            }
            index = (index + 1) % table.length;
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keySetToReturn = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                keySetToReturn.add(table[i].getKey());
            }
        }
        return keySetToReturn;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add 
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> valueList = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                valueList.add(table[i].getValue());
            }
        }
        return valueList;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */
    public void resizeBackingTable(int length) {
        if (length < 0 || length == 0) {
            throw new IllegalArgumentException("Length should be positive.");
        }
        if (length < size) {
            throw new IllegalArgumentException("Length cannot be less than "
                    + "the number of items in the hash map.");
        }
        MapEntry<K, V>[] newArr = (MapEntry<K, V>[]) new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                int index = Math.abs(table[i].getKey().hashCode()
                        % newArr.length);

                while (newArr[index] != null) {
                    index = (index + 1) % newArr.length;
                }
                newArr[index] = table[i];
            }
        }
        table = newArr;
    }

    /**
     * Clears the table and resets it to the default length.
     */
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }
    
    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}