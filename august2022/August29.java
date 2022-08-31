package com.fang.leetcode.august2022;

import java.util.Scanner;

/**
 * @author letian
 * @version 1.0
 * 逆向并查集
 * 题目链接见https://leetcode.cn/problems/TJZLyC/
 */
public class August29 {

    // 并查集所代表的数组
    static int[] father;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        father = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
        int[] w = new int[n];
        int[] q = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            q[i] = scanner.nextInt();
        }
        // 区间段的和
        int[] sum = new int[n + 1];
        int[] ans = new int[n];
        for (int i = n - 1; i > 0; i--) {
            int x = q[i];
            // x + 1的代表元
            int to = find(x + 1);
            // 合并x和x+1
            father[x] = to;
            sum[to] += sum[x] + w[i];
            // 合并后的最大值和之前某一个堆的最大值进行比较
            ans[i - 1] = Math.max(ans[i], sum[to]);
        }
        for (int i : ans) {
            System.out.println(i);
        }
    }

    public static int find(int x) {
        if (father[x] != x) {
            father[x] = find(father[x]);
        }
        return father[x];
    }

}
