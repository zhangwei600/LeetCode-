package com.fang.leetcode.November;

import java.util.HashSet;

/**
 * @author letian
 * @version 1.0
 * 演示11-14日的每日一题
 * 以及一个原地哈希题目
 * 核心思想是折半查找
 * <a href="https://leetcode.cn/problems/split-array-with-same-average/description/">...</a>
 * 题解的url
 * <a href="https://leetcode.cn/problems/split-array-with-same-average/solutions/1966163/shu-zu-de-jun-zhi-fen-ge-by-leetcode-sol-f630/">...</a>
 */
public class November14 {
    //给定你一个整数数组 nums
    //我们要将 nums 数组中的每个元素移动到 A 数组 或者 B 数组中，使得 A 数组和 B 数组不为空，并且 average(A) == average(B) 。
    //如果可以完成则返回true ， 否则返回 false  。
    //注意：对于数组 arr ,  average(arr) 是 arr 的所有元素的和除以 arr 长度。
    public static void main(String[] args) {

    }


    // nums的长度小于30,所以我们将这个数组分成两半
    // 从两个部分分别取得一些数字，看看能不能凑出题目需要的A和B数组
    public boolean splitArraySameAverage(int[] nums) {
        if (nums.length == 1) {
            return false;
        }
        int n = nums.length;
        int m = n / 2;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            // 这里每个数字都乘以n是为了后面求平均值能够除尽
            // 防止引入浮点数，影响我们判断相等的精度
            nums[i] *= n;
            sum += nums[i];
        }
        // 得到处理后的平均值，这个数字一定是一个整数
        sum /= n;
        for (int i = 0; i < n; i++) {
            // 减去平均值，这样我们就得到一个均值为0的数组
            // 也就是说我们只要弄出一个均值为0的子数组就可以了
            nums[i] -= sum;
        }
        //
        HashSet<Integer> set = new HashSet<>();
        // 通过位图记录一个数字在不在a数组中
        for (int i = 1; i < (1 << m); i++) {
            int p1 = 0;
            // 如果对应位置上是1，代表这个下标上的数字在A数组中，那么就将这个数字加到p1上
            for (int j = 0; j < m; j++) {
                if ((i & (1 << j)) != 0) {
                    p1 += nums[j];
                }
            }
            // 能从前半部分且分出一个sum为0的子数组，直接返回true
            // 剩下的数字一定可以拼成另一个sum等于0的子数组，这个时候两个数组的平均值也就相等了
            if (p1 == 0) {
                return true;
            }
            set.add(p1);
        }
        int rSum = 0;
        for (int i = m; i < n; i++) {
            rSum += nums[i];
        }
        for (int i = 1; i < (1 << (n - m)); i++) {
            int p2 = 0;
            for (int j = m; j < n; j++) {
                if ((i & (1 << (j - m))) != 0) {
                    p2 += nums[j];
                }
            }
            // 第一个成立条件就是可以从后半部分得到一个均值为0的子数组
            // 第二个条件就是从前半个数组中提取的部分和后半个数组中提取的部分，拼到一起可以形成一个均值为0的子数组
            // 也就是说这两个部分拼起来的和等于0，所以我们只需要判断在第一个数组所能得到的所有情况下能不能得到-p2
            // 注意不能将前半部分和后半部分数组中的数字拿光，这样的话，我们就凑不出另一个数组了，也就是p2 != rSum
            if (p2 == 0 || (p2 != rSum) && set.contains(-p2)) {
                return true;
            }
        }
        return false;
    }


    // 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
    //
    //请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
    public int firstMissingPositive(int[] nums) {
        // 原地哈希，这个位置上的数字应该去他应该在的位置上
        // 这个题目的答案一定在[1, n + 1]上出现，因为就算数组中的每一个数都不一样
        // 而且是连续排好序的，全是正整数，我们最多返回n + 1。如[1,2,3]我们返回4
        // 而一般的数组[-1, -2, -5, 7]，我们直接就返回1了，这是第一个没有出现的最小正整数
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // 精华在这个while循环中
            // 小于0的数字我们不考虑，因为哈希函数没他的份
            // 数组里面的下标都是存放1-n的正整数的
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1);
            }
        }
        // 遍历的时候，如果得到第一个num[i]不在自己该在的位置上，也就是nums[i - 1]的话，我们就找到了
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 遍历完都没有找到的话，那么这数组就是由连续的正整数形成的,如[1,2,3,4,5,6,7,8]
        // 那么第一个没出现的正整数就是n + 1，n是数组长度
        return n + 1;
    }
    void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
