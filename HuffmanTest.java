import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class HuffmanTest {
    //---------------test on buildingn from alphabet map------------

    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromMapNull() {
        Map<Character, Integer> alphabet = null;
        Huffman h = new Huffman(alphabet);

    }
    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromMapEmpty() {
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        Huffman h = new Huffman(alphabet);

    }

    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromBadMapLessThan2() {
        Map<Character, Integer> alphabet = new TreeMap<Character, Integer>();
        alphabet.put('a', 3);

        Huffman h = new Huffman(alphabet);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromBadMapNegativeFreq() {
        Map<Character, Integer> alphabet = new TreeMap<Character, Integer>();
        alphabet.put('a', -1);
        Huffman h = new Huffman(alphabet);
    }

    @Test
    public void testBuildFromMapSize2() {
        Map<Character, Integer> alphabet = new TreeMap<Character, Integer>();
        alphabet.put('a', 1);
        alphabet.put('b', 2);
        Huffman h = new Huffman(alphabet);
        MinPQ<Huffman.Node> q = h.getQueue();
        Huffman.Node n = q.extractMin();

        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());
        //test on lc and rc
        assertEquals('a', n.getLeftChild().getLetter());
        assertEquals(1.0 / 3, n.getLeftChild().getFreq(), 0.01);
        assertEquals('b', n.getRightChild().getLetter());
        assertEquals(2.0 / 3, n.getRightChild().getFreq(), 0.01);
    }

    //test on same frequency letters, should follow the property
    @Test
    public void testBuildFromMapSize2SameFreq() {
        Map<Character, Integer> alphabet = new TreeMap<Character, Integer>();
        alphabet.put('a', 1);
        alphabet.put('b', 1);
        Huffman h = new Huffman(alphabet);
        MinPQ<Huffman.Node> q = h.getQueue();
        Huffman.Node n = q.extractMin();

        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());
        //test on lc and rc
        assertEquals(1.0 / 2, n.getLeftChild().getFreq(), 0.01);
        assertEquals('a', n.getLeftChild().getLetter());
        assertEquals(1.0 / 2, n.getRightChild().getFreq(), 0.01);
        assertEquals('b', n.getRightChild().getLetter());
    }

    @Test
    public void testBuildFromMapSize3() {
        Map<Character, Integer> alphabet = new TreeMap<Character, Integer>();
        alphabet.put('a', 3);
        //2nd node
        alphabet.put('b', 2);
        alphabet.put('c', 1);
        Huffman h = new Huffman(alphabet);
        MinPQ<Huffman.Node> q = h.getQueue();

        Huffman.Node n = q.extractMin();


        //(-1, 1)
        //test on lc

        assertEquals(3.0 / 6, n.getLeftChild().getFreq(), 0.01);
        assertEquals('a', n.getLeftChild().getLetter());
        //rc
        assertEquals(3.0 / 6, n.getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());
        //rc lc and rc
        assertEquals(1.0 / 6, n.getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals('c', n.getRightChild().getLeftChild().getLetter());
        assertEquals(2.0 / 6, n.getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals('b', n.getRightChild().getRightChild().getLetter());

    }

    @Test
    public void testBuildFromMapSize4ContainsSame() {
        Map<Character, Integer> alphabet = new TreeMap<Character, Integer>();
        alphabet.put('a', 3);
        alphabet.put('d', 1);
        alphabet.put('b', 2);
        alphabet.put('c', 1);

        Huffman h = new Huffman(alphabet);
        MinPQ<Huffman.Node> q = h.getQueue();
        Huffman.Node n = q.extractMin();
        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());

        //lc
        assertEquals(3.0 / 7, n.getLeftChild().getFreq(), 0.01);
        assertEquals('a', n.getLeftChild().getLetter());
        //rc
        assertEquals(4.0 / 7, n.getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getLetter());
        //rc.lc
        assertEquals(2.0 / 7, n.getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals('b', n.getRightChild().getLeftChild().getLetter());
        //rc.rc
        assertEquals(2.0 / 7, n.getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getRightChild().getLetter());
        //rc.rc.lc
        assertEquals(1.0 / 7,
                n.getRightChild().getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals('c', n.getRightChild().getRightChild().getLeftChild().getLetter());
        //rc.rc.rc
        assertEquals(1.0 / 7,
                n.getRightChild().getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals('d', n.getRightChild().getRightChild().getRightChild().getLetter());



    }


    //---------------test on buildingn from seed-----------

    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromSeedNull() {
        String seed = null;
        Huffman h = new Huffman(seed);

    }
    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromSeedEmpty() {
        String seed = "";
        Huffman h = new Huffman(seed);

    }

    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromBadSeedLessThan2() {
        String seed = "a";

        Huffman h = new Huffman(seed);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testBuildFromBadSeed1Char() {
        String seed = "aaaaaaaa";
        Huffman h = new Huffman(seed);
    }
    //---------------test on createMapFromSeed-----------
    @Test
    public void testBuildMapFromSeed() {
        String seed = "ab";
        Map<Character, Integer> m = Huffman.createMapFromSeed(seed);
        assertTrue(m.containsKey('a'));
        assertTrue(m.containsKey('b'));
        assertTrue(m.containsValue(1));

        String seed2 = "aab";
        Map<Character, Integer> m2 = Huffman.createMapFromSeed(seed2);
        assertTrue(m2.containsKey('a'));
        assertTrue(m2.containsKey('b'));
        assertEquals((Integer)2, m2.get('a'));
        assertEquals((Integer)1, m2.get('b'));

        String seed3 = "aabbbc";
        Map<Character, Integer> m3 = Huffman.createMapFromSeed(seed3);
        assertEquals((Integer)2, m3.get('a'));
        assertEquals((Integer)3, m3.get('b'));
        assertEquals((Integer)1, m3.get('c'));

        String seed4 = "aba";
        Map<Character, Integer> m4 = Huffman.createMapFromSeed(seed4);
        assertEquals((Integer)2, m4.get('a'));
        assertEquals((Integer)1, m4.get('b'));
    }


    //---------------now testing on valid seeds
    @Test
    public void testBuildFromSeed2Char() {
        String seed = "ab";
        Huffman h = new Huffman(seed);
        MinPQ<Huffman.Node> q = h.getQueue();
        Huffman.Node n = q.extractMin();

        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());
        //test on lc and rc
        assertEquals(1.0 / 2, n.getLeftChild().getFreq(), 0.01);
        assertEquals('a', n.getLeftChild().getLetter());
        assertEquals(1.0 / 2, n.getRightChild().getFreq(), 0.01);
        assertEquals('b', n.getRightChild().getLetter());
    }

    @Test
    public void testBuildFromSeed3Chars() {

        String seed = "abc";
        Huffman h = new Huffman(seed);
        MinPQ<Huffman.Node> q = h.getQueue();
        Huffman.Node n = q.extractMin();
        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());
        //lc
        assertEquals(1.0 / 3, n.getLeftChild().getFreq(), 0.01);
        assertEquals('c', n.getLeftChild().getLetter());

        //(c, 1/3)
        //rc
        assertEquals(2.0 / 3, n.getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getLetter());
        //rc.lc
        assertEquals(1.0 / 3, n.getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals('a', n.getRightChild().getLeftChild().getLetter());

        //(a, 1/3)

        //rc.rc
        assertEquals(1.0 / 3, n.getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals('b', n.getRightChild().getRightChild().getLetter());

        //(b, 1/3)
    }

    @Test
    public void testBuildFromSeed4Chars() {

        String seed = "abcb";
        Huffman h = new Huffman(seed);
        MinPQ<Huffman.Node> q = h.getQueue();
        //root
        Huffman.Node n = q.extractMin();
        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());
        //lc
        assertEquals(2.0 / 4, n.getLeftChild().getFreq(), 0.01);
        assertEquals('b', n.getLeftChild().getLetter());

        //(b, 1/4)
        //rc
        assertEquals(2.0 / 4, n.getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getLetter());
        //rc.lc
        assertEquals(1.0 / 4, n.getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals('a', n.getRightChild().getLeftChild().getLetter());


        //rc.rc
        assertEquals(1.0 / 4, n.getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals('c', n.getRightChild().getRightChild().getLetter());


    }

    @Test
    public void testBuildFromSeed4CharsSame() {

        String seed = "aaabbbccdd";
        Huffman h = new Huffman(seed);
        MinPQ<Huffman.Node> q = h.getQueue();
        //root
        Huffman.Node n = q.extractMin();
        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());
        //lc
        assertEquals(2.0 / 5, n.getLeftChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getLeftChild().getLetter());


        //lc.lc
        assertEquals(1.0 / 5, n.getLeftChild().getLeftChild().getFreq(), 0.01);
        assertEquals('c', n.getLeftChild().getLeftChild().getLetter());


        //lc.rc
        assertEquals(1.0 / 5, n.getLeftChild().getRightChild().getFreq(), 0.01);
        assertEquals('d', n.getLeftChild().getRightChild().getLetter());


        //rc
        assertEquals(3.0 / 5, n.getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getLetter());


        //rc.lc
        assertEquals(3.0 / 10, n.getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals('a', n.getRightChild().getLeftChild().getLetter());


        //rc.rc
        assertEquals(3.0 / 10, n.getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals('b', n.getRightChild().getRightChild().getLetter());

    }

    @Test
    public void testBuildFromSeed8() {

        String seed = "aabcddefgh";
        Huffman h = new Huffman(seed);
        MinPQ<Huffman.Node> q = h.getQueue();

        //root "ghad bcef"
        Huffman.Node n = q.extractMin();
        assertEquals(1.0, n.getFreq(), 0.01);
        assertEquals((char) -1, n.getLetter());

        //rc "ghad"
        assertEquals(0.6, n.getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getLetter());


        //rc.lc "gh"
        assertEquals(0.2, n.getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getLeftChild().getLetter());

        //rc.lc.lc "g"
        assertEquals(0.1, n.getRightChild().getLeftChild().getLeftChild().getFreq(), 0.01);
        assertEquals('g', n.getRightChild().getLeftChild().getLeftChild().getLetter());


        //rc.lc.rc "h"
        assertEquals(0.1, n.getRightChild().getLeftChild().getRightChild().getFreq(), 0.01);
        assertEquals('h', n.getRightChild().getLeftChild().getRightChild().getLetter());


        //rc.rc "ad"
        assertEquals(0.4, n.getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getRightChild().getRightChild().getLetter());


        //lc "bcef", freq 0.4
        assertEquals(0.4, n.getLeftChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getLeftChild().getLetter());


        //lc.lc "bc"
        assertEquals(0.2, n.getLeftChild().getLeftChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getLeftChild().getLeftChild().getLetter());

        //lc.lc.lc "b"
        assertEquals(0.1, n.getLeftChild().getLeftChild().getLeftChild().getFreq(), 0.01);
        assertEquals('b', n.getLeftChild().getLeftChild().getLeftChild().getLetter());


        //lc.lc.rc "c"
        assertEquals(0.1, n.getLeftChild().getLeftChild().getRightChild().getFreq(), 0.01);
        assertEquals('c', n.getLeftChild().getLeftChild().getRightChild().getLetter());

        //lc.rc "ef"
        assertEquals(0.2, n.getLeftChild().getRightChild().getFreq(), 0.01);
        assertEquals((char) -1, n.getLeftChild().getRightChild().getLetter());


        //lc.rc.lc "e"
        assertEquals(0.1, n.getLeftChild().getRightChild().getLeftChild().getFreq(), 0.01);
        assertEquals('e', n.getLeftChild().getRightChild().getLeftChild().getLetter());


        //lc.rc.rc "f"
        assertEquals(0.1, n.getLeftChild().getRightChild().getRightChild().getFreq(), 0.01);
        assertEquals('f', n.getLeftChild().getRightChild().getRightChild().getLetter());

    }

    //------------------testing for decompress
    @Test (expected = IllegalArgumentException.class)
    public void testDcpNull() {
        Huffman h = new Huffman("abc");
        h.decompress(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDcpNot0Or1() {
        Huffman h = new Huffman("abc");
        h.decompress("3");
    }

    @Test
    public void testDcpEmpty() {
        Huffman h = new Huffman("abc");
        assertEquals("", h.decompress(""));

    }

    //test on weird
    @Test (expected = IllegalArgumentException.class)
    public void testDcpInvalidBit() {
        Huffman h = new Huffman("abc");
        h.decompress("0101");
    }

    //test on weird input 2
    @Test (expected = IllegalArgumentException.class)
    public void testDcpInvalidBit2() {
        Huffman h = new Huffman("abbc");
        h.decompress("100111");
    }
    //test on weird input 3
    @Test (expected = IllegalArgumentException.class)
    public void testDcpInvalidBit3() {
        Huffman h = new Huffman("abbc");
        h.decompress("010101");
    }

    //test on weird input 4
    @Test (expected = IllegalArgumentException.class)
    public void testDcpInvalidBit4() {
        Huffman h = new Huffman("abbc");
        h.decompress("1100111");
    }

    //test on weird input 5
    @Test (expected = IllegalArgumentException.class)
    public void testDcpInvalidBit5() {
        Huffman h = new Huffman("aaabbcd");
        h.decompress("1101011011");
    }
    //test on weird input 6
    @Test (expected = IllegalArgumentException.class)
    public void testDcpInvalidBit6() {
        Huffman h = new Huffman("aaabbcd");
        h.decompress("01001");
    }

    //test on other inputs
    @Test
    public void testDcpValidTest1() {
        Huffman h = new Huffman("abbc");
        h.decompress("10");
        assertEquals("a", h.decompress("10"));
        assertEquals("c", h.decompress("11"));
        assertEquals("ca", h.decompress("1110"));
        assertEquals("ccb", h.decompress("11110"));
        assertEquals("bbba", h.decompress("00010"));
        assertEquals("cbbc", h.decompress("110011"));
        assertEquals("b", h.decompress("0"));
    }

    //test on other inputs
    @Test
    public void testDcpValidTest2() {
        Huffman h = new Huffman("abc");
        assertEquals("c", h.decompress("0"));
        assertEquals("b", h.decompress("11"));
        assertEquals("ca", h.decompress("010"));
        assertEquals("ccb", h.decompress("0011"));
        assertEquals("bbba", h.decompress("11111110"));
        assertEquals("cbbc", h.decompress("011110"));
        assertEquals("a", h.decompress("10"));
    }

    //test on other inputs
    @Test
    public void testDcpValidTest3() {
        Huffman h = new Huffman("aaabbbccdd");
        assertEquals("c", h.decompress("00"));
        assertEquals("b", h.decompress("11"));
        assertEquals("ca", h.decompress("0010"));
        assertEquals("ccb", h.decompress("000011"));
        assertEquals("bbba", h.decompress("11111110"));
        assertEquals("cbbc", h.decompress("00111100"));
        assertEquals("dac", h.decompress("011000"));

        assertEquals("abcd", h.decompress("10110001"));

        assertEquals("a", h.decompress("10"));
        assertEquals("d", h.decompress("01"));
    }

    //test on other inputs
    @Test
    public void testDcpValidTest4() {
        Huffman h = new Huffman("aaabbcd");
        assertEquals("c", h.decompress("110"));
        assertEquals("b", h.decompress("10"));
        assertEquals("ca", h.decompress("1100"));
        assertEquals("ccb", h.decompress("11011010"));
        assertEquals("bbba", h.decompress("1010100"));
        assertEquals("cbbc", h.decompress("1101010110"));
        assertEquals("dac", h.decompress("1110110"));

        assertEquals("abcd", h.decompress("010110111"));
        assertEquals("dacb", h.decompress("111011010"));
        assertEquals("a", h.decompress("0"));
        assertEquals("d", h.decompress("111"));
    }

    //--------------------test on compress-------------
    @Test (expected = IllegalArgumentException.class)
    public void testCompressInvalid() {
        Huffman h = new Huffman("aaabbcd");
        h.compress(null);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testCompressInvalid2() {
        Huffman h = new Huffman("aaabbcd");
        h.compress("abcde");
    }

    @Test
    public void testCompressInput1() {

        Huffman h = new Huffman("abbc");
        h.compress("a");
        assertEquals("10", h.compress("a"));
        assertEquals("11", h.compress("c"));
        assertEquals("1110", h.compress("ca"));
        assertEquals("11110", h.compress("ccb"));
        assertEquals("00010", h.compress("bbba"));
        assertEquals("110011", h.compress("cbbc"));
        assertEquals("0", h.compress("b"));
    }

    @Test
    public void testCompressInput2() {
        Huffman h = new Huffman("abc");
        assertEquals("0", h.compress("c"));
        assertEquals("11", h.compress("b"));
        assertEquals("010", h.compress("ca"));
        assertEquals("0011", h.compress("ccb"));
        assertEquals("11111110", h.compress("bbba"));
        assertEquals("011110", h.compress("cbbc"));
        assertEquals("10", h.compress("a"));
    }

    //test on other inputs
    @Test
    public void testCompressInput3() {
        Huffman h = new Huffman("aaabbbccdd");
        assertEquals("00", h.compress("c"));
        assertEquals("11", h.compress("b"));
        assertEquals("0010", h.compress("ca"));
        assertEquals("000011", h.compress("ccb"));
        assertEquals("11111110", h.compress("bbba"));
        assertEquals("00111100", h.compress("cbbc"));
        assertEquals("011000", h.compress("dac"));

        assertEquals("10110001", h.compress("abcd"));

        assertEquals("10", h.compress("a"));
        assertEquals("01", h.compress("d"));
    }

    //test on other inputs
    @Test
    public void testCompressInput4() {
        Huffman h = new Huffman("aaabbcd");
        assertEquals("110", h.compress("c"));
        assertEquals("10", h.compress("b"));
        assertEquals("1100", h.compress("ca"));
        assertEquals("11011010", h.compress("ccb"));
        assertEquals("1010100", h.compress("bbba"));
        assertEquals("1101010110", h.compress("cbbc"));
        assertEquals("1110110", h.compress("dac"));

        assertEquals("010110111", h.compress("abcd"));
        assertEquals("111011010", h.compress("dacb"));
        assertEquals("0", h.compress("a"));
        assertEquals("111", h.compress("d"));
    }

    //size 8 tree
    @Test
    public void testCompressInput8() {
        String seed = "aabcddefgh";
        Huffman h = new Huffman(seed);
        assertEquals("001", h.compress("c"));
        assertEquals("000", h.compress("b"));
        assertEquals("010", h.compress("e"));
        assertEquals("011", h.compress("f"));
        assertEquals("100", h.compress("g"));
        assertEquals("101", h.compress("h"));
        assertEquals("001110", h.compress("ca"));
        assertEquals("001001000", h.compress("ccb"));
        assertEquals("000000000110", h.compress("bbba"));
        assertEquals("001000000001", h.compress("cbbc"));
        assertEquals("111110001", h.compress("dac"));

        assertEquals("110000001111", h.compress("abcd"));
        assertEquals("111110001000", h.compress("dacb"));
        assertEquals("010011100", h.compress("efg"));
        assertEquals("110", h.compress("a"));
        assertEquals("111", h.compress("d"));

    }

    //-----------test on compression ratio--------------------
    @Test
    public void testCompRatio1() {
        String seed = "aabcddefgh";
        Huffman h = new Huffman(seed);
        assertEquals("001", h.compress("c"));
        assertEquals("000", h.compress("b"));
        assertEquals("010", h.compress("e"));
        assertEquals("011", h.compress("f"));
        assertEquals("100", h.compress("g"));
        assertEquals("101", h.compress("h"));
        assertEquals(3 / 16.0, h.compressionRatio(), 0.01);

    }

    @Test
    public void testCompRatio2() {
        String seed = "abbc";
        Huffman h = new Huffman(seed);
        assertEquals("10", h.compress("a"));
        assertEquals("11", h.compress("c"));
        assertEquals("1110", h.compress("ca"));
        assertEquals("11110", h.compress("ccb"));
        assertEquals("00010", h.compress("bbba"));
        assertEquals("110011", h.compress("cbbc"));
        assertEquals("0", h.compress("b"));
        assertEquals(25 / 16.0 / 16, h.compressionRatio(), 0.01);

    }

    @Test
    public void testCompRatio3() {
        String seed = "abc";
        Huffman h = new Huffman(seed);
        assertEquals("0", h.compress("c"));
        assertEquals("11", h.compress("b"));
        assertEquals("010", h.compress("ca"));
        assertEquals("0011", h.compress("ccb"));
        assertEquals("11111110", h.compress("bbba"));
        assertEquals("011110", h.compress("cbbc"));
        assertEquals("10", h.compress("a"));
        assertEquals(26 / 16.0 / 16, h.compressionRatio(), 0.01);

    }

    //---------------test on expectedEncodingLength
    @Test
    public void testEncLength1() {
        String seed = "abbc";
        Huffman h = new Huffman(seed);
        assertEquals(1.5, h.expectedEncodingLength(), 0.01);
    }

    @Test
    public void testEncLength2() {
        String seed = "aaabbcd";
        Huffman h = new Huffman(seed);
        assertEquals(13.0 / 7, h.expectedEncodingLength(), 0.01);
    }


}