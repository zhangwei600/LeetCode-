package com.fang.leetcode.september2022;

/**
 * @author letian
 * @version 1.0
 * 一个二分顶级扣边界的题目
 * <a href="https://leetcode.cn/problems/ways-to-split-array-into-three-subarrays/">...</a>
 * 我们称一个分割整数数组的方案是 好的，当它满足：
 * <p>
 * 数组被分成三个 非空连续子数组，从左至右分别命名为left，mid，right。
 * left中元素和小于等于mid中元素和，mid中元素和小于等于right中元素和。
 */
public class September15 {
    public static void main(String[] args) {
        September15 temp = new September15();
        System.out.println(temp.waysToSplit(new int[]{1, 2, 3}));
    }

    static int mod = (int) (1e9 + 7);


    public int waysToSplit(int[] nums) {
        int tot = 0;
        int n = nums.length;
        // 前缀和数组
        int[] sum = new int[n + 1];
        // 前缀和数组
        // tot是整个数组的和
        for (int i = 0; i < nums.length; i++) {
            tot += nums[i];
            sum[i + 1] = tot;
        }
        // 答案
        long ans = 0L;
        for (int i = 1; i < n - 1 && sum[i] * 3 <= tot; i++) {
            int left = sum[i];
            int l = i + 1;
            // 右边至少留下一个数字
            // 也就是第三组必须有一个数字
            int r = n - 1;
            while (l <= r) {
                int mid = (l + r) >> 1;
                // 左边开得太小导致中间不够大
                // 所以mid左边的全可以扔了
                if (sum[mid] - left < left) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            // 最小的左边界
            int k = l;
            l = i + 1;
            r = n - 1;
            // 找右边界
            while (l <= r) {
                int mid = (l + r) >> 1;
                // mid开得太大的话
                // 证明mid右边的都不行，直接砍掉
                if (tot - sum[mid] < sum[mid] - left) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            // 最大的右边界
            int j = r;
            ans += j - k + 1;
        }
        return (int) (ans % mod);
    }
}
