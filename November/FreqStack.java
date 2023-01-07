package com.fang.leetcode.November;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 演示11月30日的每日一题
 * 也就是这个月的最后一题,下面给出题解的链接
 * <a href="https://leetcode.cn/problems/maximum-frequency-stack/solutions/1998430/mei-xiang-ming-bai-yi-ge-dong-hua-miao-d-oich/">...</a>
 */
public class FreqStack {
    // 频率栈
    private final Map<Integer, Integer> freq;
    // 针对每个频率设置一个新栈
    private final Map<Integer, Deque<Integer>> group;
    // 维护最大频率
    private int maxFreq;

    public FreqStack() {
        freq = new HashMap<>();
        group = new HashMap<>();
        maxFreq = 0;
    }

    public void push(int val) {
        freq.put(val, freq.getOrDefault(val, 0) + 1);
        group.putIfAbsent(freq.get(val), new ArrayDeque<>());
        group.get(freq.get(val)).push(val);
        maxFreq = Math.max(maxFreq, freq.get(val));
    }

    public int pop() {
        // 得到最大频率栈的栈顶数字
        int val = group.get(maxFreq).peek();
        // 将这个数字的词频减1，方便后面再次添加这个数字的时候能够进入应该进入的词频栈
        freq.put(val, freq.get(val) - 1);
        group.get(maxFreq).pop();
        if (group.get(maxFreq).isEmpty()) {
            maxFreq--;
        }
        return val;
    }
}