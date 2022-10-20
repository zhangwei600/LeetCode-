package com.fang.leetcode.October2022;

/**
 * @author letian
 * @version 1.0
 * 演示今天的每日一题
 */
public class October17 {
    public static void main(String[] args) {
        October17 o = new October17();
        System.out.println(o.kthGrammar(10, 100));
    }
    // 我们构建了一个包含 n 行(索引从 1 开始)的表。首先在第一行我们写上一个 0。接下来的每一行，将前一行中的0替换为01，1替换为10。
    // 例如，对于 n = 3 ，第 1 行是 0 ，第 2 行是 01 ，第3行是 0110 。
    // 给定行数n和序数 k，返回第 n 行中第 k个字符。（k从索引 1 开始）
    public int kthGrammar(int n, int k) {
        // base case
        if (n <= 2) {
            if (n == 1) {
                return 0;
            } else {
                return 1;
            }
        }
        int half = 1 << n - 2;
        // 是大于等于0
        // 后面应该怎么做呢，有一半就可以了
        // 前面面
        if (k <= half) {
            return kthGrammar(n - 1, k);
        } else {
            return kthGrammar(n - 1, k - half) == 0 ? 1 : 0;
        }
    }
}
