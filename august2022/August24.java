package com.fang.leetcode.august2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author letian
 * @version 1.0
 * 演示bfs的双端操作，这里使用两个哈希表来代替bfs中的队列
 * 题目的链接https://leetcode.cn/problems/word-ladder/
 */
@SuppressWarnings("all")
public class August24 {
    /**
     *
     * @param beginWord 开始单词
     * @param endWord 目标单词
     * @param wordList 中间结果的集合，只有在这个集合中的才能作为中间转换的跳板
     * @return 最小的转换次数
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>();
        for (String s : wordList) {
            dict.add(s);
        }
        if (!dict.contains(endWord)) {
            return 0;
        }
        // 检查过的set
        Set<String> seen = new HashSet<>();
        // 从开始单词进行搜索的集合
        Set<String> begin = new HashSet<>();
        // 从目标单词进行搜索的集合
        Set<String> end = new HashSet<>();
        // 加入目标单词和开始单词
        begin.add(beginWord);
        end.add(endWord);
        int step = 1;
        while (!end.isEmpty() && !begin.isEmpty()) {
            step++;
            if (begin.size() > end.size()) {
                Set<String> temp = begin;
                begin = end;
                end = temp;
            }
            Set<String> next = new HashSet<>();
            for (String s : begin) {
                if (check(next, end, dict, s, seen)) {
                    return step;
                }
            }
            // 原来的begin不要了，使用下一层的next集合
            begin = next;
        }
        return 0;

    }

    public boolean check(Set<String> next, Set<String> end, Set<String> dict, String s, Set<String> seen) {
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            for (char temp = 'a'; temp <= 'z'; temp++) {
                if (temp == c) {
                    continue;
                }
                arr[i] = temp;
                String ele = new String(arr);
                // 首先中间集合必须有ele单词
                if (dict.contains(ele)) {
                    // 如果另一个集合中有这个新单词，那么就直接找到了一条路
                    // 可以直接返回了
                    if (end.contains(ele)) {
                        return true;
                    }
                    // 加入下一层集合的条件就是没有被访问过，而且必须被中间过程单词表包含
                    if (dict.contains(ele) && !seen.contains(ele)) {
                        seen.add(ele);
                        next.add(ele);
                    }
                }

            }
            // 单词还原
            arr[i] = c;
        }
        return false;
    }
}
