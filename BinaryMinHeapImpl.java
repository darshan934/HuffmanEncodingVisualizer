import java.util.*;

/**
 *
 * @param <V> {@inheritDoc}
 * @param <Key> {@inheritDoc}
 *
 */
public class BinaryMinHeapImpl<Key extends Comparable<Key>, V> implements BinaryMinHeap<Key, V> {
    private List<Entry<Key, V>> li;

    public BinaryMinHeapImpl() {
        li = new ArrayList<Entry<Key, V>>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {

        return li.size();
    }

    @Override
    public boolean isEmpty() {

        return (size() == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Key key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (this.containsValue(value)) {
            throw new IllegalArgumentException("heap contains this value");
        }

        Entry<Key, V> e = new Entry<Key, V>(key, value);
        Key myK = e.getKey();
        V myV = e.getValue();
        if (isEmpty()) {
            li.add(0, e);
            return;
        }
        for (int i = 0; i < li.size(); i++) {
            if (i == 0 && myK.compareTo(li.get(i).getKey()) < 0) {
                li.add(0, e);
                return;
            } else if (i == li.size() - 1) {
                li.add(i + 1, e);
                return;
            } else if (myK.compareTo(li.get(i).getKey()) >= 0 &&
                    myK.compareTo(li.get(i + 1).getKey()) <= 0) {
                li.add(i + 1, e);
                return;
            }
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public V peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return li.get(0).getValue();
    }
    //helper for re-minheapifying the heap
    void minHeapify(int i) {
        Entry<Key, V> min = li.get(i);
        int smallestIndex = i;
        if ((i + 1) * 2 <= size()) {
            Entry<Key, V> left = li.get((i + 1) * 2 - 1);
            if (left.getKey().compareTo(li.get(i).getKey()) < 0) {
                smallestIndex = (i + 1) * 2 - 1;
                min = left;
            }
        }
        if ((i + 1) * 2 + 1 <= size()) {
            Entry<Key, V> right = li.get((i + 1) * 2);
            if (right.getKey().compareTo(min.getKey()) < 0) {
                smallestIndex = (i + 1) * 2;
            }
        }
        if (smallestIndex != i) {
            Collections.swap(li, i, smallestIndex);
            minHeapify(smallestIndex);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Entry<Key, V> e = li.remove(0);
        if (size() != 0) {
            minHeapify(0);
        }
        return e.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {
        Set<V> set = new HashSet<V>();
        for (Entry<Key, V> e : li) {
            if (e != null) {
                set.add(e.getValue());
            }
        }
        return set;
    }
    /**
     * Helper entry class for maintaining value-key pairs.
     * The underlying indexed list for your heap will contain
     * these entries.
     *
     * You are not required to use this, but we recommend it.
     */
    class Entry<A, B> {

        private A key;
        private B value;

        public Entry(A key, B value) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return  the value stored in the entry
         */
        B getValue() {
            return this.value;
        }

        /**
         * @return  the key stored in the entry
         */
        A getKey() {
            return this.key;
        }

        /**
         * Changes the key of the entry.
         *
         * @param key  the new key
         * @return  the old key
         */
        A setKey(A key) {
            A oldKey = this.key;
            this.key = key;
            return oldKey;
        }

    }

}