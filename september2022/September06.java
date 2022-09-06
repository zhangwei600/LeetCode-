package com.fang.leetcode.september2022;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 九月六日的题解
 * 每日一题是一个困难题，具体的链接如下
 * <a href="https://leetcode.cn/problems/count-unique-characters-of-all-substrings-of-a-given-string/">...</a>
 * 另一个是二分查找的几个经典题目，开启二分查找训练的计划
 */
public class September06 {
    public static void main(String[] args) {
        September06 temp = new September06();
        // 一个字符串给你，让你输出每个子串中只出现一次字符的数量总和
        // 如ABA的子串有A、AB、ABA、B、BA、A，对应的是1，2，1，1，2，1。所以总和就是8
        System.out.println(temp.uniqueLetterString("LEETCODE"));
        System.out.println(temp.uniqueLetterString("ABA"));
    }


    public int uniqueLetterString(String s) {
        // 当前字符出现的位置
        int[] curIndex = new int[26];
        // 当前字符上一次出现的位置
        int[] lastIndex = new int[26];
        // 这个是开头给每一个字符添加上一次出现的位置时候，使用-1
        Arrays.fill(curIndex, -1);
        Arrays.fill(lastIndex, -1);
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = c - 'A';
            if (curIndex[index] != -1) {
                // 这个字符在这一段子串中提供的贡献值就是
                ans += (i - curIndex[index]) * (curIndex[index] - lastIndex[index]);
            }
            // 然后滚动更新
            lastIndex[index] = curIndex[index];
            curIndex[index] = i;
        }

        // 最后别忘了将最后一个字符出现的位置改为s.length()，这个位置可以认为所有的字符都
        // 在这里出现了
        for (int i = 0; i < 26; i++) {
            ans += (curIndex[i] - lastIndex[i]) * (s.length() - curIndex[i]);
        }
        return ans;
    }


}
