import org.junit.Test;


import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class BinaryMinHeapImplTest {
    //test on empty heap
    @Test
    public void testIsEmpty() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        assertEquals(0, b.size());
        assertTrue(b.isEmpty());
        assertTrue(b.values().isEmpty());
        b.add(2, 3);
        assertFalse(b.isEmpty());
        assertFalse(b.values().isEmpty());
        assertTrue(b.values().contains(3));

    }
    // test on illegal stuff
    @Test (expected = NoSuchElementException.class)
    public void testIsEmptyPeek() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.peek();
    }
    @Test (expected = NoSuchElementException.class)
    public void testIsEmptyExtractMin() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.extractMin();
    }



    //test on add when illegal argument
    @Test (expected = IllegalArgumentException.class)
    public void testAddExistingValue() {

        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.add(2, 3);
        b.add(2, 3);
    }

    //test on add when illegal argument
    @Test (expected = IllegalArgumentException.class)
    public void testAddNull() {

        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.add(2, 3);
        b.add(null, 3);
    }

    //test on heaps
    @Test
    public void testBSize1() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.add(2, 3);
        assertEquals(1, b.size());

        assertFalse(b.isEmpty());
        assertTrue(b.containsValue(3));
        assertTrue(b.values().contains(3));
        assertFalse(b.values().contains(2));
        assertEquals((Integer) 3, b.peek());
        assertEquals((Integer) 3, b.extractMin());
        assertEquals(0, b.size());
        assertTrue(b.isEmpty());
    }

    //test on heaps
    @Test
    public void testBSize2SecondSmaller() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.add(2, 3);
        b.add(1, 0);
        assertEquals(2, b.size());

        assertFalse(b.isEmpty());
        assertTrue(b.containsValue(0));
        assertTrue(b.values().contains(0));

        assertEquals((Integer) 0, b.peek());
        assertEquals((Integer) 0, b.extractMin());
        assertEquals(1, b.size());
        assertFalse(b.containsValue(0));
        assertFalse(b.values().contains(0));
        assertEquals((Integer) 3, b.peek());

    }

    //test on heaps
    @Test
    public void testBSize2Tie() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.add(2, 3);
        b.add(2, 0);
        assertEquals(2, b.size());

        assertFalse(b.isEmpty());
        assertTrue(b.containsValue(0));
        assertTrue(b.values().contains(0));

        Integer a = b.extractMin();

        assertEquals(1, b.size());

    }

    //test on bigger heaps
    @Test
    public void testBSize3() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.add(2, 3);
        b.add(1, 0);
        b.add(3, 10);
        assertEquals(3, b.size());

        assertFalse(b.isEmpty());
        assertTrue(b.containsValue(0));
        assertTrue(b.containsValue(10));
        assertTrue(b.containsValue(3));
        assertTrue(b.values().contains(0));
        assertTrue(b.values().contains(3));
        assertTrue(b.values().contains(10));
        assertEquals((Integer) 0, b.peek());
        assertEquals((Integer) 0, b.extractMin());
        assertEquals(2, b.size());
        assertFalse(b.containsValue(0));
        assertFalse(b.values().contains(0));
        assertEquals((Integer) 3, b.peek());
        assertEquals((Integer) 3, b.extractMin());
        assertFalse(b.containsValue(3));
        assertFalse(b.values().contains(3));
        assertEquals(1, b.size());
        assertEquals((Integer) 10, b.extractMin());
        assertTrue(b.isEmpty());
        assertTrue(b.values().isEmpty());

    }

    @Test
    public void testBSize6() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();
        b.add(2, 3);
        b.add(1, 0);
        b.add(3, 10);
        b.add(6, 6);
        b.add(5, 5);
        b.add(4, 4);


        assertEquals(6, b.size());

        assertFalse(b.isEmpty());
        assertTrue(b.containsValue(0));
        assertTrue(b.containsValue(10));
        assertTrue(b.containsValue(3));

        assertTrue(b.values().contains(0));
        assertTrue(b.values().contains(3));
        assertTrue(b.values().contains(10));

        assertEquals((Integer) 0, b.peek());
        assertEquals((Integer) 0, b.extractMin());
        assertEquals(5, b.size());
        assertFalse(b.containsValue(0));
        assertFalse(b.values().contains(0));

        assertEquals((Integer) 3, b.peek());
        assertEquals((Integer) 3, b.extractMin());
        assertFalse(b.containsValue(3));
        assertFalse(b.values().contains(3));
        assertEquals(4, b.size());

        assertEquals((Integer) 10, b.extractMin());
        assertEquals((Integer) 4, b.extractMin());
        assertEquals((Integer) 5, b.extractMin());
        assertEquals((Integer) 6, b.extractMin());

    }

    @Test
    public void testBSize6BackwardAdd() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();

        b.add(6, 6);
        b.add(5, 5);
        assertEquals((Integer) 5, b.peek());
        b.add(4, 4);
        assertEquals((Integer) 4, b.peek());
        b.add(3, 3);
        b.add(2, 2);
        b.add(1, 1);
        assertEquals((Integer) 1, b.peek());
        assertEquals((Integer) 1, b.extractMin());
        assertEquals((Integer) 2, b.extractMin());
        assertEquals((Integer) 3, b.extractMin());
        assertEquals((Integer) 4, b.extractMin());
        assertEquals((Integer) 5, b.extractMin());
        assertEquals((Integer) 6, b.extractMin());
    }

    @Test
    public void testBSize6DisorganizedAdd() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();

        b.add(2, 2);
        b.add(5, 5);
        assertEquals((Integer) 2, b.peek());
        b.add(4, 4);
        assertEquals((Integer) 2, b.peek());
        b.add(4, 0);
        b.add(3, 3);
        assertEquals((Integer) 2, b.peek());
        b.add(1, 1);
        assertEquals((Integer) 1, b.peek());

        assertEquals((Integer) 1, b.extractMin());
        assertEquals((Integer) 2, b.extractMin());
        assertEquals((Integer) 3, b.extractMin());

        b.extractMin();
        b.extractMin();
        assertEquals((Integer) 5, b.extractMin());

    }

    //test on set
    @Test
    public void testBSize6SetCreation() {
        BinaryMinHeapImpl<Integer, Integer> b = new BinaryMinHeapImpl<Integer, Integer>();

        b.add(6, 6);
        b.add(5, 5);
        assertEquals((Integer) 5, b.peek());
        b.add(4, 4);
        assertEquals((Integer) 4, b.peek());
        b.add(3, 3);
        b.add(2, 2);
        b.add(1, 1);
        assertEquals((Integer) 1, b.peek());
        assertTrue(b.values().contains(6));
        assertTrue(b.values().contains(5));
        assertTrue(b.values().contains(4));
        assertTrue(b.values().contains(3));
        assertTrue(b.values().contains(2));
        assertTrue(b.values().contains(1));
    }

}