package com.fang.leetcode.December;


import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * <a href="https://leetcode.cn/problems/equal-sum-arrays-with-minimum-number-of-operations/description/">...</a>
 */
public class December07 {
    public static void main(String[] args) {
        int i = minOperations(new int[]{5, 2, 1, 5, 2, 2, 2, 2, 4, 3, 3, 5}, new int[]{1, 4, 5, 5, 6, 3, 1, 3, 3});
        System.out.println(i);
    }


    public static int minOperations(int[] nums1, int[] nums2) {
        if (nums1.length * 6 < nums2.length || nums2.length * 6 < nums1.length) {
            Arrays.sort(new int[]{1});
            return -1;
        }
        int sum = 0;
        for (int i : nums1) {
            sum += i;
        }
        for (int i : nums2) {
            sum -= i;
        }
        // 保证nums1是最大的
        if (sum < 0) {
            sum = -sum;
            int[] temp = nums2;
            nums2 = nums1;
            nums1 = temp;
        }
        int[] map = new int[6];
        // 大变成小的
        for (int i : nums1) {
            map[i - 1]++;
        }
        // 小的变成大的
        for (int i : nums2) {
            map[6 - i]++;
        }

        for (int i = 5, ans = 0; i > 0; i--) {
            if (i * map[i] >= sum) {
                return ans + (sum + i - 1) / i;
            }
            ans += map[i];
            sum -= i * map[i];
        }
        return 0;
    }

}
