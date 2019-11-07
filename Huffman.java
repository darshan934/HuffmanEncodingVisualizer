
import java.util.Map;
import java.util.*;


/**
 * Implements construction, encoding, and decoding logic of the Huffman coding algorithm. Characters
 * not in the given seed or alphabet should not be compressible, and attempts to use those
 * characters should result in the throwing of an {@link IllegalArgumentException} if used in {@link
 * #compress(String)}.
 *
 */
public class Huffman {
    private List<String> inputs;
    private List<String> outputs;
    private Map<Character, Integer> alph;

    private MinPQ<Node> queue;

    //helpers to test minPQ;

    MinPQ<Node> getQueue() {
        return queue;
    }

    //inner class, node
    class Node implements Comparable<Node> {
        private double freq;
        private char letter;
        private Node leftChild;
        private Node rightChild;

        Node(double freq, char letter) {
            this.freq = freq;
            this.letter = letter;
            leftChild = null;
            rightChild = null;
        }

        //public functions to modify the lc and rc info of the node

        void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        boolean isLeaf() {
            return leftChild == null && rightChild == null;
        }

        public int compareTo(Node n) {
            //if both leaf, break the tie by comparing freq, then letter size
            if (this.getFreq() < n.getFreq()) {
                return -1;
            } else if (this.getFreq() > n.getFreq()) {
                return 1;
            } else {
                if (this.isLeaf() && n.isLeaf()) {
                    if ((int) this.getLetter() < (int) n.getLetter()) {
                        return -1;
                    } else if ((int) this.getLetter() > (int) n.getLetter()) {
                        return 1;
                    } else {
                        return 0;
                    }
                    //leaf is bigger than node
                    //2 nodes: recurse until its smallest leaf, compare
                } else if (this.isLeaf() && !n.isLeaf()) {
                    return -1;
                } else if (!this.isLeaf() && n.isLeaf()) {
                    return 1;
                } else {
                    Node mySmallest = this.getSmallestLeaf();
                    Node nSmallest = n.getSmallestLeaf();
                    return mySmallest.compareTo(nSmallest);
                }
            }
        }

        Node getSmallestLeaf() {
            return getSmallestLeafHelper(this);
        }

        //helper to find the smallest child
        Node getSmallestLeafHelper(Node n) {
            if (n.isLeaf()) {
                return n;
            } else {
                return n.getLeftChild();
            }
        }

        double getFreq() {
            return freq;
        }
        char getLetter() {
            return letter;
        }

        Node getLeftChild() {
            return leftChild;
        }
        Node getRightChild() {
            return rightChild;
        }

        public String toString() {
            return ("(" + getLetter() + ", " + getFreq() + " )");
        }
    }



    /**
     * Constructs a {@code Huffman} instance from a seed string, from which to deduce the alphabet
     * and corresponding frequencies.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param seed the String from which to build the encoding
     * @throws IllegalArgumentException seed is null, seed is empty, or resulting alphabet only has
     * 1 character
     */
    public Huffman(String seed) {
        if (seed == null) {
            throw new IllegalArgumentException("seed is null");
        }
        if (seed.length() == 0) {
            throw new IllegalArgumentException("seed is empty");
        }
        boolean allSame = true;
        for (int i = 0; i < seed.length() - 1; i++) {
            if (seed.charAt(i) != seed.charAt(i + 1)) {
                allSame = false;
                break;
            }
        }
        if (allSame) {
            throw new IllegalArgumentException("seed creates an alphabet of 1 character");
        }

        Map<Character, Integer> alphabet = createMapFromSeed(seed);
        alph = alphabet;
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();
        //initialize queue
        queue = new MinPQ<Node>();

        Set<Map.Entry<Character, Integer>> setOfMappings = alphabet.entrySet();

        //get the total num of letters
        int numOfLetters = 0;
        for (Map.Entry<Character, Integer> entry : setOfMappings) {
            numOfLetters += entry.getValue();
        }
        for (Map.Entry<Character, Integer> entry : setOfMappings) {
            /* initializing leaf nodes for each entry,
            *putting each char as letters, and each int/total as freq
            */
            Node n = new Node(entry.getValue() / (double) numOfLetters, entry.getKey());
            queue.add(n);
        }

        //using the pqueue to construct a binary tree
        while (queue.size() > 1) {
            Node n1 = queue.extractMin();
            Node n2 = queue.extractMin();
            Node parent = new Node(n1.getFreq() + n2.getFreq(), (char) -1);
            parent.setLeftChild(n1);
            parent.setRightChild(n2);
            queue.add(parent);
        }


    }

