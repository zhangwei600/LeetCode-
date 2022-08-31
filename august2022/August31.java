package com.fang.leetcode.august2022;


import java.util.*;

/**
 * @author letian
 * @version 1.0
 * 八月的最后一天，每日一题是模拟栈序列
 * 题目链接是https://leetcode.cn/problems/validate-stack-sequences/
 * 也就是考察一个数组能不能使用另一个数组用栈给模拟出来
 */
@SuppressWarnings("all")
public class August31 {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Scanner scanner = new Scanner(System.in);
        // 一个数字要弹出，那么首先这个数字要进去
        // 那么这个数字之前的数字必须满足逆序
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < pushed.length; i++) {
            map.put(pushed[i], i);
        }
        // 用来模拟的栈
        Stack<Integer> stack = new Stack<>();
        int limit = -1;
        int pre = 0;
        boolean flag = false;
        for (int k : popped) {
            if (map.get(k) > limit) {
                if (flag) {
                    pre = limit + 1;
                }
                limit = map.get(k);
                flag = true;
                // 之前的都进栈
                for (int j = pre; j < limit; j++) {
                    stack.push(pushed[j]);
                }
            } else {
                if (!stack.isEmpty() && stack.peek() == k) {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }
    // 第二种验证栈序列的方法
    public boolean validSequence2(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int index = 0;
        for (int i : pushed) {
            stack.push(i);
            while (!stack.isEmpty() && stack.peek() == popped[index]) {
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}



