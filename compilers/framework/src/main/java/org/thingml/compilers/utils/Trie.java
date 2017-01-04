/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sintef
 */
public class Trie<T> {
    public TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(T el, String name) {
        HashMap<Character, TrieNode> children = root.children;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            TrieNode t;
            if (children.containsKey(c)) {
                t = children.get(c);
            } else {
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.children;

            //set leaf node
            if (i == name.length() - 1) {
                t.isLeaf = true;
                t.el = el;
            }
        }
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);

        if (t != null && t.isLeaf)
            return true;
        else
            return false;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if (searchNode(prefix) == null)
            return false;
        else
            return true;
    }

    public TrieNode searchNode(String str) {
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            } else {
                return null;
            }
        }

        return t;
    }

    public class TrieNode<T> {
        public char c;
        public HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        public boolean isLeaf;
        public T el;

        public TrieNode() {
        }

        public TrieNode(char c) {
            this.c = c;
        }

        public Collection<TrieNode> getChildren() {
            return children.values();
        }

        public void print(int tab) {
            for (int i = 0; i < tab; i++) {
                System.out.print("-");
            }
            System.out.println(c);
            for (TrieNode t : children.values()) {
                t.print(tab + 1);
            }
        }
    }
}
