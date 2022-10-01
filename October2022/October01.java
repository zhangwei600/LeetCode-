package com.fang.leetcode.October2022;

import java.util.PriorityQueue;

/**
 * @author letian
 * @version 1.0
 * 跳跃游戏
 */
public class October01 {

    /**
     * 给你一个下标从 0 开始的整数数组 nums和一个整数 k。
     * 一开始你在下标0处。每一步，你最多可以往前跳k步，但你不能跳出数组的边界。也就是说，你可以从下标i跳到[i + 1， min(n - 1, i + k)]包含两个端点的任意位置。
     * 你的目标是到达数组最后一个位置（下标为 n - 1），你的 得分为经过的所有数字之和。
     * 请你返回你能得到的 最大得分。
     * 链接：<a href="https://leetcode.cn/problems/jump-game-vi">...</a>
     * k的意思就是两步之间距离不能超过k
     * 输入：nums = [1,-1,-2,4,-7,3], k = 2
     * 输出：7
     * 解释：你可以选择子序列 [1,-1,4,3] （上面加粗的数字），和为 7 。
     */


    public static void main(String[] args) {

    }
    public int maxResult(int[] nums, int k) {
        // 以i下标开始到尾部的最大得分就是dp[i]
        // 大根堆，顶部是i位置之前所有dp数组中的最大值
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> (o2[0] - o1[0]));
        int n = nums.length;
        int[] dp = new int[n];
        // 最后一个数到尾部的得分
        dp[n - 1] = nums[n - 1];
        // int max = dp[n - 1];
        // 从后往前计算的时候
        pq.offer(new int[]{dp[n - 1], n - 1});
        for (int i = n - 2; i >= 0; i--) {
            while (!pq.isEmpty()) {
                int[] cur = pq.peek();
                // 下标之差小于k
                if (cur[1] - i <= k) {
                    // 下标之差在允许范围内，就可以计算出这个位置开头的最大值
                    // 直接使用堆里面的最大值即可
                    dp[i] = nums[i] + cur[0];
                    // max = Math.max(dp[i], max);
                    break;
                } else {
                    // 堆里面的最大值虽然好，但是已经超过i位置能够够的着的最远起点
                    // 因此直接从堆里面踢出去，不再考虑
                    pq.poll();
                }
            }
            pq.offer(new int[]{dp[i], i});
        }
        return dp[0];


    }
}
