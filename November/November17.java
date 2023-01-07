package com.fang.leetcode.November;

import java.util.ArrayList;
import java.util.List;

/**
 * @author letian
 * @version 1.0
 * 演示11月17日的每日一题
 * 题意就是在一个字符串数组中，判断有多少字符串是一个字符串的子序列，返回这个数字
 */
public class November17 {
    public static void main(String[] args) {
        System.out.println(new November17().process("aa", new String[]{"a", "aa"}));
    }
    static class Node {
        int index;
        int wordIndex;

        // 每一个单词保存的是当前指针指向的位置
        // 以及这个单词在words数组中的下标
        public Node(int index, int wordIndex) {
            this.index = index;
            this.wordIndex = wordIndex;
        }
    }
    public int process(String s, String[] words) {
        @SuppressWarnings("all")
        List<Node>[] arr = new List[26];
        for (int i = 0; i < 26; i++) {
            arr[i] = new ArrayList<>();
        }
        int index = 0;
        for (String str : words) {
            arr[str.charAt(0) - 'a'].add(new Node(0, index++));
        }
        int ans = 0;
        for (char c : s.toCharArray()) {
            List<Node> old = arr[c - 'a'];
            // 原来的数组不要了，换一个
            arr[c - 'a'] = new ArrayList<>();
            for (Node node : old) {
                // 单词指针往后移动一位
                node.index++;
                if (node.index == words[node.wordIndex].length()) {
                    ans++;
                } else {
                    // 将单词添加到对应的字符对应的列表中
                    arr[words[node.wordIndex].charAt(node.index) - 'a'].add(node);
                }
            }
        }
        return ans;
    }
}