    //helper, need to test
    static Map<Character, Integer> createMapFromSeed(String seed) {
        Map<Character, Integer> alphabet = new HashMap<Character, Integer>();
        for (int i = 0; i < seed.length(); i++) {
            if (alphabet.containsKey(seed.charAt(i))) {
                alphabet.put(seed.charAt(i), alphabet.get(seed.charAt(i)) + 1);
            } else {
                alphabet.put(seed.charAt(i), 1);
            }
        }
        return alphabet;
    }

    /**
     * Constructs a {@code Huffman} instance from a frequency map of the input alphabet.
     * <p/>
     * Do NOT modify this constructor header.
     *
     * @param alphabet a frequency map for characters in the alphabet
     * @throws IllegalArgumentException if the alphabet is null, empty, has fewer than 2 characters,
     * or has any non-positive frequencies
     */
    public Huffman(Map<Character, Integer> alphabet) {
        if (alphabet == null) {
            throw new IllegalArgumentException("alphabet null");
        }
        if (alphabet.size() < 2) {
            throw new IllegalArgumentException("alphabet empty");
        }
        Collection<Integer> li = new ArrayList<Integer>();
        for (Integer i : li = alphabet.values()) {
            if (i <= 0) {
                throw new IllegalArgumentException("non-positive freq");
            }
        }
        alph = alphabet;
        inputs = new ArrayList<String>();
        outputs = new ArrayList<String>();

        //initialize queue
        queue = new MinPQ<Node>();
        Set<Map.Entry<Character, Integer>> setOfMappings = alphabet.entrySet();

        //get the total num of letters
        int numOfLetters = 0;
        for (Map.Entry<Character, Integer> entry : setOfMappings) {
            numOfLetters += entry.getValue();
        }
        for (Map.Entry<Character, Integer> entry : setOfMappings) {
            /* initializing leaf nodes for each entry,
            * putting each char as letters, and each int/total as freq
            * */
            Node n = new Node(entry.getValue() / (double) numOfLetters, entry.getKey());
            queue.add(n);
        }

        //using the pqueue to construct a binary tree
        while (queue.size() > 1) {
            Node n1 = queue.extractMin();
            Node n2 = queue.extractMin();
            Node parent = new Node(n1.getFreq() + n2.getFreq(), (char) -1);
            parent.setLeftChild(n1);
            parent.setRightChild(n2);
            queue.add(parent);
        }


    }

