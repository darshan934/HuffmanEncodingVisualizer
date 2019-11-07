# Huffman Encoding Algorithm Visualizer

#### This Repository contains a Visualizer of the Tree of Encoding of Huffman's Algorithm.

This project aims at coding and visualizing Huffman Algorithm to optimally encode strings formed by symbols of a self-created alphabet using Huffman Encoding. It supports compression and decompression of strings (aka, the conversion between the string and the encoded bits), and calculates the average compression ratio (aka, the ratio between the original string’s bits length and the bits length after compression) to see how well it performs in the compression.

It also provides a Visualizer to visualize the encoding process, which shows the Tree that represents the encoding scheme. From the root, going left-branch would mean adding a “0” to the encoded string, and going right-branch means adding a “1”. At the leaf positions are the letters from the alphabet. This allows us to infer the encoding scheme of all letters in the alphabet. 
