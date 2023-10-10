package com.example.gallery.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@RequiresApi(api = Build.VERSION_CODES.N)
public class HuffmanAlgorithm {
    private static Node root;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Node buildHuffmanTree(String text)
    {
        Map<Character, Integer> freq = new HashMap<>();
        for (int i = 0 ; i < text.length(); i++) {
            if (!freq.containsKey(text.charAt(i))) {
                freq.put(text.charAt(i), 0);
            }
            freq.put(text.charAt(i), freq.get(text.charAt(i)) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(
                (l, r) -> l.freq - r.freq);

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() != 1)
        {
            Node left = pq.poll();
            Node right = pq.poll();

            int sum = left.freq + right.freq;
            pq.add(new Node(' ', sum, left, right));
        }

        Node root = pq.peek();

        return root;

    }
    public static String Encryption(String text) {

        root=buildHuffmanTree(text);
        Map<Character, String> huffmanCode = new HashMap<>();
        Huffman.encode(root, "", huffmanCode);

        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < text.length(); i++) {
            sb.append(huffmanCode.get(text.charAt(i)));
        }
        return sb.toString();
    }

    public static void Decryption(String code) {
        StringBuilder sb=new StringBuilder(code);
        int index = -1;
        while (index < sb.length() - 2) {
            index = Huffman.decode(root, index, sb);
        }
    }

    public static class Node
    {
        char ch;
        int freq;
        Node left = null, right = null;

        Node(char ch, int freq)
        {
            this.ch = ch;
            this.freq = freq;
        }

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }
    static class Huffman {
        public static void encode(Node root, String str,
                                  Map<Character, String> huffmanCode) {
            if (root == null)
                return;

            // found a leaf node
            if (root.left == null && root.right == null) {
                huffmanCode.put(root.ch, str);
            }


            encode(root.left, str + "0", huffmanCode);
            encode(root.right, str + "1", huffmanCode);
        }

        public static int decode(Node root, int index, StringBuilder sb) {
            if (root == null)
                return index;

            if (root.left == null && root.right == null) {
                return index;
            }

            index++;

            if (sb.charAt(index) == '0')
                index = decode(root.left, index, sb);
            else
                index = decode(root.right, index, sb);

            return index;
        }
    }
}