    /**
     * Compresses the input string.
     *
     * @param input the string to compress, can be the empty string
     * @return a string of ones and zeroes, representing the binary encoding of the inputted String.
     * @throws IllegalArgumentException if the input is null or if the input contains characters
     * that are not compressible
     */
    public String compress(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input null");
        }
        for (int i = 0; i < input.length(); i++) {
            if (!alph.containsKey(input.charAt(i))) {
                throw new IllegalArgumentException("input not in alphabet");
            }
        }
        inputs.add(input);
        StringBuilder outProducer = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            outProducer.append(compressHelper(queue.peek(), input.charAt(i),
                               new StringBuilder()).toString());

        }
        String out = outProducer.toString();
        outputs.add(out);
        return out;
    }


    StringBuilder compressHelper(Node n, char target, StringBuilder b) {
        String bitsSoFar = b.toString();
        if (n.isLeaf() && n.getLetter() == target) {
            return b;
        } else if (n.isLeaf() && n.getLetter() != target) {
            return null;
        } else {
            StringBuilder left = new StringBuilder();
            StringBuilder right = new StringBuilder();
            if (n.getLeftChild() != null) {
                left.append(bitsSoFar);
                left.append(0);
                left = compressHelper(n.getLeftChild(), target, left);

            }
            if (n.getRightChild() != null) {
                right.append(bitsSoFar);
                right.append(1);
                right = compressHelper(n.getRightChild(), target, right);
            }
            if (right != null && left == null) {
                return right;
            } else if (left != null && right == null) {
                return left;
            } else {
                return null;
            }
        }
    }

    /**
     * Decompresses the input string.
     *
     * @param input the String of binary digits to decompress, given that it was generated by a
     * matching instance of the same compression strategy
     * @return the decoded version of the compressed input string
     * @throws IllegalArgumentException if the input is null, or if the input contains characters
     * that are NOT 0 or 1, or input contains a sequence of bits that is not decodable
     */
    //unimplemented, still need to think
    public String decompress(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input null");
        }

        //also, if input contains a sequence that is not decodable, meaning is not in outputs set?
        /*
         *need another exception check! or throw within the functio
         */
        //empty string
        if (input.length() == 0) {
            String out = "";
            return out;
        } else {
            StringBuilder b = new StringBuilder();
            Node root = queue.peek();
            Node curNode = root;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '0') {
                    if (curNode.getLeftChild() != null) {
                        curNode = curNode.getLeftChild();
                    } else {
                        //ensures all invalid strings are ruled out?
                        throw new IllegalArgumentException("invalid");
                    }
                } else if (c == '1') {
                    if (curNode.getRightChild() != null) {
                        curNode = curNode.getRightChild();
                    } else {
                        throw new IllegalArgumentException("invalid");
                    }
                } else {
                    throw new IllegalArgumentException("contains non-0-or-1 char");
                }
                /*in the end, checks if some chars are unused or
                 *leads to a non-leaf node and stops halfway
                 */
                if (i == input.length() - 1 && !curNode.isLeaf()) {
                    throw new IllegalArgumentException("invalid");
                }
                if (curNode.isLeaf()) {
                    b.append(curNode.getLetter());
                    curNode = root;
                }
            }
            String out = b.toString();
            return out;
        }
    }





  /**
     * Computes the compression ratio so far. This is the length of all output strings from {@link
     * #compress(String)} divided by the length of all input strings to {@link #compress(String)}.
     * Assume that each char in the input string is a 16 bit int.
     *
     * @return the ratio of the total output length to the total input length in bits
     * @throws IllegalStateException if no calls have been made to {@link #compress(String)} before
     * calling this method
     */
    public double compressionRatio() {
        int totalInputLength = 0;
        int totalOutputLength = 0;
        for (String s : inputs) {
            totalInputLength += s.length();
        }
        for (String str : outputs) {
            totalOutputLength += str.length();
        }
        return totalOutputLength / (16.0 * totalInputLength);
    }

    /**
     * Computes the expected encoding length of an arbitrary character in the alphabet based on the
     * objective function of the compression.
     * 
     * The expected encoding length is simply the sum of the length of the encoding of each 
     * character multiplied by the probability that character occurs. 
     *
     * @return the expected encoding length of an arbitrary character in the alphabet
     */
    public double expectedEncodingLength() {
        Set<Map.Entry<Character, Integer>> set = alph.entrySet();
        int numOfLetters = 0;
        for (Map.Entry<Character, Integer> entry : set) {
            numOfLetters += entry.getValue();
        }
        double sum = 0.0;
        for (Map.Entry<Character, Integer> e : set) {
            String s = "" + e.getKey();
            sum += (double) e.getValue() / numOfLetters * compress(s).length();
        }
        return sum;
    }
}
