package com.fang.leetcode.september2022;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 九月八日的两道题
 * 一个是摆动增减构造需要的排列
 * 另一个是二分查找袋子中的最小的球链接分别如下
 * <a href="https://leetcode.cn/problems/beautiful-arrangement-ii/">...</a>
 *<a href="https://leetcode.cn/problems/minimum-limit-of-balls-in-a-bag/">...</a>
 */
public class September08 {
    public static void main(String[] args) {
        September08 temp = new September08();
        System.out.println(Arrays.toString(temp.constructArray(10, 11)));
        System.out.println(temp.minimumSize(new int[]{10, 100, 36721}, 10000));

    }

    public int[] constructArray(int n, int k) {
        // 增减摆动
        int[] ans = new int[n];
        boolean add = true;
        int index = 1;
        ans[0] = 1;
        // 前面出现差为k的摆动序列，后面直接构造一个递增序列即可
        while (k > 0) {
            if (add) {
                ans[index] = k + ans[index - 1];

            } else {
                ans[index] = ans[index - 1] - k;
            }
            k--;
            index++;
            add = !add;
        }
        if (index < n) {
            ans[index++] = ans[1] + 1;
        }
        for (; index < n; index++) {
            ans[index] = ans[index - 1] + 1;
        }
        return ans;
    }


    /**
     * 给你一个整数数组 nums ，其中 nums[i] 表示第 i 个袋子里球的数目。同时给你一个整数 maxOperations
     * 你的开销是单个袋子里球数目的 最大值 ，你想要 最小化 开销。
     */
    // 例子[9] 2可以进行两次操作第一次分为6和3，再将6分为3和3，这个时候可以得到最小的
    // 代价也就是3，我们就是从1到max(nums)之间进行二分找到一个数字，使其能够满足操作次数
    // 不超过maxOperations次，且每个袋子中的球的数量尽可能小，也就是尽量靠近左边
    public int minimumSize(int[] nums, int maxOperations) {
        // 整数数组，需要进行多次操作
        // 查找一个数字，这个数字是需要进行二分的
        // 10^9
        int r = 0;
        for (int num : nums) {
            r = Math.max(r, num);
        }
        int l = 1;
        // int r = max;
        // 这个就是二分查找的范围
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (check(nums, mid) > maxOperations) {
                l = mid + 1;
            } else if (check(nums, mid) == maxOperations && check(nums, mid - 1) > maxOperations) {
                // 这个要找最左边的那个等于maxOperations的那个值
                return mid;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }



    // 这个就是以flag为每一个袋子最大的容量，然后进行计算看每一个袋子的切分数目
        public int check(int[] nums, int flag) {
        int ans = 0;
        //
        for (int num : nums) {
            // 向下取整数
            ans += (num - 1) / flag;
        }
        return ans;
    }
}
