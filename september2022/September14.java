package com.fang.leetcode.september2022;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 九月十四日的滑动窗口加上贪心的策略
 * 排序然后滑动
 */
public class September14 {
    public static void main(String[] args) {
        September14 temp = new September14();
        int i = temp.maxFrequency(new int[]{1, 2, 4}, 5);
        System.out.println(i);
    }

    /**
     *
     * @param nums 数组
     * @param k 操作数，只能将一个数字加一，这样看作一次操作
     * @return 返回在k次操作内，能使得nums数组中产生最多的频数是多少
     * 输入：nums = [1,2,4], k = 5
     * 输出：3
     * 解释：对第一个元素执行 3 次递增操作，对第二个元素执 2 次递增操作，此时 nums = [4,4,4] 。
     * 4 是数组中最高频元素，频数是 3 。
     */
    public int maxFrequency(int[] nums, int k) {
        // 排序加上窗口
        Arrays.sort(nums);
        // 窗口左边界
        int l = 0;
        // 窗口右边的边界
        int r = 1;
        // 最小频数是1，也就是操作数在k以内产生不了超过一个的相同的数组元素
        int res = 1;
        int sum = 0;
        // 窗口扩大的时候操作数需要怎么进行计算
        // O(n)的滑动窗口算法
        while (r < nums.length) {
            // 每次进入这个循环，表明l-r范围上已经通过操作变成同样的数字
            // 那么后面加上一个新的数字，我们怎么计算操作次数呢
            // 这里就是减去倒数第二个数加乘上之前范围里的数字个数
            sum += (nums[r] - nums[r - 1]) * (r - l);
            // 这个代表操作数过多，所以我们需要减少左边的边界
            while (sum > k) {
                sum -= nums[r] - nums[l];
                l++;
            }
            res = Math.max(res, r - l + 1);
            r++;
        }
        return res;

    }

    /**
     *
     * @param nums 数组
     * @return 数组中唯一一个只出现一次的数字
     * 这个数组是排序过后的，而且这个数组中只有一个数字出现了一次，其他的数字均出现了两次
     */
    public int singleNonDuplicate(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        int mid;
        // 如果这个数据和
        while (l < r) {
            mid = l + r >> 1;
            // 和1进行异或运算，那么这个数字由奇数减去1变为偶数
            // 由偶数加上1变为奇数
            if (nums[mid] == nums[mid ^ 1]) {
                // [1,1,2,3,3,4,4,8,8]
                // 只出现一次的数字左边是偶数下标等于右边的，

                l = mid + 1;
            } else {
                // 那个只出现一次的数字右边，是奇数下标等于右边的
                r = mid;
            }
        }
        // 这个速度确实快
        return nums[r];
    }
}
