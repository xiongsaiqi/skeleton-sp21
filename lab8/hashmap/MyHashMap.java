package hashmap;

import java.util.LinkedList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;


/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author XiongSaiqi
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private double maxLoad;
    /* size */
    private int size;
    /* keySet */
    private HashSet<K> keySet;

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        /*FIXME 这些代码都是我写的  应该早点把git掌握熟练的 妈的 现在每次都没有什么版本更迭的感觉了
        使用工厂方法来构建底层数据结构
         使用一个ArrayList来作为hashMap的底层
         */
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        this.maxLoad = maxLoad;
        size = 0;
        keySet = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }
    // Your code won't compile until you do so!

    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[index] == null) {
            return false;
        }
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[index] == null) {
            return null;
        }
        for (Node node : buckets[index]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        double curLoad = (double) size / buckets.length;
        if (curLoad > maxLoad) {
            resizeBuckets(2);
        }
        int index = Math.floorMod(key.hashCode(), buckets.length);
        if (buckets[index] == null) {
            buckets[index] = createBucket();
        }
        Node s = createNode(key, value);
        keySet.add(key);
        pushNode(s, buckets[index]);
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        for (Node s : buckets[index]) {
            if (s.key.equals(key)) {
                buckets[index].remove(s);
                return s.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashSetIterator();
    }

    private void resizeBuckets(double multiply) {
        Collection<Node>[] temp = buckets;
        buckets = createTable((int) (temp.length * multiply));
        for (Collection<Node> nodes : temp) {
            if (nodes == null) {
                continue;
            }
            Iterator<Node> iter = nodes.iterator();
            int index = Math.floorMod(iter.next().hashCode(), buckets.length);
            buckets[index] = nodes;
        }
    }

    private void pushNode(Node s, Collection<Node> colles) {
        for (Node p : colles) {
            if (s.key == p.key) {
                p.value = s.value;
                return;
            }
        }
        colles.add(s);
        size++;
    }

    private class MyHashSetIterator implements Iterator<K> {
        private int curIndex;
        private Iterator<K> iter;

        MyHashSetIterator() {
            curIndex = 0;
            iter = keySet.iterator();
        }

        @Override
        public boolean hasNext() {
            return curIndex < size;
        }

        @Override
        public K next() {
            curIndex += 1;
            return iter.next();
        }
    }
}
