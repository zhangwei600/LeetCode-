package com.fang.leetcode.twenty_twenty_three.january;

/**
 * @author letian
 * @version 1.0
 * 演示2021-1-7的一道题
 * <a href="https://leetcode.cn/problems/minimum-operations-to-reduce-x-to-zero/description/">...</a>
 */
public class Seventh {
    // 给你一个整数数组 nums 和一个整数 x 。每一次操作时，你应当移除数组 nums 最左边或最右边的元素，
    // 然后从 x 中减去该元素的值。请注意，需要 修改 数组以供接下来的操作使用。
    // 如果可以将 x 恰好 减到 0 ，返回 最小操作数 ；否则，返回 -1 。
    public static void main(String[] args) {

    }


    public int minOperations(int[] nums, int x) {
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        int target = sum - x, l = 0, r = 0, k = 0, ans = -1;
        if (target < 0) {
            return -1;
        }
        while (r < nums.length) {
            k += nums[r];
            while (k > target) {
                k -= nums[l++];
            }
            if (target == k) {
                ans = Math.max(ans, r - l + 1);
            }
            r++;
        }
        return ans < 0 ? -1 : nums.length - ans;
    }

}
