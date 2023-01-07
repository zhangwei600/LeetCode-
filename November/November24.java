package com.fang.leetcode.November;

/**
 * @author letian
 * @version 1.0
 * 子数组的个数
 */
public class November24 {
    // 给你一个整数数组 nums 和两个整数：left 及 right 。
    // 找出 nums 中连续、非空且其中最大元素在范围 [left, right] 内的子数组，并返回满足条件的子数组的个数
    //生成的测试用例保证结果符合 32-bit 整数范围。
    public static void main(String[] args) {
        int i = numSubarrayBoundedMax(new int[]{1, 2, 3}, 10, 20);
        System.out.println(i);
    }

    public static int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int n = nums.length;
        // dp[i]表示以nums[i]结尾的，符合要求的子数组有多少个
        int[] dp = new int[n];
        dp[0] = (nums[0] >= left && nums[0] <= right) ? 1 : 0;
        int k = (nums[0] >= left) ? 0 : -1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > right) {
                dp[i] = 0;
            } else if (nums[i] < left) {
                dp[i] = dp[i - 1];
            } else {
                // r如果nums[i]在区间范围内的话，那么就是以前面一个数字结尾符合要求的子数组数量
                // 加上之前连续小于left但是加上i位置的数字，进而符合要求的子数组
                // 以及自身形成的一个子数组
                // 就是以nums[i]为结尾的子数组
                dp[i] = dp[i - 1] + (i - k - 1) + 1;
            }
            if (nums[i] >= left) {
                k = i;
            }
        }
        int res = 0;
        for (int i : dp) {
            res += i;
        }
        return res;
    }
}
