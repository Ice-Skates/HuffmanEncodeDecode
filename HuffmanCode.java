/*
 * Nate Snyder
 * P3: HuffmanCode
 * 3/8/24
 * 
 */



 import java.util.*;
 import java.io.*;
 
 /* HuffmanCode class
  * 
  * FINISH THIS COMMENT
  * This is the HuffmanCode class. It contains method that compress and deco
  * 
  * 
  */
 
 public class HuffmanCode {
 
     private HuffmanNode root;
 
 
     /* HuffmanCode (array constructor)
     *
     * Behavior:
         - Constructs a binary tree and declares the root field. The tree is
         sorted from least frequent to most frequent characters.
 
 
     * Return:
          -N/A
 
     * Parameters:
         - int[]: This array is responsible for storing the frequencies of each character
         The index of each value in the array corresponds to it's ascii code.
 
 
     * Exceptions:
         - n/a
 
     */
 
     public HuffmanCode(int[] frequencies) {
 
 
         Queue<HuffmanNode> pq = new PriorityQueue<>();
 
         for (int i = 0; i < frequencies.length; i++) {
             if (frequencies[i] > 0) {
                 HuffmanNode node = new HuffmanNode((char) i, frequencies[i]);
                 pq.add(node);
             } 
         }
 
 
         if (pq.size() > 0) {
             this.root = makeHuffmanTree(pq);
         }
 
         else {
             this.root = null;
         }
 
     }
 
 
     /* makeHuffmanTree
 
     * Behavior:
         - This method is repsonsible for the recursive process of creating the tree.
         
     * Return:
         - returns the root node 
         
     * Parameters:
         - Queue<HuffmanNode>: The priority queue that contains the node for each character
         that occurs.
 
     * Exceptions:
         - n/a
     * 
     * 
     */
 
 
     private HuffmanNode makeHuffmanTree(Queue<HuffmanNode> pq) {
 
         if (pq.size() == 1) {
             return pq.remove();
         }
 
         HuffmanNode left = pq.remove();
         HuffmanNode right = pq.remove();
 
         HuffmanNode combined = new HuffmanNode('\0', left.frequency + right.frequency);
 
         combined.left = left;
         combined.right = right;
         pq.add(combined);
         return makeHuffmanTree(pq);
 
     }
         
         
 
     /* HuffmanCode (Scanner constructor)
     * Behavior:
     *  - creates a tree for the huffManCode read from a file.
     * Return:
     *  -N/A
     * Parameters:
     *  - Scanner: The scanner to read the file for the information of the huffManCode.
     * Exceptions:
     *  - N/A
     * 
     * 
     */
 
 
     public HuffmanCode(Scanner input){
         while(input.hasNextLine()){
             int ascii = Integer.parseInt(input.nextLine());
             String code = input.nextLine();
             root = add(ascii, code, root, 0);
        }
    }

    /* add 
     *
     * Behavior:
     *  - Takes the ascii number and code of a character from the file and 
     *  adds it to the tree
     * 
     * Return:
     *  - HuffmanNode: returns the new root node of the tree with the new node
     *  added.
     * 
     * 
     * Parameters:
     *  - ascii: The ascii code of the character being added to the tree
     *  - code: The code that distinguishes the frequency of the new character
     *  - overallRoot: The root of the tree for when the method is called
     *  - index: The index of the character being added.
     * 
     * 
     * Exceptions:
     *  -  N/A
     *
     */
 
 
 
     private HuffmanNode add(int ascii, String code, HuffmanNode overallRoot, int index) {
 
         if(code.length() <= index){
             overallRoot = new HuffmanNode ((char)ascii, 0);
         } 
         
         else {
             if (overallRoot == null) {
                 overallRoot = new HuffmanNode ('\0', 0);
             } 
 
             if(code.charAt(index) == '0'){
                 overallRoot.left = add(ascii, code, overallRoot.left, index + 1);
             } else if(code.charAt(index) == '1'){
                 overallRoot.right = add(ascii, code, overallRoot.right, index + 1);
             }
         }
         return overallRoot;
     }
 
      
 
 
     /* save
     * Behavior:
     *  - calls the recusrive private method to save the huffManCode to a file.
     * Return:
     *  - n/a
     * Parameters:
     *  - output: The print stream necessary for printing the code to a file
     * Exceptions:
     * - N/A
     * 
     * 
     */
 
 
     public void save(PrintStream output) {
         /* This method should store the current Huffman Code
          * to the given output stream in the standard format (see below).
          */
         save(output, "", root);
     }
 
 
     /* save 
     * Behavior:
     *  - the method that saves the HuffmanCode to a file.
     * Return:
     *  - n/a
     * Parameters:
     *  - output: The print stream necessary for printing the code to a given file
     *  - code: The binary code of the node used to perseve information of the node in the file.
     *  - node: The node that the method is currently testing.
     * Exceptions:  
     *  - N/A
     * 
     * 
     */
 
 
     private void save(PrintStream output, String code, HuffmanNode node) {
         if (node == null) {
             
         }
 
         //leaf node
         else if (node.character != '\0') {
             output.println((int)node.character);
             output.println(code);
         }
 
         else {
             save(output, code + "0", node.left);
             save(output, code + "1", node.right);
         }
 
     }
 
 
     /* Translate
     * Behavior:
     *  - Calls the recursive method that translates the bits into the corresponding characters
     * Return:
     *  - n/a
     * Parameters:
     *  - input: The input of bits that represents the chracters 
     *  - output: The characters will be written to by using the output print stream
     * Exceptions:
     *  - n/a
     * 
     * 
     */
 
 
     public void translate(BitInputStream input, PrintStream output) {
         translate(input, output, root);
     }
 
 
     /*
     * Behavior:
     *  - translates an input of bits into the correspoding characters. 
     *  - this is done by reading the bits and printing the characters to a file.
     * Return:
     *  - n/a
     * Parameters:
     *  - input: The input of bits that represents the chracters 
     *  - output: The characters will be written to by using the output print stream
     *  - root: The root node that is used
     * Exceptions:
     *  - n/a
     * 
     * 
     */
 
 
     private void translate(BitInputStream input, PrintStream output, HuffmanNode node) {
         HuffmanNode currentNode = node;
 
         while (input.hasNextBit()) {
             int bit = input.nextBit();
 
             if (bit == 0) {
                 currentNode = currentNode.left;
             } else if (bit == 1) {
                 currentNode = currentNode.right;
             }
 
             if (currentNode.right == null && currentNode.left == null) {
                 output.write(currentNode.character);
                 currentNode = node;
             }
         }
     }
 
     /* HuffmanNode subclass
     * Behavior:
     *  - defines the characteristics of the Huffman nodes.
     *  - each node is a representation of a character. 
     *  - These nodes are used to build the Huffman tree.
     * 
     * Implements: 
     *  - Comparable: This is used to order the nodes by frequency when they are stored
     *  to create the huffman tree.
     * 
     * fields:
     *  - character: The character for the given node. 
     *  - frequency: How often the character occurs.
     *  - left: The node to the left of this node, null if there's not left node
     *  - right: The node to the right of this node, null if there's not right node
     * 
     */
 
 
     private static class HuffmanNode implements Comparable<HuffmanNode> {
         public final char character;
         public final int frequency;
         public HuffmanNode left;
         public HuffmanNode right;
 
 
         
 
         /* HuffmanNode
         * Behavior:
         *  - Creates a new node with the given character and it's frequency. 
         * Parameters:
         *  - character: The character for the node
         *  - frequency: The amount of times the character occurs. 
         * 
         * 
         */
         public HuffmanNode(char character, int frequency) {
             this.character = character;
             this.frequency = frequency;
         }
 
         /* compareTo
         * 
         * Behavior:
         *  - compares the frequency of two characters. This is used for the
         *  comparable.
         * Return:
         *  - returns the difference in freqeuncys, if it's negative then than designated 
         *  character is more frequent, if positive than the current one is more frequent
         *  if 0 then they occurs the same amount.
         * Parameters:
         *  - o: The node's frequency that will be compared to the current node.
         * 
         * 
         */
 
         @Override
         public int compareTo(HuffmanNode o) {
             return this.frequency - o.frequency;
         }        
     }
 }
 
 
